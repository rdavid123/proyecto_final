package com.aquaclean.aquacleanapp.controller;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aquaclean.aquacleanapp.model.Pedido;
import com.aquaclean.aquacleanapp.model.Usuario;
import com.aquaclean.aquacleanapp.service.PedidoService;
import com.aquaclean.aquacleanapp.service.UserService;

@Controller
@RequestMapping("/aquaclean")
public class EmpleadoControlador {
	
	@Autowired
	private UserService userService;
	@Autowired
	private PedidoService pedidoService;
	
	Date date = new Date(); //fecha actual
	
	public Usuario getUserAuthenticated(Authentication authentication) {
		return userService.findUserById(userService.findUserByEmail(authentication.getName()).getId());
	}

	@GetMapping("/empleados")
	public String home(Model model, Authentication authentication) {
		model.addAttribute("user", getUserAuthenticated(authentication));
		
		
		model.addAttribute("pedidos", pedidoService.findAll().stream().filter(p -> {
            LocalDateTime fechaPedido = p.getFecha_pedido()
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();
            return fechaPedido.toLocalDate().isEqual(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        }).collect(Collectors.toList()));
		
		model.addAttribute("pendientes_size", pedidoService.findAll().stream().filter(p -> p.getEstado().equals("pendiente")).count());
		model.addAttribute("proceso_size", pedidoService.findAll().stream().filter(p -> p.getEstado().equals("proceso")).count());
		model.addAttribute("terminado_size", pedidoService.findAll().stream().filter(p -> p.getEstado().equals("proceso_terminado")).count());	
		model.addAttribute("total", pedidoService.findAll().size());
		model.addAttribute("title", "Inicio");
		return "empleado/inicio.html";
	}
	
	@GetMapping("/empleados/pedidos")
	public String pedidos(Model model,
			@RequestParam(name = "order",required = false) String order, 
			@RequestParam(name = "id",required = false) String id,
			Authentication authentication) {
		
		/*
		List<PedidoDetalles> pedidos ;
		if(order != null &&  order.equals("1")) {
			pedidos = pedidoService.findAll().stream().sorted(Comparator.comparing(PedidoDetalles::getFecha_pedido).reversed() ).collect(Collectors.toList());
		}else if(order != null && order.equals("2")) {
			model.addAttribute("pedidos",
			pedidos = pedidoService.findAll().stream().sorted(Comparator.comparing(PedidoDetalles::getFecha_pedido) ).collect(Collectors.toList()));
		}else {
			pedidos = pedidoService.findAll();
		}
		*/
		model.addAttribute("pedidos_pendientes", pedidoService.findAll().stream().filter(p -> p.getEstado().equals("pendiente"))
		.collect(Collectors.toList()));
		model.addAttribute("pedidos_en_proceso", pedidoService.findAll().stream().filter(p -> p.getEstado().equals("proceso"))
				.collect(Collectors.toList()));
		
		if(id != null) {
			model.addAttribute("pedido_update", pedidoService.findPedidoDetallesById( Long.parseLong(id) ));
		}
		
		model.addAttribute("size_pedidos_terminados", pedidoService.findAll().stream().filter(p -> p.getEstado().equals("proceso_terminado")).collect(Collectors.toList()).size());
		model.addAttribute("size_pedidos_disponibles", pedidoService.findAll().stream().filter(p -> !p.getEstado().equals("proceso_terminado")).collect(Collectors.toList()).size());
		model.addAttribute("user", getUserAuthenticated(authentication));
		model.addAttribute("title", "clientes");
		return "empleado/pedidos.html";
	}
	
	@GetMapping("/empleados/clientes")
	public String clientes(Model model,Authentication authentication) {
		model.addAttribute("users", userService.findAllClientes());
		model.addAttribute("user", getUserAuthenticated(authentication));
		model.addAttribute("title", "clientes");
		return "empleado/clientes.html";
	}
	
	@GetMapping("/empleados/repartidores")
	public String repartidores(Model model,Authentication authentication) {
		model.addAttribute("users", userService.findAllRepartidores());
		model.addAttribute("user", getUserAuthenticated(authentication));
		model.addAttribute("title", "repartidores");
		return "empleado/repartidores.html";
	}
	
	@PostMapping("/empleados/pedidos/update/proceso/{id}")
	public String updatePedidoProceso(@PathVariable Long id ,RedirectAttributes redirect) {
		Pedido pedido = pedidoService.findById(id);
		pedido.setEstado("proceso");
		pedidoService.update(pedido);
		redirect.addFlashAttribute("mensajesuccess", "Pedido editado exitosamente");
		return "redirect:/aquaclean/empleados/pedidos";
	}
	@PostMapping("/empleados/pedidos/update/terminado/{id}")
	public String updatePedidoTerminado(@PathVariable Long id ,RedirectAttributes redirect) {
		Pedido pedido = pedidoService.findById(id);
		pedido.setEstado("proceso_terminado");
		pedidoService.update(pedido);
		redirect.addFlashAttribute("mensajesuccess", "Pedido editado exitosamente");
		return "redirect:/aquaclean/empleados/pedidos";
	}
}
