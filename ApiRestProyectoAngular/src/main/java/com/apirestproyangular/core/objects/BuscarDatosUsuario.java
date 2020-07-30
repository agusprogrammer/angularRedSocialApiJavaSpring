package com.apirestproyangular.core.objects;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.apirestproyangular.core.entity.AmigosUsu;
import com.apirestproyangular.core.entity.Comentario;
import com.apirestproyangular.core.entity.Entradas;
import com.apirestproyangular.core.entity.Fotos;
import com.apirestproyangular.core.entity.Publicacion;
import com.apirestproyangular.core.entity.Usuario;
import com.apirestproyangular.core.entity.Videos;
import com.apirestproyangular.core.service.UsuarioService;

public class BuscarDatosUsuario {
	
	private UsuarioService service;
	
	public BuscarDatosUsuario() {}
	
	//Metodo para poner los datos de lo usuarios de una lista (Usado en las listas de busqueda de usuarios)
	public ArrayList<Usuario> obtenerDatosUsu(ArrayList<Usuario> listaUsu) {
		
		ArrayList<Usuario> listaUsuObt = new ArrayList<Usuario>();
		
		for(int i = 0; listaUsu.size() > i ; i++){
			
			Usuario usu = new Usuario();
			usu = listaUsu.get(i);
			
			//Poner datos al usuario
			usu = service.getUsuarioById(usu.getIdUsu());
			listaUsuObt.add(usu);
			
		}
		
		return listaUsuObt;
	}
	
	//Obtener usuario por id, usado en el controlador de amigos de usuario
	public Usuario obtenerUsuarioId(int idUsu) {
		
		Usuario usu = new Usuario();
		usu = service.getUsuarioById(idUsu);
		return usu;
	}
	
	//Metodo para vaciar los objetos de un usuario antes de enviarlo por json
	public Usuario vaciarObjetosUsu(Usuario usu) {
		
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
