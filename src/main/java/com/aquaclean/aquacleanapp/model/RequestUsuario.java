package com.aquaclean.aquacleanapp.model;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class RequestUsuario {
	private Long id;
	private String nombre;
	private String apellido;
	private String correo;
	private String telefono;
	private String dni;
	private MultipartFile avatar;
	private String password;
	private Long rol;
	private Boolean estado_repartidor;
	
	public RequestUsuario() {
		
	}

	public RequestUsuario(Long id, String nombre, String apellido, String correo, String telefono, String dni,
			MultipartFile avatar, String password, Long rol, Boolean estado_repartidor) {
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.correo = correo;
		this.telefono = telefono;
		this.dni = dni;
		this.avatar = avatar;
		this.password = password;
		this.rol = rol;
		this.estado_repartidor = estado_repartidor;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public MultipartFile getAvatar() {
		return avatar;
	}

	public void setAvatar(MultipartFile avatar) {
		this.avatar = avatar;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getRol() {
		return rol;
	}

	public void setRol(Long rol) {
		this.rol = rol;
	}

	public Boolean getEstado_repartidor() {
		return estado_repartidor;
	}

	public void setEstado_repartidor(Boolean estado_repartidor) {
		this.estado_repartidor = estado_repartidor;
	}
}
