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
	
	// Buscar nuevos amigos de usuario (incluyendo los que estan)
	/*
	@Query("SELECT u FROM Usuario u "
			+ "WHERE u.idUsu NOT IN(SELECT us.idUsu FROM Usuario us WHERE us.idUsu = ?1) "
			+ "ORDER BY u.fechaAlta DESC")
	List<Usuario> findNuevosAmigos(Integer idUsu);
	*/
	
	// Buscar nuevos amigos de usuario (Sin incluir los que estan en las peticiones,
	// ya sean pendientes o en amigos)
	@Query("SELECT u FROM Usuario u "
			+ "WHERE u.idUsu NOT IN(SELECT am.idUsuAm.idReceptor FROM AmigosUsu am WHERE am.idUsuAm.idReceptor = ?1 OR am.idUsuAm.idSolicitante = ?1) "
			+ "AND u.idUsu NOT IN(SELECT amUsu.idUsuAm.idSolicitante FROM AmigosUsu amUsu WHERE amUsu.idUsuAm.idSolicitante = ?1 OR amUsu.idUsuAm.idReceptor = ?1) "
			+ "ORDER BY u.fechaAlta DESC")
	List<Usuario> findNuevosAmigos(Integer idUsu);
	
	
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
