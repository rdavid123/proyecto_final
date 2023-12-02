package com.aquaclean.aquacleanapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aquaclean.aquacleanapp.service.UserService;

@Controller
@RequestMapping("/aquaclean")
public class AdminController {
	
	@Autowired
	private UserService userService;

	@GetMapping("/admin")
	public String home(Model model, Authentication authentication) {
		model.addAttribute("user", userService.findUserById(userService.findUserByEmail(authentication.getName()).getId()));
		model.addAttribute("title","admin");
		return "admin/inicio.html";
	}
}
