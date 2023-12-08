package com.aquaclean.aquacleanapp.model;

import java.math.BigDecimal;

import org.springframework.format.annotation.DateTimeFormat;

public class Pedido {
	private Long id;
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
	private String fecha_pedido;
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
	private String fecha_entrega;
	private String direccion;
	private String estado;
	private Integer cantidad_prendas;
	private Long servicio;
	private String tipo_entrega;
	private String descripcion;
	private Long cliente;
	private Long repartidor;
	
	private Double peso;
	

	public Pedido() {
	}

	

	
	
	public Pedido(Long id, String fecha_pedido, String fecha_entrega, String direccion, String estado,
			Integer cantidad_prendas, Long servicio, String tipo_entrega, String descripcion, Long cliente,
			Long repartidor, Double peso) {
		super();
		this.id = id;
		this.fecha_pedido = fecha_pedido;
		this.fecha_entrega = fecha_entrega;
		this.direccion = direccion;
		this.estado = estado;
		this.cantidad_prendas = cantidad_prendas;
		this.servicio = servicio;
		this.tipo_entrega = tipo_entrega;
		this.descripcion = descripcion;
		this.cliente = cliente;
		this.repartidor = repartidor;
		this.peso = peso;
	}





	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public String getFecha_pedido() {
		return fecha_pedido;
	}

	public void setFecha_pedido(String fecha_pedido) {
		this.fecha_pedido = fecha_pedido;
	}

	public String getFecha_entrega() {
		return fecha_entrega;
	}


	public void setFecha_entrega(String fecha_entrega) {
		this.fecha_entrega = fecha_entrega;
	}

	public String getDireccion() {
		return direccion;
	}


	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getEstado() {
		return estado;
	}





	public void setEstado(String estado) {
		this.estado = estado;
	}


	public Integer getCantidad_prendas() {
		return cantidad_prendas;
	}





	public void setCantidad_prendas(Integer cantidad_prendas) {
		this.cantidad_prendas = cantidad_prendas;
	}


	public Long getServicio() {
		return servicio;
	}

	public void setServicio(Long servicio) {
		this.servicio = servicio;
	}

	public String getTipo_entrega() {
		return tipo_entrega;
	}

	public void setTipo_entrega(String tipo_entrega) {
		this.tipo_entrega = tipo_entrega;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}





	public Long getCliente() {
		return cliente;
	}





	public void setCliente(Long cliente) {
		this.cliente = cliente;
	}





	public Long getRepartidor() {
		return repartidor;
	}





	public void setRepartidor(Long repartidor) {
		this.repartidor = repartidor;
	}





	public Double getPeso() {
		return peso;
	}





	public void setPeso(Double peso) {
		this.peso = peso;
	}





	private double obtenerTarifaPorEntrega() {
		if(tipo_entrega.equals("1")) {
        	return  6.00;
        }else if(tipo_entrega.equals("2")) {
        	return 3.00;
        }else {
        	return 0;
        }
	}
	
	private double obtenerTarifaPorServicio() {

        if (servicio == (long) 1) {
        	return 3.50; 
        } else if (servicio == (long) 2) {
        	return 6.00; 
        }else {
        	return 0;
        }
	}
	
	public double calcularTotal() {
		double precioServicio = obtenerTarifaPorServicio();
        double precioEntrega = obtenerTarifaPorEntrega();
        return peso * precioServicio + precioEntrega;
        
	};
}
