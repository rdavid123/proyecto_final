package com.aquaclean.aquacleanapp.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.aquaclean.aquacleanapp.model.Usuario;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@Service
public class DjangoAuthService {
	
	@Value("${django.api.url}")
    private String djangoApiUrl;
	
	
    public ResponseEntity<Usuario> authenticate(String correo, String password) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Construir el cuerpo de la solicitud
        String requestBody = String.format("{\"correo\": \"%s\", \"password\": \"%s\"}", correo, password);
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        // Realizar la solicitud POST a la API Django
        return restTemplate.postForEntity(djangoApiUrl+"/api/token/", request, Usuario.class);
    }
}