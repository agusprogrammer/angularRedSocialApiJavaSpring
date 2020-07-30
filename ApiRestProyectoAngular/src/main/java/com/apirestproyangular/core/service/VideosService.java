package com.apirestproyangular.core.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apirestproyangular.core.entity.Videos;
import com.apirestproyangular.core.repository.VideosRepository;

@Service
public class VideosService {

	@Autowired
	private VideosRepository repository;
	
	public Videos saveVideo(Videos vid) {
		return repository.save(vid);
	}
	
	public List<Videos> saveVideos(List<Videos> vid) {
		return repository.saveAll(vid);
	}
	
	public List<Videos> getVideos(){
		return repository.findAll();
	}
	
	public Videos getVideoById(int id) {
		return repository.findById(id).orElse(null);
	}
	
	public String deleteVideo(int id) {
		repository.deleteById(id);
		return "Video removed !! " + id;
	}
	
	public Videos updateVideo (Videos vid) {
		Videos existingVideo = repository.findById(vid.getIdVideo()).orElse(null);
		existingVideo.setUsuarioVideos(vid.getUsuarioVideos());
		existingVideo.setVideoString(vid.getVideoString());
		existingVideo.setFechaSubidaVid(vid.getFechaSubidaVid());
		existingVideo.setVideoBlob(vid.getVideoBlob());
		return repository.save(existingVideo);
	}
	
	//Metodos propios -----------------------------------------------------
	
	/* buscar videos del usuario limite de 9 ultimas*/
	public List<Videos> findVideosPerfilUsuario(Integer idUsu) {
		return repository.findVideosPerfilUsuario(idUsu);
	}
	
	/* buscar videos de un usuario*/
	public List<Videos> findVideosUsuario(Integer idUsu) {
		return repository.findVideosUsuario(idUsu);
	}
	
}
