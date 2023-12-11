package com.aquaclean.aquacleanapp.model;

import org.springframework.format.annotation.DateTimeFormat;

public class Ofertas {
	
	private Long id;
	private String place_image;
	private String titulo;
	private String descripcion;
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
	private String fecha_creacion;
	private Double precio;
	

	public Ofertas() {
		//Este es el constructor predeterminado de la clase Ofertas.
		
	}
	
	//Este es el constructor de la clase Ofertas
	//que toma todos los campos de la clase como parámetros.
	
	public Ofertas(Long id, String place_imagen,
			String titulo, String descripcion, String fecha_creacion, Double precio) { 
		super();
		
		this.id = id;
		this.place_image = place_image;
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.fecha_creacion = fecha_creacion;
		this.precio = precio;
		
	} 
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	


	public String getPlace_image() {
		return place_image;
	}

	public void setPlace_image(String place_image) {
		this.place_image = place_image;
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
	
	public String getFecha_creacion() {
		return fecha_creacion;
	}

	public void setFecha_creacion(String fecha_creacion) {
		this.fecha_creacion = fecha_creacion;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	
	
}

	

