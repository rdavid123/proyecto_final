package com.aquaclean.aquacleanapp.model;

import java.util.Date;


public class PedidoDetalles {
	private Long id;
	private Date fecha_pedido;
	private Date fecha_entrega;
	private String direccion;
	private String estado;
	private Integer cantidad_prendas;
	private Servicio servicio;
	private String tipo_entrega;
	private String descripcion;
	private UsuarioDetalles cliente;
	
	private UsuarioDetalles repartidor;
	
	private Double peso;
	
	public PedidoDetalles() {
		
	}

	public PedidoDetalles(Long id, Date fecha_pedido, Date fecha_entrega, String direccion, String estado, Integer cantidad_prendas,
			Servicio servicio, String tipo_entrega, String descripcion,UsuarioDetalles cliente,
			UsuarioDetalles repartidor, Double peso) {
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

	public Date getFecha_pedido() {
		return fecha_pedido;
	}

	public void setFecha_pedido(Date fecha_pedido) {
		this.fecha_pedido = fecha_pedido;
	}

	public Date getFecha_entrega() {
		return fecha_entrega;
	}

	public void setFecha_entrega(Date fecha_entrega) {
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

	public Servicio getServicio() {
		return servicio;
	}

	public void setServicio(Servicio servicio) {
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


	public UsuarioDetalles getCliente() {
		return cliente;
	}

	public void setCliente(UsuarioDetalles cliente) {
		this.cliente = cliente;
	}

	public UsuarioDetalles getRepartidor() {
		return repartidor;
	}

	public void setRepartidor(UsuarioDetalles repartidor) {
		this.repartidor = repartidor;
	}

	public Double getPeso() {
		return peso;
	}

	public void setPeso(Double peso) {
		this.peso = peso;
	}

	

	
}
