package com.apirestproyangular.core.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.stream.events.Comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.apirestproyangular.core.entity.Comentario;
import com.apirestproyangular.core.entity.Fotos;
import com.apirestproyangular.core.entity.Publicacion;
import com.apirestproyangular.core.objects.BuscarDatosUsuario;
import com.apirestproyangular.core.objects.Respon;
import com.apirestproyangular.core.service.PublicacionService;

@RestController
public class PublicacionController {
	
	@Autowired
	private PublicacionService service;
	
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/addPublicacion")
	public ResponseEntity<Respon> addPublicacion(@RequestBody Publicacion publ) {
		String mensajeOperacion = ""; //Si esta vacio, problema con insertar la entrada
		
		if(publ != null) {
			
			//Comprobar errores de insertado
			try {
				
				Publicacion publDevuelta = new Publicacion();
				
				//Insertar y devolver usuario
				publDevuelta = service.savePublicacion(publ);
				
				//Ver si se ha insertado en caso de no dar excepcion
				if(publDevuelta != null) {
					mensajeOperacion = "Publicacion insertada";
					
				}else {
					mensajeOperacion = "Problemas, publicacion no insertada";
				}
				
				
			}catch(Exception e) {
				System.out.println("Problemas en la inserccion" + e.toString());
				mensajeOperacion = "Problemas, Publicacion no insertada";
			}
			
		} else {
			mensajeOperacion = "Publicacion vacia";
		}
		
		Respon resp = new Respon();
		resp.setRespuesta(mensajeOperacion);
		
		return new ResponseEntity<Respon>(resp, HttpStatus.OK);
		
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/addPublicaciones")
	public ResponseEntity<List<Publicacion>> addPublicaciones (@RequestBody List<Publicacion> publicaciones){
		return new ResponseEntity<List<Publicacion>>(service.savePublicaciones(publicaciones), HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/publicaciones")
	public ResponseEntity<List<Publicacion>> findAllPublicaciones(){
		return new ResponseEntity<List<Publicacion>>(service.getPublicaciones(), HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/publicacionId/{id}")
	public ResponseEntity<Publicacion> findPublicacionesById(@PathVariable Integer id) {
		
		return new ResponseEntity<Publicacion>(service.getPublicacionById(id),HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@PutMapping("/updatePublicacion")
	public ResponseEntity<Publicacion> updatePublicacion(@RequestBody Publicacion publ) {
		return new ResponseEntity<Publicacion>(service.updatePublicacion(publ), HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@DeleteMapping("/deletePublicacion/{id}")
	public ResponseEntity<Respon> deletePublicacion(@PathVariable int id) {
		
		Respon resp = new Respon();
		resp.setRespuesta(service.deletePublicacion(id));
		return new ResponseEntity<Respon>(resp, HttpStatus.OK);
	}
	
	//metodos propios ------------------------------------------------
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/getPubliInicioUsuario/{idUsu}")
	public ResponseEntity<List<Publicacion>> getPubliInicioUsuario(@PathVariable Integer idUsu){
		
		ArrayList<Publicacion> listaPubl = new ArrayList<Publicacion>();
		listaPubl =  (ArrayList<Publicacion>) service.findPubliInicioUsuario(idUsu);
		listaPubl = quitarListas(listaPubl);
		return new ResponseEntity<List<Publicacion>>(listaPubl, HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/getPublicacionesUsuario/{idUsu}")
	public ResponseEntity<List<Publicacion>> getPublicacionesUsuario(@PathVariable Integer idUsu){
		
		ArrayList<Publicacion> listaPubl = new ArrayList<Publicacion>();
		listaPubl =  (ArrayList<Publicacion>) service.findPublicacionesUsuario(idUsu);
		listaPubl = quitarListas(listaPubl);
		return new ResponseEntity<List<Publicacion>>(listaPubl, HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/getPubliPubl")
	public ResponseEntity<List<Publicacion>> getPubliPubl(){
		
		ArrayList<Publicacion> listaPubl = new ArrayList<Publicacion>();
		listaPubl = (ArrayList<Publicacion>) service.findPublicacionesPubl();
		listaPubl = quitarListas(listaPubl);
		return new ResponseEntity<List<Publicacion>>(listaPubl, HttpStatus.OK);
	}
	
	//Vaciar listas
	private ArrayList<Publicacion> quitarListas(ArrayList<Publicacion> listaPubl) {
		
		ArrayList<Publicacion> listaPublDev = new ArrayList<>();
		BuscarDatosUsuario busUsu = new BuscarDatosUsuario();
		
		for(int i = 0; listaPubl.size() > i ; i++) {
			
			Publicacion publ = new Publicacion();
			publ = listaPubl.get(i);
			publ.setUsuarioPubl(busUsu.vaciarObjetosUsu(publ.getUsuarioPubl()));
			
			Set<Comentario> comentsPubl = new HashSet<Comentario>();
			// comentsPubl = publ.getComentariosPublicacion();
			publ.setComentariosPublicacion(comentsPubl);
			
			/*
			Set<Comentario> comentsPublDev = new HashSet<Comentario>();
			for(Comentario com: comentsPubl) {
				
				com.setUsuarioComent(busUsu.vaciarObjetosUsu(com.getUsuarioComent()));;
				comentsPublDev.add(com);
			}
			
			
			publ.setComentariosPublicacion(comentsPublDev);
			*/
			
			listaPublDev.add(publ);
		}
		
		return listaPublDev;
	}
	
}
