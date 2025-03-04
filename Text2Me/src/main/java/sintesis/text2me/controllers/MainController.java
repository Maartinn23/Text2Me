package sintesis.text2me.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

	@GetMapping({"", "/"})
	public String home() {
		return "index";
	}

	@GetMapping("/contact")
	public String contact() {
		return "contact";
	}
	
	@GetMapping("/xats")
	public String xats() {
		return "xats";
	}
	
	@GetMapping("/register")
	public String register() {
		return "register";
		
	}
	@GetMapping("/login")
	public String login() {
		return "login";
		
	}



}

































