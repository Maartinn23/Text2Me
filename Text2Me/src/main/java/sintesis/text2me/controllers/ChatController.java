package sintesis.text2me.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.ui.Model;
import jakarta.servlet.http.HttpServletRequest;
import sintesis.text2me.repositories.AppUserRepository;
import sintesis.text2me.services.AppUserService;

@Controller
public class ChatController {
	
	
	
	@Autowired
	AppUserRepository repo;

	@Autowired
	AppUserService appUserService;
	
	@GetMapping("/xats")
	public String xats(Model model, HttpServletRequest request) {

		UserDetails loggedUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		String prueba = appUserService.getUserLogged(loggedUser.getUsername());

		model.addAttribute("user", loggedUser);

		return "xats";

	}


}
