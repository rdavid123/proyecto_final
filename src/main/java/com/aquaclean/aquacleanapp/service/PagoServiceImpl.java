package com.aquaclean.aquacleanapp.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.aquaclean.aquacleanapp.model.Pago;
import com.aquaclean.aquacleanapp.model.PagoDetalles;
import com.aquaclean.aquacleanapp.model.Pedido;

@Service
public class PagoServiceImpl implements PagoService{
	
	@Value("${django.api.url}")
	private String api_url;
	
    private RestTemplate restTemplate = new RestTemplate();
    private HttpHeaders headers = new HttpHeaders();


	@Override
	public Pago findById(Long id) {
		ResponseEntity<Pago> pago = restTemplate.getForEntity(api_url+"/pagos/"+id+"/", Pago.class);
		Pago p = pago.getBody();
		return p;
	}

	@Override
	public void save(PagoDetalles pago) {
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<PagoDetalles> request = new HttpEntity<>(pago, headers);
		restTemplate.exchange(api_url + "/pagosdetail/", HttpMethod.POST, request, PagoDetalles.class);
	}

}
