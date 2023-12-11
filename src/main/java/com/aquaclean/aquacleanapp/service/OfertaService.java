package com.aquaclean.aquacleanapp.service;

import java.util.List;
import com.aquaclean.aquacleanapp.model.Ofertas;

public interface OfertaService {
	public List<Ofertas> findAllOfertas();
	public Ofertas findById(Long id);
}
