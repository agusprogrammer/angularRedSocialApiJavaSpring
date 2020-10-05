package com.apirestproyangular.core.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apirestproyangular.core.entity.Publicacion;
import com.apirestproyangular.core.repository.PublicacionRepository;

@Service
public class PublicacionService {

	@Autowired
	private PublicacionRepository repository;
	
	public Publicacion savePublicacion(Publicacion publicacion) {
		return repository.save(publicacion);
	}
	
	public List<Publicacion> savePublicaciones(List<Publicacion> publicaciones) {
		return repository.saveAll(publicaciones);
	}
	
	public List<Publicacion> getPublicaciones(){
		return repository.findAll();
	}
	
	public Publicacion getPublicacionById(int id) {
		return repository.findById(id).orElse(null);
	}
	
	public String deletePublicacion(int id) {
		
		String publicacionBorrada = "no";
		
		try {
			
			repository.deleteById(id);
			publicacionBorrada = "si";
			
		} catch (Exception e) {
			publicacionBorrada = "no";
		}
		
		return publicacionBorrada;
	}
	
	public Publicacion updatePublicacion(Publicacion publicacion) {
		Publicacion existingPublicacion = repository.findById(publicacion.getIdPublicacion()).orElse(null);
		existingPublicacion.setUsuarioPubl(publicacion.getUsuarioPubl());
		existingPublicacion.setTituloPublicacion(publicacion.getTituloPublicacion());
		existingPublicacion.setTextoPublicacion(publicacion.getTextoPublicacion());
		existingPublicacion.setFechaCreacionPub(publicacion.getFechaCreacionPub());
		existingPublicacion.setPubEsPrivada(publicacion.getPubEsPrivada());
		return repository.save(existingPublicacion);
	}
	
	// metodos propios -------------------------------------------------------
	
	/*Publicaciones para el inicio de un usuario (amigos del usuario)*/
	public List<Publicacion> findPubliInicioUsuario(Integer idUsu) {
		
		ArrayList<Publicacion> publInicio = new ArrayList<>();
		publInicio = (ArrayList<Publicacion>) repository.findPubliInicioUsuario(idUsu);
		return publInicio;
		
	}
	
	/*Publicaciones publicas para la seccion de explorar*/
	public List<Publicacion> findPublicacionesPubl() {
		
		ArrayList<Publicacion> publPublicas = new ArrayList<>();
		publPublicas = (ArrayList<Publicacion>) repository.findPublicacionesPubl();
		return publPublicas;
	}
	
	/*Publicaciones de un usuario*/
	public List<Publicacion> findPublicacionesUsuario(Integer idUsu) {
		
		ArrayList<Publicacion> publUsuario = new ArrayList<>();
		publUsuario = (ArrayList<Publicacion>) repository.findPublicacionesUsuario(idUsu);
		return publUsuario;
	}
	
	
}
