package com.apirestproyangular.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.apirestproyangular.core.entity.AmigosUsu;
import com.apirestproyangular.core.entity.AmigosUsuId;
import com.apirestproyangular.core.entity.Usuario;

public interface AmigosUsuRepository extends JpaRepository <AmigosUsu, AmigosUsuId>{

	/*En el programa se usara ids para buscar y luego se obtendra los objetos usuario*/
	/*manera optima*/

	/*Nota: mirar de coger id u objetos (en este caso el id)*/
	
	
	// SELECT am.idUsuAm.idSolicitante, am.idUsuAm.idReceptor, "
	//+ "am.solicitudAceptada, am.fechaEnviada "
	//+ "FROM ...
	
	//antes: am.idUsuAm.idSolicitante = ?1 OR am.idUsuAm.idReceptor = ?2

	
	
	/*buscar amigos del usuario*/
	@Query("SELECT am FROM AmigosUsu am "
			+ "WHERE (am.usuAmIdSolicitante.idUsu = ?1 OR am.usuAmIdReceptor.idUsu = ?2) "
			+ "AND am.solicitudAceptada = 1")
	List<AmigosUsu> findAmigosUsuario(Integer idUsuSolicitud, Integer idUsuRecep);
	// List<AmigosUsu> findAmigosUsuario(Usuario idUsuSolicitud, Usuario idUsuRecep);
	
	/*buscar peticiones de amistad del usuario en general (sin identificar enviadas recibidas)*/
	@Query("SELECT am FROM AmigosUsu am "
			+ "WHERE (am.usuAmIdSolicitante.idUsu = ?1 OR am.usuAmIdReceptor.idUsu = ?2) "
			+ "AND am.solicitudAceptada = 0")
	List<AmigosUsu> findPeticionesEnGeneralUsuario(Integer idUsuSolicitud, Integer idUsuRecep);
	
	
	/* buscar peticiones de amistad realizadas por el usuario*/
	@Query("SELECT am FROM AmigosUsu am "
			+ "WHERE (am.usuAmIdSolicitante.idUsu = ?1) "
			+ "AND am.solicitudAceptada = 0")
	List<AmigosUsu> findPeticionesRealizadasUsuario(Integer idUsuSolicitud);
	
	/* buscar peticiones recibidas por el usuario*/
	@Query("SELECT am FROM AmigosUsu am "
			+ "WHERE (am.usuAmIdReceptor.idUsu = ?1) "
			+ "AND am.solicitudAceptada = 0")
	List<AmigosUsu> findPeticionesRecibidasUsuario(Integer idUsuRecep);
	
	
}
