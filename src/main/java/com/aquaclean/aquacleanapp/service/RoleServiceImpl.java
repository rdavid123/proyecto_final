package com.aquaclean.aquacleanapp.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.aquaclean.aquacleanapp.model.Role;

@Service
public class RoleServiceImpl implements RoleService{
	
	@Value("${django.api.url}")
	private String api_url;
	private RestTemplate restTemplate = new RestTemplate();

	@Override
	public Role getRoleCliente() {
		ResponseEntity<Role> response = restTemplate.exchange(api_url+"/roles/2/", HttpMethod.GET, null, Role.class);
		if(response.getStatusCode() == HttpStatus.OK) {
			System.out.println("Rol encontrado");
		}else {
			System.out.println("Rol No encontrados");
		}
		Role rol = response.getBody();
		return rol;
	}

	@Override
	public Role getRoleEmpleado() {
		ResponseEntity<Role> response = restTemplate.exchange(api_url+"/roles/1/", HttpMethod.GET, null, Role.class);
		if(response.getStatusCode() == HttpStatus.OK) {
			System.out.println("Rol encontrado: Empleado");
		}else {
			System.out.println("Rol No encontrados");
		}
		Role rol = response.getBody();
		return rol;
	}

}
