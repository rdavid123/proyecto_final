package com.aquaclean.aquacleanapp.service;

import com.aquaclean.aquacleanapp.model.Pago;
import com.aquaclean.aquacleanapp.model.PagoDetalles;

public interface PagoService {
	public Pago findById(Long id);
	public void save(PagoDetalles pago);
	public Double sumarPagos();

}
