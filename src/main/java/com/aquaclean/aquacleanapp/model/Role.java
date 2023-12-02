package com.aquaclean.aquacleanapp.model;

public class Role {
	private Integer id;
	private String rol;
	private String descripcion;
	
	public Role() {
		
	}
	
	public Role(Integer id, String rol, String descripcion) {
		this.id = id;
		this.rol = rol;
		this.descripcion = descripcion;
	}
	public Role(String rol, String descripcion) {
		this.rol = rol;
		this.descripcion = descripcion;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
}
