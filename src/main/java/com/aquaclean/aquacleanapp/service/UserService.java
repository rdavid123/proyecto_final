package com.aquaclean.aquacleanapp.service;

import java.util.List;


import org.springframework.security.core.userdetails.UserDetailsService;
import com.aquaclean.aquacleanapp.model.Usuario;
import com.aquaclean.aquacleanapp.model.UsuarioDetalles;

public interface UserService extends UserDetailsService{

	public List<UsuarioDetalles> findAll();
	public List<Usuario> findAllClientes();
	public List<Usuario> findAllEmpleados();
	public List<Usuario> findAllRepartidores();
	public List<Usuario> findAllRepartidoresDisponibles();
	
	public void registerCliente(Usuario usu);
	public void registerAdmin(Usuario usu);
	
	public UsuarioDetalles findUserByEmail(String email);
	public Usuario findUserById(Long id);
	public UsuarioDetalles findUserDetallesById(Long id);
	public void updateUsuario(Usuario u); //no img
}
