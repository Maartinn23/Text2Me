package sintesis.text2me.controllers;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.ui.Model;
import jakarta.servlet.http.HttpServletRequest;
import sintesis.text2me.models.AppUser;
import sintesis.text2me.models.Chat;
import sintesis.text2me.models.FilteredUsersDto;
import sintesis.text2me.models.Message;
import sintesis.text2me.models.MessageDto;
import sintesis.text2me.repositories.AppUserRepository;
import sintesis.text2me.repositories.ChatRepository;
import sintesis.text2me.repositories.MessageRepository;
import sintesis.text2me.services.AppUserService;
import sintesis.text2me.services.AuthenticatorService;
import sintesis.text2me.services.CryptoService;

@Controller
public class ChatController {

	@Autowired
	AppUserRepository appUserRepository;

	@Autowired
	ChatRepository chatRepository;

	@Autowired
	MessageRepository messageRepository;

	@Autowired
	AppUserService appUserService;

	@Autowired
	AuthenticatorService authenticatorService;

	@Autowired
	CryptoService cryptoService;

	/*
	 * End-point que retorna la vista html de la pagina dels xats + buscador.
	 */
	@GetMapping("/xatList")
	public String xatList(Model model, HttpServletRequest request) {

		UserDetails loggedUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		String prueba = appUserService.getUserLogged(loggedUser.getUsername());

		model.addAttribute("user", loggedUser);

		return "xatList";

	}

	/*
	 * End-point que fa el mecanisme de funcionament del buscador d'usuaris
	 */

	@GetMapping("/xatList/userSearch/{name}")
	public ResponseEntity<List<FilteredUsersDto>> searchUsersByNameOrEmail(@PathVariable String name) {

		List<AppUser> users = appUserRepository.findByFirstNameContainingIgnoreCase(name);

		List<FilteredUsersDto> filteredUsers = new ArrayList<>();

		for (AppUser user : users) {
			String username = user.getFirstName();
			String email = user.getEmail();
			filteredUsers.add(new FilteredUsersDto(username, email));

		}

		return ResponseEntity.ok(filteredUsers);

	}

	/*
	 * End-point que ens retorna els xats previament iniciats en altres sesions.
	 */

	@GetMapping("/xatList/xatUsers")
	public ResponseEntity<List<FilteredUsersDto>> getUserChats() {

		String email = authenticatorService.getLoggedUserName();
		AppUser currentUser = appUserRepository.findByEmail(email).orElseThrow();

		List<Chat> chatsLoaded = chatRepository.findByChatParticipantsContaining(currentUser);

		List<FilteredUsersDto> response = new ArrayList<>();

		for (Chat chat : chatsLoaded) {

			List<AppUser> chatParticipants = chat.getChatParticipants();

			for (AppUser user : chatParticipants) {
				if (!user.getEmail().equals(email)) {
					response.add(new FilteredUsersDto(user.getFirstName(), user.getEmail()));
					break;
				}
			}

		}

		return ResponseEntity.ok(response);
	}

	/*
	 * End-point que carrega la vista html amb la informació del usuari receptor
	 * quan iniciem xats.
	 */

	@GetMapping("/xats")
	public String xats(@RequestParam(name = "email", required = false) String receptorEmail, Model model,
			HttpServletRequest request) {

		UserDetails loggedUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		String prueba = appUserService.getUserLogged(loggedUser.getUsername()); // <----- CAMBIAR ESTO PARA PRODUCCIÓN

		model.addAttribute("user", loggedUser);

		if (receptorEmail != null)
			model.addAttribute("receptorEmail", receptorEmail);

		return "xats";

	}

	/*
	 * End-point que carrega la imatge de l'usuari enmagatzemada en bbdd.
	 */

	@GetMapping("/xats/user/image/{user}")
	public ResponseEntity<byte[]> getUserImage(@PathVariable String user) {

		Optional<AppUser> userLogged = appUserRepository.findByEmail(user);

		if (userLogged.isPresent() && userLogged.get().getProfileImage() != null) {
			byte[] userImage = userLogged.get().getProfileImage();
			return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_JPEG_VALUE).body(userImage);
		}

		return ResponseEntity.notFound().build(); // en cas de no trobar res informem de l'error.

	}

	/*
	 * End-point que carrega els missatges persistits a la bbdd y els desxifra.
	 */

	@GetMapping("/xats/messages/{email}")
	public ResponseEntity<List<MessageDto>> getMessages(@PathVariable String email) {

		String currentUserEmail = authenticatorService.getLoggedUserName();

		AppUser user1 = appUserRepository.findByEmail(currentUserEmail).orElseThrow();
		AppUser user2 = appUserRepository.findByEmail(email).orElseThrow();

		Chat chat = chatRepository.findByChatParticipantsContainsAndChatParticipantsContains(user1, user2)
				.orElseThrow(null);

		if (chat == null) {
			return ResponseEntity.ok(List.of());
		}

		List<Message> messages = messageRepository.findByChatOrderBySendingDateAsc(chat);

		List<MessageDto> response = new ArrayList<>();

		for (Message m : messages) {
			try {

				byte[] keyBytes = Base64.getDecoder().decode(m.getSk());
				SecretKey sK = new SecretKeySpec(keyBytes, 0, keyBytes.length, "AES");

				byte[] ivBytes = Base64.getDecoder().decode(m.getIv());
				IvParameterSpec iv = new IvParameterSpec(ivBytes);

				String messageDecrypted = cryptoService.decryptMessage(m.getContent(), sK, iv);

				String imageUrl = "/xats/user/image" + m.getSender().getEmail();

				response.add(new MessageDto(m.getSender().getEmail(), messageDecrypted, m.getSendingDate(), imageUrl));
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		return ResponseEntity.ok(response);
	}

}