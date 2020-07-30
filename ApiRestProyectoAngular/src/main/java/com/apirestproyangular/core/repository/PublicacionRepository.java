package com.apirestproyangular.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.apirestproyangular.core.entity.Publicacion;

public interface PublicacionRepository extends JpaRepository<Publicacion, Integer> {
	
	/*Publicaciones para el inicio de un usuario (amigos del usuario)*/
	@Query("SELECT p FROM Publicacion p WHERE p.usuarioPubl.idUsu = ?1 " +
			"AND p.usuarioPubl.idUsu IN(SELECT am.usuAmIdSolicitante.idUsu FROM AmigosUsu am WHERE am.solicitudAceptada = 1) " +
			"OR p.usuarioPubl.idUsu IN(SELECT a.usuAmIdReceptor.idUsu FROM AmigosUsu a WHERE a.solicitudAceptada = 1) " +
			"ORDER BY p.fechaCreacionPub DESC")
	List<Publicacion> findPubliInicioUsuario(Integer idUsu);
	
	/*Publicaciones publicas para la seccion de explorar*/
	@Query("SELECT p FROM Publicacion p WHERE p.pubEsPrivada = 0 " + 
			"ORDER BY p.fechaCreacionPub DESC")
	List<Publicacion> findPublicacionesPubl();
	
	/*Publicaciones de un usuario*/
	@Query("SELECT p FROM Publicacion p WHERE p.usuarioPubl.idUsu = ?1 " +
			"ORDER BY p.fechaCreacionPub DESC")
	List<Publicacion> findPublicacionesUsuario(Integer idUsu);
}
