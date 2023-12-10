package com.aquaclean.aquacleanapp.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.aquaclean.aquacleanapp.model.Usuario;
import com.aquaclean.aquacleanapp.model.UsuarioDetalles;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

@Service
public class UserServiceImpl implements UserService{

	@Value("${django.api.url}")
	private String api_url;
    private RestTemplate restTemplate = new RestTemplate();
    private HttpHeaders headers = new HttpHeaders();
    
    @Autowired
	private BCryptPasswordEncoder passwordEncoder;
    
	@Override
	public List<UsuarioDetalles> findAll() {
		UsuarioDetalles[] array = restTemplate.getForObject(api_url+"/users_detail/", UsuarioDetalles[].class);
		List<UsuarioDetalles> usuarioDetalles = Arrays.asList(array);
		return usuarioDetalles;
	}
	
	@Override
	public List<Usuario> findAllClientes() {
		Usuario[] array = restTemplate.getForObject(api_url+"/clientes/", Usuario[].class);
		List<Usuario> clientes = Arrays.asList(array);
		for(Usuario u : clientes) {
			if(u.getAvatar() == null) {
				u.setAvatar(api_url+"/imagenes/imagenes/default.jpg");
			}
		}
		return clientes;
	}
	@Override
	public List<Usuario> findAllEmpleados() {
		Usuario[] array = restTemplate.getForObject(api_url+"/empleados/", Usuario[].class);
		List<Usuario> empleados = Arrays.asList(array);
		for(Usuario u : empleados) {
			if(u.getAvatar() == null) {
				u.setAvatar(api_url+"/imagenes/imagenes/default.jpg");
			}
		}
		return empleados;
	}
	@Override
	public List<Usuario> findAllRepartidores() {
		Usuario[] array = restTemplate.getForObject(api_url+"/repartidores/", Usuario[].class);
		List<Usuario> repartidores = Arrays.asList(array);
		for(Usuario u : repartidores) {
			if(u.getAvatar() == null) {
				u.setAvatar(api_url+"/imagenes/imagenes/default.jpg");
			}
		}
		return repartidores;
	}
	
	@Override
	public void registerAdmin(Usuario usu) {
	    usu.setPassword(passwordEncoder.encode(usu.getPassword()));
	    usu.setRol((long) 1);
	    System.out.println(usu.toString());
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    HttpEntity<Usuario> request = new HttpEntity<>(usu, headers);
	    restTemplate.exchange(api_url+"/users/",HttpMethod.POST, request, Usuario.class);
	}

	@Override
	public void registerCliente(Usuario usu) {
	    usu.setPassword(passwordEncoder.encode(usu.getPassword()));
	    usu.setRol((long) 2);
	    System.out.println(usu.toString());
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    HttpEntity<Usuario> request = new HttpEntity<>(usu, headers);
	    restTemplate.exchange(api_url+"/users/",HttpMethod.POST, request, Usuario.class);
	}


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	    ResponseEntity<UsuarioDetalles> u = restTemplate.getForEntity(api_url+"/users/email/"+username+"/", UsuarioDetalles.class);
	    UsuarioDetalles user = u.getBody();
	    if(user == null) {
	        throw new UsernameNotFoundException("UsuarioDetalles no encontrado");
	    }
	    Collection<? extends GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority(user.getRol().getRol()));
	    User usr = new User(user.getCorreo(), user.getPassword(), authorities);
	    return usr;
	}

	@Override
	public UsuarioDetalles findUserByEmail(String email) {
		ResponseEntity<UsuarioDetalles> u = restTemplate.getForEntity(api_url+"/users/email/"+email+"/", UsuarioDetalles.class);
	    UsuarioDetalles user = u.getBody();
		return user;
	}

	@Override
	public Usuario findUserById(Long id) { //for post, put
		ResponseEntity<Usuario> response = restTemplate.getForEntity(api_url+"/users/"+id+"/", Usuario.class);
		Usuario user = response.getBody();
		if(user.getAvatar() == null) {
			user.setAvatar(api_url+"/imagenes/imagenes/default.jpg");
		}
		return user;
	}
	
	@Override
	public UsuarioDetalles findUserDetallesById(Long id) { //for only get
		ResponseEntity<UsuarioDetalles> response = restTemplate.getForEntity(api_url+"/users_detail/"+id+"/", UsuarioDetalles.class);
		UsuarioDetalles user = response.getBody();
		if(user.getAvatar() == null) {
			user.setAvatar(api_url+"/imagenes/imagenes/default.jpg");
		}
		return user;
	}


	@Override
	public void updateUsuario(Usuario u) {
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Usuario> requestEntity = new HttpEntity<>(u, headers);
		ResponseEntity<Void> response = restTemplate.exchange(api_url + "/usersupdate/{id}/", HttpMethod.PUT,requestEntity,Void.class,u.getId());
		if (response.getStatusCode().is2xxSuccessful()) {
            System.out.println("usuario editado exitosamente");
        } else {
            System.err.println("Error al editar el usuario. Código de estado: " + response.getStatusCodeValue());
        }
	}

	@Override
	public List<Usuario> findAllRepartidoresDisponibles() {
		Usuario[] array = restTemplate.getForObject(api_url+"/repartidores/", Usuario[].class);
		List<Usuario> repartidores = Arrays.asList(array).stream().filter(r -> r.getEstado_repartidor()== true).collect(Collectors.toList());
		for(Usuario u : repartidores) {
			if(u.getAvatar() == null) {
				u.setAvatar(api_url+"/imagenes/imagenes/default.jpg");
			}
		}
		return repartidores;
	}
}
