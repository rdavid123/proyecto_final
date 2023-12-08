package com.aquaclean.aquacleanapp.model;

public class StripePaymentRequest {
	

    private Long amount;
    private String email;
    private String productName;
    private String id_cliente;
    private String tipo_entrega;
    private String fecha_pedido;
    private String fecha_entrega;
    private String direccion;
    private String id_servicio;
    private String cantidad_prendas;
    private String id_repartidor;

	public StripePaymentRequest() {
	}



	public StripePaymentRequest(Long amount, String email, String productName, String id_cliente, String tipo_entrega,
			String fecha_pedido, String fecha_entrega, String direccion, String id_servicio, String cantidad_prendas,
			String id_repartidor) {
		super();
		this.amount = amount;
		this.email = email;
		this.productName = productName;
		this.id_cliente = id_cliente;
		this.tipo_entrega = tipo_entrega;
		this.fecha_pedido = fecha_pedido;
		this.fecha_entrega = fecha_entrega;
		this.direccion = direccion;
		this.id_servicio = id_servicio;
		this.cantidad_prendas = cantidad_prendas;
		this.id_repartidor = id_repartidor;
	}


	public Long getAmount() {
		return amount;
	}


	public void setAmount(Long amount) {
		this.amount = amount;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getProductName() {
		return productName;
	}


	public void setProductName(String productName) {
		this.productName = productName;
	}


	public String getId_cliente() {
		return id_cliente;
	}


	public void setId_cliente(String id_cliente) {
		this.id_cliente = id_cliente;
	}

	public String getTipo_entrega() {
		return tipo_entrega;
	}


	public void setTipo_entrega(String tipo_entrega) {
		this.tipo_entrega = tipo_entrega;
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

	public String getCantidad_prendas() {
		return cantidad_prendas;
	}



	public void setCantidad_prendas(String cantidad_prendas) {
		this.cantidad_prendas = cantidad_prendas;
	}



	public String getId_repartidor() {
		return id_repartidor;
	}



	public void setId_repartidor(String id_repartidor) {
		this.id_repartidor = id_repartidor;
	}



	public String getId_servicio() {
		return id_servicio;
	}



	public void setId_servicio(String id_servicio) {
		this.id_servicio = id_servicio;
	}
    
    
}
