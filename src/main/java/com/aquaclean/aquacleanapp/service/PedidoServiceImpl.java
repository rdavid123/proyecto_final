package com.aquaclean.aquacleanapp.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.aquaclean.aquacleanapp.model.PedidoDetalles;
import com.aquaclean.aquacleanapp.model.Pedido;

@Service
public class PedidoServiceImpl implements PedidoService{
	
	@Value("${django.api.url}")
	private String api_url;
	
    private RestTemplate restTemplate = new RestTemplate();
    private HttpHeaders headers = new HttpHeaders();

	@Override
	public List<PedidoDetalles> findAll() {
		PedidoDetalles[] array = restTemplate.getForObject(api_url+"/pedidosdetail/", PedidoDetalles[].class);
		List<PedidoDetalles> pedidoDetalles = Arrays.asList(array);
		for(PedidoDetalles p : pedidoDetalles) {
			if(p.getCliente().getAvatar() == null) {
				p.getCliente().setAvatar(api_url+"/imagenes/imagenes/default.jpg");
			}
			if(p.getRepartidor() != null) {
				if(p.getRepartidor().getAvatar() == null) {
					p.getRepartidor().setAvatar(api_url+"/imagenes/imagenes/default.jpg");
				}
				
			}
		}
		return pedidoDetalles;
	}

	@Override
	public Pedido save(Pedido p) {
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Pedido> request = new HttpEntity<>(p, headers);
		restTemplate.exchange(api_url + "/pedidos/", HttpMethod.POST, request, Pedido.class);
		return null;
	}

	@Override
	public void update(Pedido p) {
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Pedido> requestEntity = new HttpEntity<>(p, headers);
		ResponseEntity<Void> response = restTemplate.exchange(api_url + "/pedidos/{id}/", HttpMethod.PUT,requestEntity,Void.class,p.getId() );
		if (response.getStatusCode().is2xxSuccessful()) {
            System.out.println("Pedido editado exitosamente");
        } else {
            System.err.println("Error al editar el pedido. Código de estado: " + response.getStatusCodeValue());
        }
	}

	@Override
	public Pedido findById(Long id) {
		ResponseEntity<Pedido> pedido = restTemplate.getForEntity(api_url+"/pedidos/"+id+"/", Pedido.class);
		Pedido p = pedido.getBody();
		return p;
	}

	@Override
	public PedidoDetalles findPedidoDetallesById(Long id) {
		ResponseEntity<PedidoDetalles> pedido = restTemplate.getForEntity(api_url+"/pedidosdetail/"+id+"/", PedidoDetalles.class);
		PedidoDetalles p = pedido.getBody();
		if(p.getCliente().getAvatar() == null) {
			p.getCliente().setAvatar(api_url+"/imagenes/imagenes/default.jpg");
		}
		if(p.getRepartidor() != null) {
			if(p.getRepartidor().getAvatar() == null) {
				p.getRepartidor().setAvatar(api_url+"/imagenes/imagenes/default.jpg");
			}
		}
		return p;
	}

	@Override
	public List<PedidoDetalles> findAllByIdRepartidor(Long id) {
		PedidoDetalles[] array = restTemplate.getForObject(api_url+"/pedidosdetail/", PedidoDetalles[].class);
		return Arrays.asList(array).stream().filter(p->p.getRepartidor().getId()==id).collect(Collectors.toList());
	
	}

}
