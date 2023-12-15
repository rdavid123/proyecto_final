package com.aquaclean.aquacleanapp.controller;

import org.springframework.security.core.Authentication;

import java.util.ArrayList;
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
			    .filter(p -> {
			        UsuarioDetalles repartidor = p.getRepartidor();
			        return repartidor != null && repartidor.getId() == getUserAuthenticated(authentication).getId() && "proceso_terminado".equals(p.getEstado());
			    })
			    .collect(Collectors.toList()));

		return "repartidor/inicio.html";
	}
	
	@GetMapping("/repartidor/pendientes")
	public String pendientes(Model model,Authentication authentication) {
		model.addAttribute("title", "pendientes");
		model.addAttribute("user", getUserAuthenticated(authentication));
		
		List<PedidoDetalles> pedidos = pedidoService.findAllByIdRepartidor(getUserAuthenticated(authentication).getId()).stream()
				.filter(p->p.getEstado().equals("en_camino")).collect(Collectors.toList());
		
		model.addAttribute("pendientes", pedidos);
		return "repartidor/pendientes.html";
	}
	
	@GetMapping("/repartidor/completados")
	public String completados(Model model, Authentication authentication) {
		model.addAttribute("user", getUserAuthenticated(authentication));
		model.addAttribute("title", "completados");
		List<PedidoDetalles> pedidos = pedidoService.findAllByIdRepartidor(getUserAuthenticated(authentication).getId()).stream()
				.filter(p->p.getEstado().equals("finalizado")).collect(Collectors.toList());
		model.addAttribute("pedidos", pedidos);
		return "repartidor/completados.html";
	}
	
	@PostMapping("/repartidor/accept/pedido/{id_pedido}")
	public String aceptarPedido(@PathVariable Long id_pedido ,RedirectAttributes redirect,Authentication authentication) {
		if(getUserAuthenticated(authentication).getEstado_repartidor() == true) {
			Pedido pedido = pedidoService.findById(id_pedido);
			pedido.setRepartidor(getUserAuthenticated(authentication).getId());
			pedido.setEstado("en_camino");
			pedidoService.update(pedido); //add repartidor 
			/* get and update Repartidor */
			Usuario rep = getUserAuthenticated(authentication);
			rep.setEstado_repartidor(false);
			userService.updateUsuario(rep); //update estado repartidor
			redirect.addFlashAttribute("mensajesuccess", "Pedido aceptado");
			return "redirect:/aquaclean/repartidor";
		}else {
			redirect.addFlashAttribute("mensajesuccess", "No se pudo aceptar este pedido, tienes un pedido pendiente por terminar.");
			return "redirect:/aquaclean/repartidor";
		}
		
	}
	
	@PostMapping("/repartidor/cancelar/pedido/{id_pedido}")
	public String rechazarPedido(@PathVariable Long id_pedido ,RedirectAttributes redirect,Authentication authentication) {
		Pedido pedido = pedidoService.findById(id_pedido); //buscar pedido a rechazar
		
		List<Usuario> repartidores_disponibles = userService.findAllRepartidores().stream().filter(r -> r.getEstado_repartidor()== true && r.getId()!=getUserAuthenticated(authentication).getId()).collect(Collectors.toList());
		pedido.setRepartidor(repartidores_disponibles.get(0).getId()); //obtener el primer repartidor y asignarlo al pedido
		if(repartidores_disponibles.isEmpty()) {
			redirect.addFlashAttribute("mensajesuccess", "No hay un repartidor disponible actualmente. Intentalo luego");
        	return "redirect:/aquaclean/pedido";
		}	
		pedidoService.update(pedido);
		redirect.addFlashAttribute("mensajesuccess", "Pedido rechazado.");
		return "redirect:/aquaclean/repartidor";
	}
	
	@PostMapping("/repartidor/finalizar/pedido/{id_pedido}")
	public String finalizarPedido(@PathVariable Long id_pedido ,RedirectAttributes redirect, Authentication authentication) {
		Pedido pedido = pedidoService.findById(id_pedido);
		pedido.setEstado("finalizado");
		Usuario repartidor = getUserAuthenticated(authentication);
		repartidor.setEstado_repartidor(true);
		userService.updateUsuario(repartidor);
		pedidoService.update(pedido);
		redirect.addFlashAttribute("mensajesuccess", "Pedido finalizado exitosamente");
		return "redirect:/aquaclean/repartidor";
	}
}
