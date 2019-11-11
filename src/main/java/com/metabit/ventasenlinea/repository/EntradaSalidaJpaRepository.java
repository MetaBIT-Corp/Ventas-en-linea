package com.metabit.ventasenlinea.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.metabit.ventasenlinea.entity.EntradaSalida;
import com.metabit.ventasenlinea.entity.Kardex;

@Repository("entradaSalidaJpaRespository")
public interface EntradaSalidaJpaRepository extends JpaRepository<EntradaSalida, Serializable>{
	
	public List<EntradaSalida> findByKardex(Kardex kardex_id);
}
