package com.cdo.cloud.web.security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/security")
public class SecurityController {

	@GetMapping("/index")
	public String index() {		
		return "index";
	}
	
	@GetMapping("/welcome")
	public String welcome() {
		return "welcome";
	}
}
