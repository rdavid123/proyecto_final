package com.aquaclean.aquacleanapp.model;

public class UsuarioDetalles {
	private Long id;
	private String nombre;
	private String apellido;
	private String correo;
	private String telefono;
	private String dni;
	private String avatar;
	private String password;
	private Role rol;
	
	public UsuarioDetalles() {
		
	}

	public UsuarioDetalles(Long id, String nombre, String apellido, String correo, String telefono, String dni,
			String avatar, String password, Role rol) {
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.correo = correo;
		this.telefono = telefono;
		this.dni = dni;
		this.avatar = avatar;
		this.password = password;
		this.rol = rol;
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

	public String getAvatar() {
		return avatar;
	}


	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}


	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public Role getRol() {
		return rol;
	}


	public void setRol(Role rol) {
		this.rol = rol;
	}


	public String getFullName() {
		return nombre + " " + apellido;
	}

	public Boolean darkMode() {
		Boolean dark_mode = false;
		return dark_mode;
	}
}
