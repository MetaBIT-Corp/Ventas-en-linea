package com.metabit.ventasenlinea.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.metabit.ventasenlinea.entity.EntradaSalida;
import com.metabit.ventasenlinea.entity.Kardex;
import com.metabit.ventasenlinea.repository.EntradaSalidaJpaRepository;
import com.metabit.ventasenlinea.service.EntradaSalidaService;

@Service("entradaSalidaServiceImpl")
public class EntradaSalidaServiceImpl implements EntradaSalidaService{
	
	@Autowired
	@Qualifier("entradaSalidaJpaRespository")
	private EntradaSalidaJpaRepository entradaSalidaRespository;
	
	@Override
	public List<EntradaSalida> getAllEntradaSalida(Kardex kardex_id) {
		return entradaSalidaRespository.findByKardex(kardex_id);
	}

}
