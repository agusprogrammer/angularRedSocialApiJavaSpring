package com.apirestproyangular.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.apirestproyangular.core.entity.Fotos;

public interface FotosRepository extends JpaRepository<Fotos, Integer>{

	/* buscar fotos del usuario limite de 9 ultimas para el perfil de usuario*/
	
	@Query("SELECT f FROM Fotos f "
			+ "WHERE f.usuarioFotos.idUsu = ?1 "
			+ "ORDER BY f.fechaSubidaFoto DESC")
	
	// LIMIT 9
	
	List<Fotos> findFotosPerfilUsuario(Integer idUsu);
	
	
	/* buscar fotos de un usuario para la seccion de fotos */
	
	@Query("SELECT f FROM Fotos f "
			+ "WHERE f.usuarioFotos.idUsu = ?1 "
			+ "ORDER BY f.fechaSubidaFoto DESC")
	
	List<Fotos> findFotosUsuario(Integer idUsu);
	
	
	
}
