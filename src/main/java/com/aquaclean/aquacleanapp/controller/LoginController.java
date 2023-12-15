package com.aquaclean.aquacleanapp.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.filter.OncePerRequestFilter;

import com.aquaclean.aquacleanapp.model.Usuario;
import com.aquaclean.aquacleanapp.model.UsuarioDetalles;
import com.aquaclean.aquacleanapp.service.DjangoAuthService;
import com.aquaclean.aquacleanapp.service.UserService;

@Controller
public class LoginController {
	
	@Autowired
	private UserService userService;
	
	@PreAuthorize("isAnonymous()")
	@GetMapping("/login")
	public String login(Model model) {
		model.addAttribute("usuario", new Usuario());
		return "login.html";
	}
	@Autowired
    private DjangoAuthService djangoAuthService;

    @PostMapping("/authenticate")
    public String authenticate(@ModelAttribute Usuario authRequest, Model model) {
        // authRequest es una clase que representa las credenciales del usuario (correo y password)
        ResponseEntity<Usuario> responseEntity = djangoAuthService.authenticate(authRequest.getCorreo(), authRequest.getPassword());
        Usuario u = responseEntity.getBody();
        UsuarioDetalles ud = userService.findUserDetallesById(u.getId());

        Collection<? extends GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority(ud.getRol().getRol()));
        Authentication authentication = new UsernamePasswordAuthenticationToken(u.getCorreo(), null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return "redirect:/login"; 
        // si esta autenticado no puede regresar a la vista login y se redirecciona a la url según su rol
    }
}
