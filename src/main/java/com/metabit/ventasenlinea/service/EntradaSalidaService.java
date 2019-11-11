package com.metabit.ventasenlinea.service;

import java.util.List;

import com.metabit.ventasenlinea.entity.EntradaSalida;
import com.metabit.ventasenlinea.entity.Kardex;

public interface EntradaSalidaService {
	
	public List<EntradaSalida> getAllEntradaSalida(Kardex kardex_id);
}
