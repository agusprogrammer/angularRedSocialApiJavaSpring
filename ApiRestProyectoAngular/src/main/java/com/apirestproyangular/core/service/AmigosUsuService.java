package com.apirestproyangular.core.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apirestproyangular.core.entity.AmigosUsu;
import com.apirestproyangular.core.entity.AmigosUsuId;
import com.apirestproyangular.core.entity.Usuario;
import com.apirestproyangular.core.repository.AmigosUsuRepository;

@Service
public class AmigosUsuService implements Serializable {
	
	@Autowired
	private AmigosUsuRepository repository;
	
	public AmigosUsu saveAmigosUsu(AmigosUsu amigosUsu) {
		return repository.save(amigosUsu);
	}
	
	public List<AmigosUsu> saveAmigosUsu(List<AmigosUsu> amigosUsu) {
		return repository.saveAll(amigosUsu);
	}
	
	public List<AmigosUsu> getAmigosUsu(){
		return repository.findAll();
	}
	
	public AmigosUsu getAmigosUsuById(AmigosUsuId id) {
		return repository.findById(id).orElse(null);
	}

	public String deleteAmigosUsu(AmigosUsuId id) {
		repository.deleteById(id);
		return "AmigosUsu removed !! " + id.toString();
	}
	
	public AmigosUsu updateAmigosUsu (AmigosUsu amigosUsu) {
		AmigosUsu existingAmigosUsu = repository.findById(amigosUsu.getIdUsuAm()).orElse(null);
		//existingAmigosUsu.setName(amigosUsu.getName());
		//existingAmigosUsu.setQuantity(amigosUsu.getQuantity());
		//existingAmigosUsu.setPrice(amigosUsu.getPrice());
		existingAmigosUsu.setUsuAmIdSolicitante(amigosUsu.getUsuAmIdSolicitante());
		existingAmigosUsu.setUsuAmIdReceptor(amigosUsu.getUsuAmIdReceptor());
		existingAmigosUsu.setSolicitudAceptada(amigosUsu.getSolicitudAceptada());
		existingAmigosUsu.setFechaEnviada(amigosUsu.getFechaEnviada());
		return repository.save(existingAmigosUsu);
	}
	
	// Metodos propios -----------------------------------------------------------
	
	// Buscar amigos del usuario
	public List<AmigosUsu> findAmigosUsuario(Integer idUsuSolicitud, Integer idUsuRecep) {
		
		// Usuario usuSol = new Usuario();
		// Usuario usuRecept = new Usuario();
		
		// usuSol.setIdUsu(idUsuSolicitud);
		// usuRecept.setIdUsu(idUsuRecep);
		
		ArrayList<AmigosUsu> listaAmUsu = new ArrayList<AmigosUsu>();
		listaAmUsu = (ArrayList<AmigosUsu>) repository.findAmigosUsuario(idUsuSolicitud, idUsuRecep);
		return listaAmUsu;
	}
	
	/*buscar peticiones de amistad del usuario en general (sin identificar enviadas recibidas)*/
	public List<AmigosUsu> findPeticionesEnGeneralUsuario(Integer idUsuSolicitud, Integer idUsuRecep) {
		
		ArrayList<AmigosUsu> listaPeticionesGen = new ArrayList<AmigosUsu>();
		listaPeticionesGen = (ArrayList<AmigosUsu>) repository.findPeticionesEnGeneralUsuario(idUsuSolicitud, idUsuRecep);
		return listaPeticionesGen;
	}
	
	/* buscar peticiones de amistad realizadas por el usuario*/
	public List<AmigosUsu> findPeticionesRealizadasUsuario(Integer idUsuSolicitud) {
		
		ArrayList<AmigosUsu> peticionesRealizadas = new ArrayList<AmigosUsu>();
		peticionesRealizadas = (ArrayList<AmigosUsu>) repository.findPeticionesRealizadasUsuario(idUsuSolicitud);
		return peticionesRealizadas;
		
	}
	
	/* buscar peticiones recibidas por el usuario*/
	public List<AmigosUsu> findPeticionesRecibidasUsuario(Integer idUsuRecep) {
		
		ArrayList<AmigosUsu> peticionesRecibidas = new ArrayList<AmigosUsu>();
		peticionesRecibidas = (ArrayList<AmigosUsu>) repository.findPeticionesRecibidasUsuario(idUsuRecep);
		return peticionesRecibidas;
	}
	
	
	
}
