package com.apirestproyangular.core.service;

import java.io.Console;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apirestproyangular.core.entity.AmigosUsu;
import com.apirestproyangular.core.entity.Comentario;
import com.apirestproyangular.core.entity.Entradas;
import com.apirestproyangular.core.entity.Fotos;
import com.apirestproyangular.core.entity.Publicacion;
import com.apirestproyangular.core.entity.Usuario;
import com.apirestproyangular.core.entity.Videos;
import com.apirestproyangular.core.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository repository;
	
	public Usuario saveUsuario(Usuario usu) {
		
		return repository.save(usu);
	}
	
	public List<Usuario> saveUsuarios(List<Usuario> usu) {
		return repository.saveAll(usu);
	}
	
	public List<Usuario> getUsuarios(){
		
		//Lista para solo objetos usuario (sin oros objetos del json)
		ArrayList<Usuario> listaSoloUsu = new ArrayList<Usuario>();
		
		//Obtener solo el json de usuarios, no de otros objetos
		ArrayList<Usuario> listaUsu = new ArrayList<Usuario>();
		listaUsu = (ArrayList<Usuario>) repository.findAll();
		
		for(int i = 0; listaUsu.size() > i ; i++) {
			
			Usuario usu = new Usuario(); 
			usu = listaUsu.get(i);
			
			//Vaciar objetos
			usu = vaciarObjetosUsu(usu);
			
			listaSoloUsu.add(usu);
			
		} 
		
		return listaSoloUsu;
		
	}
	
	public Usuario getUsuarioById(int id) {
		
		Usuario usu = new Usuario();
		usu = repository.findById(id).orElse(usu);
		
		usu = vaciarObjetosUsu(usu);
		
		return usu;
	}
	
	
	
	public String deleteUsuario(int id) {
		repository.deleteById(id);
		return "Usuario removed !! " + id;
	}
	
	public Usuario updateUsuario (Usuario usu) {
		Usuario existingUsuario = repository.findById(usu.getIdUsu()).orElse(null);
		existingUsuario.setNombreUsu(usu.getNombreUsu());
		existingUsuario.setFechaAlta(usu.getFechaAlta());
		existingUsuario.setPais(usu.getPais());
		existingUsuario.setCiudad(usu.getCiudad());
		
		existingUsuario.setRegion(usu.getRegion());
		existingUsuario.setEmail(usu.getEmail());
		existingUsuario.setNombre(usu.getNombre());
		existingUsuario.setApellidos(usu.getApellidos());
		
		existingUsuario.setFechaNacimiento(usu.getFechaNacimiento());
		existingUsuario.setTelefono(usu.getTelefono());
		existingUsuario.setEmailEntrada(usu.getEmailEntrada());
		existingUsuario.setContrasenya(usu.getContrasenya());
		
		existingUsuario.setPerfilPrivado(usu.getPerfilPrivado());
		existingUsuario.setAceptadaPolPriva(usu.getAceptadaPolPriva());
		existingUsuario.setEsAdministrador(usu.getEsAdministrador());
		existingUsuario.setUsuarioActivo(usu.getUsuarioActivo());
		existingUsuario.setUsuarioBaneado(usu.getUsuarioBaneado());
		
		existingUsuario.setFechaFinBaneo(usu.getFechaFinBaneo());
		existingUsuario.setEstado(usu.getEstado());
		existingUsuario.setNumVisitas(usu.getNumVisitas());
		existingUsuario.setFechaUltimoLogin(usu.getFechaUltimoLogin());
		existingUsuario.setFotoPerfil(usu.getFotoPerfil());
		existingUsuario.setFotoPortada(usu.getFotoPortada());
		
		return repository.save(existingUsuario);
	}
	
	// metodos propios --------------------------------------
		
	// buscar usuario para el login
		public Usuario findUsuLogin(String emailent, String contrasenya) {
			
			Usuario usu = new Usuario();
			
			Integer idUsu = 0;
			idUsu = repository.findUsuLogin(emailent, contrasenya);
			
			System.out.println("id usuario para entrar: " + idUsu);
			
			if(idUsu > 0) {
				usu.setIdUsu(idUsu);
			}
			
			return usu;
			
		}
		
		//Obtener ultimo id
		public Integer findLastIdUsuario() {
			return repository.findLastIdUsuario();
		}
		
		/* Select para buscar nuevos amigos en la seccion buscar amigos*/
		public List<Usuario> findNuevosAmigos(Integer idUsu) {
			ArrayList<Usuario> listaNuevosAm = new ArrayList<Usuario>();
			listaNuevosAm = (ArrayList<Usuario>) repository.findNuevosAmigos(idUsu);
			return listaNuevosAm;
		}
		
		/* select para buscar nuevos amigos por pais*/
		public List<Usuario> findNuevosAmigosPais(String pais) {
			ArrayList<Usuario> listaNuevosAmPais = new ArrayList<Usuario>();
			listaNuevosAmPais = (ArrayList<Usuario>) repository.findNuevosAmigosPais(pais);
			return listaNuevosAmPais;
			
		}
		
		/* select para buscar nuevos amigos por region*/
		public List<Usuario> findNuevosAmigosRegion(String region) {
			ArrayList<Usuario> listaNuevosAmRegion = new ArrayList<Usuario>();
			listaNuevosAmRegion = (ArrayList<Usuario>) repository.findNuevosAmigosRegion(region);
			return listaNuevosAmRegion;
		}
		
		/* select para buscar nuevos amigos por ciudad*/
		public List<Usuario>  findNuevosAmigosCiudad(String ciudad) {
			ArrayList<Usuario> listaNuevosAmCiudad = new ArrayList<Usuario>();
			listaNuevosAmCiudad = (ArrayList<Usuario>) repository.findNuevosAmigosCiudad(ciudad);
			return listaNuevosAmCiudad;
		}
		
		/* Select para buscar un usuario por nombre de usuario*/
		public List<Usuario> findUsuarioNombreUsu(String nombreUsu) {
			ArrayList<Usuario> listaNuevosAm = new ArrayList<Usuario>();
			listaNuevosAm = (ArrayList<Usuario>) repository.findUsuarioNombreUsu(nombreUsu);
			return listaNuevosAm;
		}
		
		//------------------------------------------------------------
		
		//Vacia los otros objetos del usuario para enviarlo por json
		private Usuario vaciarObjetosUsu(Usuario usu) {
			
			Set<Comentario> setComment = new HashSet<Comentario>();
			usu.setComentarios(setComment);
			
			Set<Publicacion> setPubl = new HashSet<Publicacion>();
			usu.setPublicaciones(setPubl);
			
			Set<Fotos> setFotos = new HashSet<Fotos>();
			usu.setFotos(setFotos);
			
			Set<Videos> setVideos = new HashSet<Videos>();
			usu.setVideos(setVideos);
			
			Set<Entradas> setEntradas = new HashSet<Entradas>();
			usu.setEntradas(setEntradas);
			
			Set<AmigosUsu> setAmUsu = new HashSet<AmigosUsu>();
			usu.setAmigosUsuRecibidos(setAmUsu);
			usu.setAmigosUsuSolicitudes(setAmUsu);
			
			return usu;
			
		}
}
