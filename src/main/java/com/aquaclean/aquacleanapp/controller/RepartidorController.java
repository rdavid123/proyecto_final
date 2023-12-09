package com.aquaclean.aquacleanapp.controller;

import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aquaclean.aquacleanapp.model.Pedido;
import com.aquaclean.aquacleanapp.model.PedidoDetalles;
import com.aquaclean.aquacleanapp.model.Usuario;
import com.aquaclean.aquacleanapp.model.UsuarioDetalles;
import com.aquaclean.aquacleanapp.service.PedidoService;
import com.aquaclean.aquacleanapp.service.UserService;

@Controller
@RequestMapping("aquaclean/")
public class RepartidorController {
	@Autowired
	private UserService userService;
	@Autowired
	private PedidoService pedidoService;
	
	public Usuario getUserAuthenticated(Authentication authentication) {
		return userService.findUserById(userService.findUserByEmail(authentication.getName()).getId());
	}
	
	@GetMapping("/repartidor")
	public String home(Model model, Authentication authentication) {
		model.addAttribute("title", "repartidor");
		model.addAttribute("user", getUserAuthenticated(authentication));
		model.addAttribute("pedidos", pedidoService.findAll().stream()
		.filter(p->p.getEstado().equals("proceso_terminado")).collect(Collectors.toList()));
		return "repartidor/inicio.html";
	}
	
	@GetMapping("/repartidor/pendientes")
	public String pendientes(Model model,Authentication authentication) {
		model.addAttribute("title", "pendientes");
		model.addAttribute("user", getUserAuthenticated(authentication));
		List<PedidoDetalles> pedidos_pendientes = pedidoService.findAll().stream()
				.filter(p -> {
				    UsuarioDetalles repartidor = p.getRepartidor();
				    return repartidor != null && repartidor.getId() == getUserAuthenticated(authentication).getId();
				})
				.collect(Collectors.toList());
		model.addAttribute("pendientes", pedidos_pendientes);
		return "repartidor/pendientes.html";
	}
	
	@PostMapping("/repartidor/accept/pedido/{id_pedido}")
	public String updatePedio(@PathVariable Long id_pedido ,RedirectAttributes redirect,Authentication authentication) {
		if(getUserAuthenticated(authentication).getEstado_repartidor() == true) {
			Pedido pedido = pedidoService.findById(id_pedido);
			pedido.setRepartidor(getUserAuthenticated(authentication).getId());
			pedido.setEstado("en_camino");
			pedidoService.update(pedido); //add repartidor 
			/* get and update Repartidor */
			Usuario rep = getUserAuthenticated(authentication);
			rep.setEstado_repartidor(false);
			userService.updateEstadoRepartidor(rep); //update estado repartidor
			redirect.addFlashAttribute("mensajesuccess", "Pedido aceptado");
			return "redirect:/aquaclean/repartidor";
		}else {
			redirect.addFlashAttribute("mensajesuccess", "No se pudo aceptar este pedido, tienes un pedido pendiente por terminar.");
			return "redirect:/aquaclean/repartidor";
		}
		
	}
}
