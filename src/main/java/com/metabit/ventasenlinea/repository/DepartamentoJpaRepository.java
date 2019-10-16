package com.metabit.ventasenlinea.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.metabit.ventasenlinea.entity.Departamento;

@Repository("departamentoJpaRepository")
public interface DepartamentoJpaRepository extends JpaRepository<Departamento, Serializable>{

}
