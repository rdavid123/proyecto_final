package com.aquaclean.aquacleanapp.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aquaclean.aquacleanapp.model.RequestUsuario;
import com.aquaclean.aquacleanapp.model.Usuario;
import com.aquaclean.aquacleanapp.service.UserService;

@RestController
@RequestMapping("/aquaclean")
public class updateUserController {
	@Autowired
	private UserService userService;
	
	public Usuario getUserAuthenticated(Authentication authentication) {
		return userService.findUserById(userService.findUserByEmail(authentication.getName()).getId());
	}
	/*
	@PostMapping("/settings/profile/update/{id}")
	public String updateProfile(@PathVariable Long id, @ModelAttribute RequestUsuario u,
			@RequestParam("avatar_img") MultipartFile  avatar,
			RedirectAttributes redirect) {
		Usuario getUser = userService.findUserById(id);
		u.setApellido(getUser.getApellido());
		u.setNombre(getUser.getNombre());
		u.setCorreo(getUser.getCorreo());
		u.setDni(getUser.getDni());
		u.setEstado_repartidor(getUser.getEstado_repartidor());
		u.setId(getUser.getId());
		u.setPassword(getUser.getPassword());
		u.setRol(getUser.getRol());
		u.setTelefono(getUser.getTelefono());
		u.setAvatar(avatar);
		userService.update(u);
		redirect.addFlashAttribute("success_update", "Tu cuenta se ha actualizado correctamente");
		return "redirect:/aquaclean/profile/{id}";
	}
	*/
}
