package com.aquaclean.aquacleanapp.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;


import com.aquaclean.aquacleanapp.model.Ofertas;
import com.aquaclean.aquacleanapp.model.Pago;
import com.aquaclean.aquacleanapp.model.Pedido;
import com.aquaclean.aquacleanapp.model.Servicio;

@Service
public class OfertaServiceImpl implements OfertaService {
	
	@Value("${django.api.url}")
	private String api_url;
	private RestTemplate restTemplate = new RestTemplate();
    private HttpHeaders headers = new HttpHeaders();
	

	@Override
	public List<Ofertas> findAllOfertas() {
		Ofertas[] array = restTemplate.getForObject(api_url+"/Ofertas/", Ofertas[].class);
		List<Ofertas> ofertas = Arrays.asList(array);
		return ofertas;
	}
	
	@Override
	public Ofertas findById(Long id) {
		ResponseEntity<Ofertas> Ofertas = restTemplate.getForEntity(api_url+"/Ofertas/"+id+"/", Ofertas.class);
		Ofertas of = Ofertas.getBody();
		return of;
	}
	


}
