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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import sintesis.text2me.models.AppUser;
import sintesis.text2me.repositories.AppUserRepository;

@Controller
public class ProfileController {
	
	
	
	@Autowired
	AppUserRepository repo;
	

	@GetMapping("/perfil")
	public String profile() {
		
		return "profile";
		
	}
	
	@GetMapping(value = "/api/perfil",produces = "application/json" )
	@ResponseBody
	public Optional<AppUser> getUserDataByEmail(@AuthenticationPrincipal UserDetails userDetails){
		
		return repo.findByEmail(userDetails.getUsername());
	
		
	}
	
	@PostMapping("/perfil/actualitzar-foto")
	public String updateProfileImage(@RequestParam("imatgePerfil") MultipartFile image,
									@AuthenticationPrincipal UserDetails userDetails) throws IOException {
		
		
		Optional<AppUser> userLogged = repo.findByEmail(userDetails.getUsername());
		
		if(userLogged.isPresent()) {
			AppUser appUser = userLogged.get();
			appUser.setProfileImage(image.getBytes());
			repo.save(appUser);
		}
		
		
		return "redirect:/perfil";
		
		
	}
	
	
	@GetMapping("/perfil/imatge")
	@ResponseBody
	public ResponseEntity<byte[]> getProfileImage(@AuthenticationPrincipal UserDetails userDetails){
		
		Optional<AppUser> userLogged =  repo.findByEmail(userDetails.getUsername());
		
		if(userLogged.isPresent() && userLogged.get().getProfileImage() != null){
			byte[] image = userLogged.get().getProfileImage();
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_JPEG_VALUE)
					.body(image);
		}
		
		
		return ResponseEntity.notFound().build(); // en cas de no trobar res informem de l'error.
	}
	
	
}
