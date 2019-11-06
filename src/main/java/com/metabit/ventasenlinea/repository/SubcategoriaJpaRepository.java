package com.metabit.ventasenlinea.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.metabit.ventasenlinea.entity.Subcategoria;

@Repository("subcategoriaJpaRepository")
public interface SubcategoriaJpaRepository extends JpaRepository<Subcategoria, Serializable>{

}
