package com.apirestproyangular.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.apirestproyangular.core.entity.Comentario;


public interface ComentarioRepository extends JpaRepository<Comentario, Integer>{
	
	//Buscar comentarios de una publicacion
	@Query("SELECT c FROM Comentario c "
			+ "WHERE c.publComent.idPublicacion = ?1")
	List<Comentario> findComentariosPublicacion(Integer idPublicacion);
	
	
}
