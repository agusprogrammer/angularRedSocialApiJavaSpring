package com.apirestproyangular.core.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apirestproyangular.core.entity.Fotos;
import com.apirestproyangular.core.repository.FotosRepository;

@Service
public class FotosService {
	
	@Autowired
	private FotosRepository repository;
	
	public Fotos saveFoto(Fotos foto) {
		return repository.save(foto);
	}
	
	public List<Fotos> saveFotos(List<Fotos> fotos) {
		return repository.saveAll(fotos);
	}
	
	public List<Fotos> getFotos(){
		return repository.findAll();
	}
	
	public Fotos getFotoById(int id) {
		return repository.findById(id).orElse(null);
	}
	
	public String deleteFoto(int id) {
		
		String fotoBorrada = "no";
		
		try {
			
			repository.deleteById(id);
			fotoBorrada = "si";
			
		} catch (Exception e) {
			fotoBorrada = "no";
		}
		
		return fotoBorrada;
	}
	
	public Fotos updateFoto (Fotos foto) {
		Fotos existingfoto = repository.findById(foto.getIdFoto()).orElse(null);
		existingfoto.setUsuarioFotos(foto.getUsuarioFotos());
		existingfoto.setFotoString(foto.getFotoString());
		existingfoto.setFechaSubidaFoto(foto.getFechaSubidaFoto());
		existingfoto.setFotoBlob(foto.getFotoBlob());
		return repository.save(existingfoto);
	}
	
	//Metodos propios ---------------------------------------------------
	
	/* buscar fotos del usuario limite de 9 ultimas para el perfil de usuario*/
	public List<Fotos> findFotosPerfilUsuario(Integer idUsu) {
		return repository.findFotosPerfilUsuario(idUsu);
	}
	
	/* buscar fotos de un usuario para la seccion de fotos */
	public List<Fotos> findFotosUsuario(Integer idUsu) {
		return repository.findFotosUsuario(idUsu);
	}
	
	
}
