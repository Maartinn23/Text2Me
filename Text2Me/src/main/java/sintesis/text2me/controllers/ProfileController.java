
package sintesis.text2me.controllers;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import sintesis.text2me.models.AppUser;
import sintesis.text2me.models.UpdateUserDto;
import sintesis.text2me.repositories.AppUserRepository;
import sintesis.text2me.services.AuthenticatorService;

@Controller
public class ProfileController {
	
	@Autowired
	AppUserRepository appUserRepository;
	
	@Autowired
	AuthenticatorService authenticatorService;
	
	

	@GetMapping("/perfil")
	public String profile() {
		return "profile";
	}
	
	@GetMapping("/infoUsuari")
	public String infoUser() {
		return "userPreview";
	}
	
	@GetMapping(value = "/api/perfil",produces = "application/json" )
	@ResponseBody
	public Optional<AppUser> getUserDataByEmail(@AuthenticationPrincipal UserDetails userDetails){
		return appUserRepository.findByEmail(userDetails.getUsername());
	}
	
	
	@GetMapping(value = "/api/perfil/{email}")
	@ResponseBody
	public Optional<AppUser> getUserPreview(@PathVariable String email){
		return appUserRepository.findByEmail(email);
	}
	@PutMapping("/perfil/actualitzar-dades")
	public ResponseEntity<?> updateProfileData(@RequestBody UpdateUserDto updateUserDto){
		
		String currentUserEmail = authenticatorService.getLoggedUserName();
		
		Optional<AppUser> userLogged = appUserRepository.findByEmail(currentUserEmail);
		
		if(userLogged.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		AppUser userUpdated = userLogged.get();
		
		userUpdated.setFirstName(updateUserDto.getFirstName());
		userUpdated.setLastName(updateUserDto.getLastName());
		userUpdated.setEmail(updateUserDto.getEmail());
		userUpdated.setPhone(updateUserDto.getPhone());
		userUpdated.setAddress(updateUserDto.getAddress());	
		

			
		appUserRepository.save(userUpdated);
		
		return ResponseEntity.ok("Perfil actualitzat! ");
		
	}
	
	
	@PostMapping("/perfil/actualitzar-foto")
	public String updateProfileImage(@RequestParam("imatgePerfil") MultipartFile image,
									@AuthenticationPrincipal UserDetails userDetails) throws IOException {
		
		Optional<AppUser> userLogged = appUserRepository.findByEmail(userDetails.getUsername());
		
		if(userLogged.isPresent()) {
			AppUser appUser = userLogged.get();
			appUser.setProfileImage(image.getBytes());
			appUserRepository.save(appUser);
		}
		
		return "redirect:/perfil"; // tornem a la vista del perfil.
	}
	
	@GetMapping("/perfil/imatge")
	@ResponseBody
	public ResponseEntity<byte[]> getProfileImage(@AuthenticationPrincipal UserDetails userDetails){
		
		Optional<AppUser> userLogged =  appUserRepository.findByEmail(userDetails.getUsername());
		
		if(userLogged.isPresent() && userLogged.get().getProfileImage() != null){
			byte[] image = userLogged.get().getProfileImage();
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_JPEG_VALUE)
					.body(image);
		}
		
		return ResponseEntity.notFound().build(); // en cas de no trobar res informem de l'error.
	}
	
	
	@GetMapping("/perfil/imatge-previsualitzada/{email}")
	@ResponseBody
	public ResponseEntity<byte[]> getPreviewImage(@PathVariable String email){
				
		Optional<AppUser> userLogged =  appUserRepository.findByEmail(email);
		
		if(userLogged.isPresent() && userLogged.get().getProfileImage() != null){
			byte[] image = userLogged.get().getProfileImage();
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_JPEG_VALUE)
					.body(image);
		}
		
		return ResponseEntity.notFound().build(); // en cas de no trobar res informem de l'error.
	}
	
	
}





