package com.aquaclean.aquacleanapp.model;

public class Servicio {
	private Long id;
	private String servicio;
	
	
	public Servicio() {
	}


	public Servicio(Long id, String servicio) {
		super();
		this.id = id;
		this.servicio = servicio;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getServicio() {
		return servicio;
	}


	public void setServicio(String servicio) {
		this.servicio = servicio;
	}
}
