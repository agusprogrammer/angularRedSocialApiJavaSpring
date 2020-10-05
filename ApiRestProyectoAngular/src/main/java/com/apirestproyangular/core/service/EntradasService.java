package com.apirestproyangular.core.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apirestproyangular.core.entity.Entradas;
import com.apirestproyangular.core.repository.EntradasRepository;

@Service
public class EntradasService {
	
	@Autowired
	private EntradasRepository repository;
	
	public Entradas saveEntrada(Entradas entr) {
		return repository.save(entr);
	}
	
	public List<Entradas> saveEntradas(List<Entradas> entr) {
		return repository.saveAll(entr);
	}
	
	public List<Entradas> getEntradas(){
		return repository.findAll();
	}
	
	public Entradas getEntradaById(int id) {
		return repository.findById(id).orElse(null);
	}
	
	public String deleteEntrada(int id) {
		
		String entradaBorrada = "no";
		
		try {
			
			repository.deleteById(id);
			entradaBorrada = "si";
			
		} catch(Exception e) {
			entradaBorrada = "no";
		}
		
		return entradaBorrada;
	}
	
	public Entradas updateEntrada (Entradas entr) {
		Entradas existingEntrada = repository.findById(entr.getIdEntrada()).orElse(null);
		existingEntrada.setUsuarioEntradas(entr.getUsuarioEntradas());
		existingEntrada.setTituloEntrada(entr.getTituloEntrada());
		existingEntrada.setTextoEntrada(entr.getTextoEntrada());
		existingEntrada.setFechaCreacionEnt(entr.getFechaCreacionEnt());
		
		return repository.save(existingEntrada);
	}
	
	// metodos propios ---------------------------------------------------
	// entradas de un usuario
	public List<Entradas> findEntrUsuario(Integer idUsuEntr) {
		return repository.findEntrUsuario(idUsuEntr);
	}
	
	
}


