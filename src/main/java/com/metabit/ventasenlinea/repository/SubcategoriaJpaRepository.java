package com.metabit.ventasenlinea.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.metabit.ventasenlinea.entity.Categoria;
import com.metabit.ventasenlinea.entity.Subcategoria;

@Repository("subcategoriaJpaRepository")
public interface SubcategoriaJpaRepository extends JpaRepository<Subcategoria, Serializable>{
	public abstract List<Subcategoria> findAllByCategoria(Categoria categoria);
}
