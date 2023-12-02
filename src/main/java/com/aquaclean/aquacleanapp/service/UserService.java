package com.aquaclean.aquacleanapp.service;

import java.util.List;


import org.springframework.security.core.userdetails.UserDetailsService;

import com.aquaclean.aquacleanapp.model.Usuario;
import com.aquaclean.aquacleanapp.model.UsuarioDetalles;

public interface UserService extends UserDetailsService{

	public List<UsuarioDetalles> listUsers();
	
	public void registerCliente(Usuario usu);
	public void registerAdmin(Usuario usu);
	
	public UsuarioDetalles findUserByEmail(String email);
	public Usuario findUserById(Long id);
	public UsuarioDetalles findUserDetallesById(Long id);
	public void update(Usuario u);
}
