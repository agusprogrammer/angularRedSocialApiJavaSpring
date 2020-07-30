package com.apirestproyangular.core.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apirestproyangular.core.entity.Comentario;
import com.apirestproyangular.core.repository.ComentarioRepository;


@Service
public class ComentarioService {

	@Autowired
	private ComentarioRepository repository;
	
	public Comentario saveComentario(Comentario comment) {
		return repository.save(comment);
	}
	
	public List<Comentario> saveComentario(List<Comentario> comment) {
		return repository.saveAll(comment);
	}
	
	public List<Comentario> getComentarios(){
		return repository.findAll();
	}
	
	public Comentario getComentarioById(int id) {
		return repository.findById(id).orElse(null);
	}
	
	public String deleteComentario(int id) {
		repository.deleteById(id);
		return "Comentario removed !! " + id;
	}
	
	public Comentario updateComentario (Comentario comment) {
		Comentario existingComentario = repository.findById(comment.getIdComentario()).orElse(null);
		existingComentario.setUsuarioComent(comment.getUsuarioComent());
		existingComentario.setPublComent(comment.getPublComent());
		existingComentario.setTextoComentario(comment.getTextoComentario());
		existingComentario.setFechaCreacionCom(comment.getFechaCreacionCom());
		return repository.save(existingComentario);
	}
	
	//Metodos propios ---------------------------------------------------------------
	
	//Buscar comentarios de una publicacion
	public List<Comentario> findComentariosPublicacion(Integer idPublicacion) {
		
		ArrayList<Comentario> listaComment = new ArrayList<Comentario>();
		listaComment = (ArrayList<Comentario>) repository.findComentariosPublicacion(idPublicacion);
		return listaComment;
		
	}
	
	
	
}
