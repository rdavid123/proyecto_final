package com.aquaclean.aquacleanapp.model;

public class PagoDetalles {
	private Pedido pedido;
	private Double monto;
	private String fecha_pago;
	private String transaccion_id;
	
	public PagoDetalles() {
		
	}

	public PagoDetalles(Pedido pedido, Double monto, String fecha_pago, String transaccion_id) {
		super();
		this.pedido = pedido;
		this.monto = monto;
		this.fecha_pago = fecha_pago;
		this.transaccion_id = transaccion_id;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public Double getMonto() {
		return monto;
	}

	public void setMonto(Double monto) {
		this.monto = monto;
	}

	public String getFecha_pago() {
		return fecha_pago;
	}

	public void setFecha_pago(String fecha_pago) {
		this.fecha_pago = fecha_pago;
	}

	public String getTransaccion_id() {
		return transaccion_id;
	}

	public void setTransaccion_id(String transaccion_id) {
		this.transaccion_id = transaccion_id;
	}
	
	
	
}
