package com.aquaclean.aquacleanapp.service;

import java.util.List;

import com.aquaclean.aquacleanapp.model.PedidoDetalles;
import com.aquaclean.aquacleanapp.model.Pedido;

public interface PedidoService {
	public List<PedidoDetalles> findAll();
	public Pedido findById(Long id);
	public PedidoDetalles findPedidoDetallesById(Long id);
	public Pedido save(Pedido p);
	public void update(Pedido p);
	
	public List<PedidoDetalles> findAllByIdRepartidor(Long id);
}
