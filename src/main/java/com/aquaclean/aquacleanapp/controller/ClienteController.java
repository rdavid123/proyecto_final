package com.aquaclean.aquacleanapp.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aquaclean.aquacleanapp.model.Pedido;
import com.aquaclean.aquacleanapp.model.PedidoDetalles;
import com.aquaclean.aquacleanapp.model.Prendas;
import com.aquaclean.aquacleanapp.model.StripePaymentRequest;
import com.aquaclean.aquacleanapp.model.Usuario;
import com.aquaclean.aquacleanapp.model.UsuarioDetalles;
import com.aquaclean.aquacleanapp.service.PedidoService;
import com.aquaclean.aquacleanapp.service.ServicioService;
import com.aquaclean.aquacleanapp.service.UserService;

@Controller
@RequestMapping("/aquaclean")
public class ClienteController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private PedidoService pedidoService;
	@Autowired
	private ServicioService servicioService;
	@Value("${stripe.api.publicKey}")
    private String publicKey;
	
	public Usuario getUserAuthenticated(Authentication authentication) {
		return userService.findUserById(userService.findUserByEmail(authentication.getName()).getId());
	}
	
	
	public List<Prendas> listPrendas(){
		List<Prendas> prendas = new ArrayList<>();
		prendas.add(new Prendas("camisa manga corta",130,"camisas"));
		prendas.add(new Prendas("camisa manga larga",200,"camisas"));
		prendas.add(new Prendas("sabana",800,"ropa de cama"));
		return prendas;
	}
	
	@GetMapping("/clientes")
	public String home(Model model,Authentication authentication, 
			@RequestParam(name = "tipo", required = false) String tipo) {

		model.addAttribute("user", getUserAuthenticated(authentication));
		model.addAttribute("title", "Inicio");
		model.addAttribute("servicios", servicioService.findAllServicios());
		return "cliente/inicio.html";
	}
	
	@GetMapping("/pedido")
	public String pedido(Model model,Authentication authentication, 
			@RequestParam(name = "id", required = false) Integer id) {
		model.addAttribute("id_servicio", id);
		model.addAttribute("pedido",new Pedido());
		model.addAttribute("user", getUserAuthenticated(authentication));
		model.addAttribute("title", "Inicio");
		model.addAttribute("servicios", servicioService.findAllServicios());
		model.addAttribute("prendas", listPrendas());
		return "cliente/form_pedido.html";
	}
	
	@PostMapping("/clientes/save-pedido")
	public String saveNewPedido(Model model, @ModelAttribute Pedido p,
			Authentication authentication, RedirectAttributes redirectAttributes,
			@ModelAttribute StripePaymentRequest request) {
		double pricedouble = p.calcularTotal();
		int price = (int) pricedouble;
		
		/* Asignar repartidor*/
		
		if(pedidoService.findAll().size() == 0) { //si es el primer pedido se le asigna el primer repartidor
        	p.setRepartidor(userService.findAllRepartidores().get(0).getId());
        }
		
		List<Usuario> repartidores_disponibles = userService.findAllRepartidores().stream().filter(r -> r.getEstado_repartidor()== true).collect(Collectors.toList());
		p.setRepartidor(repartidores_disponibles.get(0).getId());
		if(repartidores_disponibles.isEmpty()) {
			redirectAttributes.addFlashAttribute("mensaje_error", "No hay un repartidor disponible actualmente. Intentalo luego");
        	return "redirect:/aquaclean/pedido";
		}
	
		model.addAttribute("amount",price);
		model.addAttribute("productName","PedidoReservado");
		model.addAttribute("publicKey", publicKey);
		model.addAttribute("id_cliente", getUserAuthenticated(authentication).getId());
		model.addAttribute("fecha_pedido", new Date());
		model.addAttribute("fecha_entrega", p.getFecha_entrega());
		model.addAttribute("tipo_entrega", p.getTipo_entrega());
		model.addAttribute("descripcion", p.getDescripcion());
		model.addAttribute("direccion", p.getDireccion());
		model.addAttribute("id_servicio", p.getServicio());
		model.addAttribute("id_repartidor", p.getRepartidor());
		model.addAttribute("cantidad_prendas", p.getCantidad_prendas());
		return "cliente/checkout.html";
	}
	
	
	@PostMapping("/clientes/pedido/save/exact")
	public String savePedidoPesoExacto(@ModelAttribute Pedido p, RedirectAttributes redirectAttributes,
			Authentication authentication, String servicio) {
		
		Date fechaActual = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        String fechaFormateada = dateFormat.format(fechaActual);
        
        if(pedidoService.findAll().size() == 0) { //si es el primer pedido se le asigna el primer repartidor
        	p.setRepartidor(userService.findAllRepartidores().get(0).getId());
     
        }

        for (Usuario rp : userService.findAllRepartidores()) {
            // Verificar si el repartidor actual no está asignado a ningún pedido en proceso o está asignado a un pedido finalizado
            boolean repartidorDisponible = true;

            for (PedidoDetalles pedido : pedidoService.findAll()) {
                if (pedido.getRepartidor() != null && pedido.getRepartidor().getId() == rp.getId()) {
                    // El repartidor está asignado a algún pedido
                    if (pedido.getEstado().equals("en_proceso") || pedido.getEstado().equals("pendiente") || pedido.getEstado().equals("proceso_terminado")) {
                        // El repartidor está asignado a un pedido en proceso o pendiente o proceso_terminado
                        repartidorDisponible = false;
                        break;
                    } else if (!pedido.getEstado().equals("finalizado")) {
                        // El repartidor está asignado a un pedido que no está finalizado
                        repartidorDisponible = false;
                        break;
                    }
                }
            }

            // Si el repartidor está disponible, asignarlo al pedido actual y salir del bucle
            if (repartidorDisponible) {
                p.setRepartidor(rp.getId());
                break;
            }else {
            	redirectAttributes.addFlashAttribute("mensaje_error", "No hay un repartidor disponible actualmente. Intentalo luego");
            	return "redirect:/aquaclean/pedido";
            }
        }

        p.setFecha_pedido(fechaFormateada);
		p.setEstado("pendiente");
		p.setCliente(userService.findUserById(userService.findUserByEmail(authentication.getName()).getId()).getId());
		pedidoService.save(p);
		redirectAttributes.addFlashAttribute("mensaje", "Pedido agregado exitosamente");
		return "redirect:/aquaclean/pedido";
	}
	
	@GetMapping("/clientes/pedidos")
	public String servicios(Model model,Authentication authentication) {
		UsuarioDetalles usuarioDetalles = userService.findUserByEmail(authentication.getName());

		model.addAttribute("pedidos", 
				pedidoService.findAll().stream().filter(p -> p.getCliente().getId() == usuarioDetalles.getId())
			    .collect(Collectors.toList()));
		model.addAttribute("user", getUserAuthenticated(authentication));
		model.addAttribute("title", "Pedidos");
		return "cliente/pedidos.html";
	}
}
