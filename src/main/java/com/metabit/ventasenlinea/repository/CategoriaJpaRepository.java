package com.metabit.ventasenlinea.repository;

import java.io.Serializable;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.metabit.ventasenlinea.entity.Categoria;
import com.metabit.ventasenlinea.entity.Departamento;

@Repository("categoriaJpaRepository")
public interface CategoriaJpaRepository extends JpaRepository<Categoria, Serializable>{

	public List<Categoria> findByDepartamento(Departamento departamento);
}
