package com.apirestproyangular.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.apirestproyangular.core.entity.Entradas;

public interface EntradasRepository extends JpaRepository<Entradas, Integer>{
	
	// busca las entradas de un usuario concreto
	// Nota, si se pone el objeto, no hay que poner los campos
	
	@Query("SELECT ent FROM Entradas ent "
			+ "WHERE ent.usuarioEntradas.idUsu = ?1")
	List<Entradas> findEntrUsuario(Integer idUsu);
	
	
}
