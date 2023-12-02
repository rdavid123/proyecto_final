package com.aquaclean.aquacleanapp.service;

import java.util.List;

import com.aquaclean.aquacleanapp.model.Servicio;

public interface ServicioService {
	public List<Servicio> findAllServicios();
	public Servicio getServicio(Long id);
}
