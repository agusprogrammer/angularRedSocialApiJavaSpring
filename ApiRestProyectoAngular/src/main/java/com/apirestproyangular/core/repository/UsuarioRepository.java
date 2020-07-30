package com.apirestproyangular.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.apirestproyangular.core.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
	
	//Metodos propios
	// @Query("SELECT u FROM Usuario u WHERE u.emailEntrada = ?1 AND u.contrasenya = ?2")
	// @Query("SELECT u FROM Usuario u")
	// List<Usuario> findUsuLogin(String emailent, String contrasenya);
	
	//Nota: si se le pasan parametros, tiene que cogerse los datos uno por uno
	@Query("SELECT u.idUsu FROM Usuario u WHERE u.emailEntrada = ?1 AND u.contrasenya = ?2")
	Integer findUsuLogin(String emailent, String contrasenya);
	
	//Obtener ultimo id
	@Query("SELECT max(u.idUsu) FROM Usuario u ")
	Integer findLastIdUsuario();
	
	//Buscar amigos -----------------------------------------------------
	
	@Query("SELECT u FROM Usuario u "
			+ "WHERE u.idUsu NOT IN(SELECT us.idUsu FROM Usuario us WHERE us.idUsu = ?1) "
			+ "ORDER BY u.fechaAlta DESC")
	List<Usuario> findNuevosAmigos(Integer idUsu);
	
	
	/*
	@Query("SELECT u FROM Usuario u "
			+ "WHERE u.idUsu NOT IN(SELECT us.idUsu FROM Usuario us WHERE us.idUsu = ?1) "
			+ "AND (u.amigosUsuSolicitudes NOT IN(SELECT am FROM AmigosUsu am WHERE (am.usuAmIdSolicitante.idUsu = ?2 OR am.usuAmIdReceptor.idUsu = ?3) AND am.solicitudAceptada = 1) "
			+ "OR u.amigosUsuRecibidos NOT IN(SELECT am FROM AmigosUsu am WHERE (am.usuAmIdSolicitante.idUsu = ?4 OR am.usuAmIdReceptor.idUsu = ?5) AND am.solicitudAceptada = 1))"
			+ "ORDER BY u.fechaAlta DESC")
	List<Usuario> findNuevosAmigos(Integer idUsu, Integer idUsu2, Integer idUsu3, Integer idUsu4, Integer idUsu5);
	*/
	
	
	/* select para buscar nuevos amigos por pais*/
	@Query("SELECT u FROM Usuario u WHERE u.pais like ?1 "
			+ "ORDER BY u.fechaAlta DESC")
	List<Usuario> findNuevosAmigosPais(String pais);
	
	/* select para buscar nuevos amigos por region*/
	@Query("SELECT u FROM Usuario u WHERE u.region like ?1 "
			+ "ORDER BY u.fechaAlta DESC")
	List<Usuario> findNuevosAmigosRegion(String region);
	
	/* select para buscar nuevos amigos por ciudad*/
	@Query("SELECT u FROM Usuario u WHERE u.ciudad like ?1 "
			+ "ORDER BY u.fechaAlta DESC")
	List<Usuario> findNuevosAmigosCiudad(String ciudad);
	
	/* Select para buscar un usuario por nombre de usuario*/
	@Query("SELECT u FROM Usuario u WHERE u.nombreUsu like ?1 "
			+ "ORDER BY u.fechaAlta DESC")
	List<Usuario> findUsuarioNombreUsu(String nombreUsu);
	
}
