package com.aquaclean.aquacleanapp.model;

public class Servicio {
	private Long id;
	private String titulo;
	private String descripcion;
	private Double precio;
	private String imagen;
	
	public Servicio() {
	}

	public Servicio(Long id, String titulo, String descripcion, Double precio, String imagen) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.precio = precio;
		this.imagen = imagen;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

}
