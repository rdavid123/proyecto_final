package com.aquaclean.aquacleanapp.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.aquaclean.aquacleanapp.model.Servicio;
import com.aquaclean.aquacleanapp.model.UsuarioDetalles;

@Service
public class ServicioServiceImpl implements ServicioService{
	@Value("${django.api.url}")
	private String api_url;
	
    private RestTemplate restTemplate = new RestTemplate();
    private HttpHeaders headers = new HttpHeaders();

	@Override
	public List<Servicio> findAllServicios() {
		Servicio[] array = restTemplate.getForObject(api_url+"/servicios/", Servicio[].class);
		List<Servicio> servicios = Arrays.asList(array);
		return servicios;
	}

	@Override
	public Servicio getServicio(Long id) {
		ResponseEntity<Servicio> servicio = restTemplate.getForEntity(api_url+"/servicios/"+id, Servicio.class);
		Servicio s = servicio.getBody();
		return s;
	}

}
