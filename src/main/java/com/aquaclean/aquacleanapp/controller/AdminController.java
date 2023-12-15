package com.aquaclean.aquacleanapp.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aquaclean.aquacleanapp.model.Usuario;
import com.aquaclean.aquacleanapp.model.UsuarioDetalles;
import com.aquaclean.aquacleanapp.service.PedidoService;
import com.aquaclean.aquacleanapp.service.UserService;
import com.aquaclean.aquacleanapp.service.PagoService;
import com.aquaclean.aquacleanapp.service.OfertaService;

@Controller
@RequestMapping("/aquaclean")
public class AdminController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private PedidoService pedidoService;
	@Autowired
	private OfertaService ofertaService;
	@Autowired
	private PagoService pagoService;
	
	Date date = new Date(); //fecha actual
	
	
	
	public Usuario getUserAuthenticated(Authentication authentication) {
		return userService.findUserById(userService.findUserByEmail(authentication.getName()).getId());
	}


	@GetMapping("/admin")
	public String home(Model model, Authentication authentication) {
		model.addAttribute("user", userService.findUserById(userService.findUserByEmail(authentication.getName()).getId()));
		model.addAttribute("title","admin");
		model.addAttribute("total", pedidoService.findAll().size());
		model.addAttribute("total_clientes", userService.findAllClientes().size());
		model.addAttribute("total_oferta", ofertaService.findAllOfertas().size());
		model.addAttribute("total_pagos", pagoService.sumarPagos());

		return "admin/inicio.html";
		
	}
	
	@GetMapping("/admin/clientes")
	public String clientes(Model model,Authentication authentication) {
		model.addAttribute("users", userService.findAllClientes());
		model.addAttribute("user", getUserAuthenticated(authentication));
		model.addAttribute("title", "clientes");
		return "admin/clientes.html";
	}
	

	@GetMapping("/admin/pedidos")
	public String pedidos(Model model, Authentication authentication) {
		model.addAttribute("pedidos", pedidoService.findAll());
		model.addAttribute("user", getUserAuthenticated(authentication));
		model.addAttribute("title", "pedidos");
		return "admin/pedidos.html";
	}

	
	@GetMapping("admin/empleados")
	public String empleados(Model model,Authentication authentication) {
		model.addAttribute("users", userService.findAllEmpleados());
		model.addAttribute("user", getUserAuthenticated(authentication));
		model.addAttribute("title", "empleados");
	
	
		return "admin/empleados.html";
	}
	
	@GetMapping("/admin/repartidores")
	public String repartidores(Model model,Authentication authentication) {
		model.addAttribute("users", userService.findAllRepartidores());
		model.addAttribute("user", getUserAuthenticated(authentication));
		model.addAttribute("title", "repartidores");
		return "admin/repartidores.html";
	}
	
	@PostMapping("/register/empleado")
	public String registerEmpleado(@ModelAttribute UsuarioDetalles emp, RedirectAttributes redirect) {
		userService.registerEmpleado(emp);
		redirect.addFlashAttribute("register_exito", "Empleado registrado");
		return "redirect:/aquaclean/admin/empleados";
	}
	@PostMapping("/register/repartidor")
	public String registerRepartidor(@ModelAttribute UsuarioDetalles rep, RedirectAttributes redirect) {
		userService.registerRepartidor(rep);
		redirect.addFlashAttribute("register_exito", "Repartidor registrado");
		return "redirect:/aquaclean/admin/repartidores";
	}
	
	
}
