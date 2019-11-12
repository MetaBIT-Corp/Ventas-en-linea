package com.metabit.ventasenlinea.service;

import java.util.List;

import com.metabit.ventasenlinea.entity.Kardex;

public interface KardexService {
	public List<Kardex> getAllKardex();
	
	public Kardex getKardex(int id);
	
	public void addKardex(Kardex kardex);
}
