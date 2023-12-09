package com.aquaclean.aquacleanapp.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aquaclean.aquacleanapp.model.PagoDetalles;
import com.aquaclean.aquacleanapp.model.Pedido;
import com.aquaclean.aquacleanapp.model.PedidoDetalles;
import com.aquaclean.aquacleanapp.model.Usuario;
import com.aquaclean.aquacleanapp.service.PagoService;
import com.aquaclean.aquacleanapp.service.PedidoService;
import com.aquaclean.aquacleanapp.service.UserService;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.net.Webhook;

@RestController
@RequestMapping
public class StripeWebhookController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private PedidoService pedidoService;
	
	@Autowired
	private PagoService pagoService;
	
	@Value("${stripe.webhook.secret}")
	private String webhookSecret;

	@PostMapping("/webhook/stripe")
	public ResponseEntity<String> handleStripeEvent(@RequestBody String payload,
			@RequestHeader("Stripe-Signature") String signature) {
		Event event;

		try {
			event = Webhook.constructEvent(payload, signature, webhookSecret);
			switch (event.getType()) {
			case "payment_intent.succeeded":

				PaymentIntent paymentIntent = (PaymentIntent) event.getData().getObject();

				String clienteIdStr = paymentIntent.getMetadata().get("id_cliente");
				String tipoentrega = paymentIntent.getMetadata().get("tipo_entrega");
				String direccion = paymentIntent.getMetadata().get("direccion");
				String id_servicio = paymentIntent.getMetadata().get("id_servicio");
				String fecha_entrega = paymentIntent.getMetadata().get("fecha_entrega");
				
				//String id_repartidor = paymentIntent.getMetadata().get("id_repartidor");
				String cantidad = paymentIntent.getMetadata().get("cantidad_prendas");
				
				
				//pago
				String amount = paymentIntent.getMetadata().get("amount");
				
				long id_cliente = Long.parseLong(clienteIdStr);
				//long id_pedido = Long.parseLong(pedidoIdStr);
				Double amounttotal = Double.parseDouble(amount);
				
				Pedido nuevoPedido = new Pedido();
				userService.findUserById(id_cliente);
				
				Date fechaActual = new Date();
				
		        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
		        String fechaFormateada = dateFormat.format(fechaActual);
		        
		        nuevoPedido.setFecha_pedido(fechaFormateada);
		        
				nuevoPedido.setCliente(userService.findUserById(id_cliente).getId());
				nuevoPedido.setEstado("pendiente");
				nuevoPedido.setTipo_entrega(tipoentrega);
				nuevoPedido.setServicio(Long.parseLong(id_servicio));
				nuevoPedido.setDireccion(direccion);
				nuevoPedido.setFecha_entrega(fecha_entrega);
				//nuevoPedido.setRepartidor(Long.parseLong(id_repartidor));
				nuevoPedido.setCantidad_prendas(Integer.parseInt(cantidad));		
				
				pedidoService.save(nuevoPedido);
				
				PagoDetalles newPago = new PagoDetalles();
				newPago.setPedido(nuevoPedido);
				newPago.setMonto(amounttotal);
				newPago.setFecha_pago(fechaFormateada);
				newPago.setTransaccion_id("15648956");
				pagoService.save(newPago);

				break;
			default:
				System.out.println("Unhandled event type: " + event.getType());
			}
		} catch (SignatureVerificationException e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.OK).body("Evento manejado exitosamente, pedido y pago guardado. ");

	}
}
