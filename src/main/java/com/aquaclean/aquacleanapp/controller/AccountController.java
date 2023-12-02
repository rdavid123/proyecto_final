package com.aquaclean.aquacleanapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.aquaclean.aquacleanapp.model.Usuario;
import com.aquaclean.aquacleanapp.model.UsuarioDetalles;
import com.aquaclean.aquacleanapp.service.UserService;

@Controller
@RequestMapping("/aquaclean")
public class AccountController {
	@Autowired
	private UserService userService;

	@GetMapping("/profile/{id}")
	public String profile(@PathVariable(name = "id") Long id, Model model, Authentication authentication,
			RedirectAttributes redirectAttribute) {
		if (authentication == null) {
			redirectAttribute.addFlashAttribute("session_expirated", "tu sesión ha expirado");
			return "redirect:/login";
		}
		
		model.addAttribute("user",
				userService.findUserDetallesById(userService.findUserByEmail(authentication.getName()).getId()));
		model.addAttribute("searchUser", userService.findUserDetallesById(id));
		model.addAttribute("title", userService.findUserDetallesById(id).getFullName()); // obtener el nombre completo
		return "cliente/profile.html";
	}

	@PreAuthorize("authenticated()")
	@GetMapping("/settings/profile")
	public String profileSettings(Authentication authentication, Model model, RedirectAttributes redirectAttribute) {
		if (authentication == null) {
			redirectAttribute.addFlashAttribute("session_expirated", "tu sesión ha expirado");
			return "redirect:/login";
		}
	
		Long id = userService.findUserByEmail(authentication.getName()).getId();
		model.addAttribute("user", userService.findUserById(id));
		model.addAttribute("title", "settings");
		return "cliente/settings.html";
	}
	
	@PostMapping("/settings/profile/update/{id}")
	public String updateProfile(@PathVariable Long id, @ModelAttribute Usuario u, MultipartFile avatarfile,
			RedirectAttributes redirect) {
		//Usuario usu = userService.findUserById(id);
		//userService.update(usu);
		redirect.addFlashAttribute("success_update", "Tu cuenta se ha actualizado correctamente");
		return "redirect:/aquaclean/profile/{id}";
	}
}
