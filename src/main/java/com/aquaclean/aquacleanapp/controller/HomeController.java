package com.aquaclean.aquacleanapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.aquaclean.aquacleanapp.model.UsuarioDetalles;
import com.aquaclean.aquacleanapp.service.UserService;


@Controller
public class HomeController {
	@Autowired
	private UserService userService;
	
	@GetMapping("")
	public String Home(Model model, Authentication authentication) {
		if (authentication != null && authentication.isAuthenticated()) {
			UsuarioDetalles usuarioDetalles = userService.findUserByEmail(authentication.getName());
			model.addAttribute("loginUser", usuarioDetalles.getNombre()+" "+usuarioDetalles.getApellido());
		} else {
			model.addAttribute("loginUser", "Iniciar sesión");
		}
		return "home.html";
	}
}
