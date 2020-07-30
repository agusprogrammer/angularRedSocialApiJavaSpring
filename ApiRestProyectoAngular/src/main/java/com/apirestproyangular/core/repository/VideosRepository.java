package com.apirestproyangular.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.apirestproyangular.core.entity.Videos;

public interface VideosRepository extends JpaRepository<Videos, Integer> {
	
	/* buscar videos del usuario limite de 9 ultimas*/
	
	@Query("SELECT v from Videos v "
			+ "WHERE v.usuarioVideos.idUsu = ?1 "
			+ "ORDER BY v.fechaSubidaVid DESC")
	
	//Nota los limit es error de sintaxis LIMIT 9
	
	List<Videos> findVideosPerfilUsuario(Integer idUsu);
	
	/* buscar videos de un usuario*/
	
	@Query("SELECT v FROM Videos v "
			+ "WHERE v.usuarioVideos.idUsu = ?1 "
			+ "ORDER BY v.fechaSubidaVid DESC")
	
	List<Videos> findVideosUsuario(Integer idUsu);
	
	
}
