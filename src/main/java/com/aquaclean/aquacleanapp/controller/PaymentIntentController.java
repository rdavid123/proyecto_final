package com.aquaclean.aquacleanapp.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.aquaclean.aquacleanapp.model.StripePaymentRequest;
import com.aquaclean.aquacleanapp.model.StripePaymentResponse;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;

@RestController
public class PaymentIntentController {
	@PreAuthorize("CLIENTE")
	@PostMapping("/create-payment-intent")
	public StripePaymentResponse realizarPago(@RequestBody StripePaymentRequest stripeRequest,
			Authentication authentication) throws StripeException {
		
		PaymentIntentCreateParams.Builder builder = PaymentIntentCreateParams.builder()
				.setAmount(stripeRequest.getAmount() * 100L)
				.setCurrency("usd")
				.setAutomaticPaymentMethods(
						PaymentIntentCreateParams.AutomaticPaymentMethods.builder().setEnabled(true).build()
						);

		builder.putMetadata("productName", stripeRequest.getProductName())
				.putMetadata("id_cliente", stripeRequest.getId_cliente())
				.putMetadata("tipo_entrega", stripeRequest.getTipo_entrega())
				.putMetadata("fecha_entrega", stripeRequest.getFecha_entrega())
				.putMetadata("direccion", stripeRequest.getDireccion())
				.putMetadata("id_servicio", stripeRequest.getId_servicio())
				.putMetadata("cantidad_prendas", stripeRequest.getCantidad_prendas())
				//.putMetadata("id_repartidor", stripeRequest.getId_repartidor())
				.putMetadata("amount", Double.toString(stripeRequest.getAmount()));

		PaymentIntentCreateParams params = builder.build();
		PaymentIntent intent = PaymentIntent.create(params);
		// Devuelve el client secret y el ID del intent
		return new StripePaymentResponse(intent.getId(), intent.getClientSecret());
	}
}
