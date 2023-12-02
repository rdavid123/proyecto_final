package com.aquaclean.aquacleanapp.model;

public class Prendas {
	private String nombre;
	private Integer peso;
	private String categoria;
	
	public Prendas() {
		
	}
	
	public Prendas(String nombre, Integer peso, String categoria) {
		super();
		this.nombre = nombre;
		this.peso = peso;
		this.categoria = categoria;
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Integer getPeso() {
		return peso;
	}
	public void setPeso(Integer peso) {
		this.peso = peso;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
}
