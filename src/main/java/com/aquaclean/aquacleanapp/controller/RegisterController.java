package com.aquaclean.aquacleanapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.beans.factory.annotation.Autowired;

import com.aquaclean.aquacleanapp.model.Usuario;
import com.aquaclean.aquacleanapp.model.UsuarioDetalles;
import com.aquaclean.aquacleanapp.service.UserService;

@Controller
public class RegisterController {
	
	@Autowired
	private UserService userService;

	@GetMapping("/register")
	public String register(Model model) {
		model.addAttribute("user", new Usuario());
		return "register.html";
	}
	
	@PostMapping("/register-user")
	public String saveUser(@Validated @ModelAttribute("user") UsuarioDetalles usuario, BindingResult result,RedirectAttributes redirectAttributes) {
		if (isAnyFieldEmpty(usuario)) {
			String firstEmptyField = getFirstEmptyField(usuario);	
			redirectAttributes.addFlashAttribute("errorfield", "Complete el campo " + firstEmptyField);
			return "redirect:/register";
		}
		
		if(userService.findAll().size() == 0) {
			userService.registerAdmin(usuario);
		}else {
			userService.registerCliente(usuario);
		}
		
		redirectAttributes.addFlashAttribute("registersucces", "Registrado exitosamente");
		return "redirect:/login";
	}
	
	private boolean isAnyFieldEmpty(UsuarioDetalles usuario) {
		return 
				usuario.getNombre().isEmpty()
				|| usuario.getApellido().isEmpty()
				|| usuario.getCorreo().isEmpty()
				|| usuario.getDni() == null
				|| usuario.getTelefono() == null
				|| usuario.getPassword().isEmpty();
	}
	
	private String getFirstEmptyField(UsuarioDetalles usuario) {
		if (usuario.getNombre().isEmpty()) {
			return "nombre";
		} else if (usuario.getApellido().isEmpty()) {
			return "apellido";
		} else if (usuario.getCorreo().isEmpty()) {
			return "correo";
		}  else if (null == usuario.getDni()) {
			return "dni";
		} else if (usuario.getTelefono() == null) {
			return "teléfono";
		} else if (usuario.getPassword().isEmpty()) {
			return "password";
		}
		return "";
	}
}
