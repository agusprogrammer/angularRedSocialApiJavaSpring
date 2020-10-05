package com.apirestproyangular.core.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import com.apirestproyangular.core.entity.Publicacion;
import com.apirestproyangular.core.entity.Usuario;
import com.apirestproyangular.core.objects.BuscarDatosUsuario;
import com.apirestproyangular.core.objects.Respon;
import com.apirestproyangular.core.service.ComentarioService;

@RestController
public class ComentarioController {
	
	@Autowired
	private ComentarioService service;
	
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/addComentario")
	public ResponseEntity<Respon> addComentario(@RequestBody Comentario com) {
		
		String mensajeOperacion = "";
		
		if(com != null) {
			
			try {
				
				Comentario comDev = new Comentario();
				
				comDev = service.saveComentario(com);
				
				if(comDev != null) {
					mensajeOperacion = "Comentario insertado";
					
				}else {
					mensajeOperacion = "Problemas, comentario no insertado";
				}
				
				
			}catch(Exception e) {
				System.out.println("Problemas en la inserccion: ");
				System.err.println(e.toString());
				mensajeOperacion = "Problemas, comentario no insertado";
			}
		}
		
		Respon resp = new Respon();
		resp.setRespuesta(mensajeOperacion);
		
		return new ResponseEntity<Respon>(resp, HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/addComentarios")
	public ResponseEntity<List<Comentario>> addComentarios (@RequestBody List<Comentario> coms){
		return new ResponseEntity<List<Comentario>>(service.saveComentario(coms), HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/comentarios")
	public ResponseEntity<List<Comentario>> findAllComentarios(){
		return new ResponseEntity<List<Comentario>>(service.getComentarios(), HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/comentarios/{id}")
	public ResponseEntity<Comentario> findComentarioById(@PathVariable int id) {
		return new ResponseEntity<Comentario>(service.getComentarioById(id), HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@PutMapping("/updateComentario")
	public ResponseEntity<Comentario> updateComentario(@RequestBody Comentario com) {
		return new ResponseEntity<Comentario>(service.updateComentario(com), HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@DeleteMapping("/deleteComentario/{id}")
	public ResponseEntity<Respon> deleteComentario(@PathVariable int id) {
		
		Respon resp = new Respon();
		resp.setRespuesta(service.deleteComentario(id));
		
		return new ResponseEntity<Respon>(resp, HttpStatus.OK);
	}
	
	//Metodos propios ---------------------
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/getComentariosPublicacion/{idPubl}")
	public ResponseEntity<List<Comentario>> getComentariosPublicacion(@PathVariable Integer idPubl) {
		
		ArrayList<Comentario> listaComment = new ArrayList<Comentario>();
		listaComment = (ArrayList<Comentario>) service.findComentariosPublicacion(idPubl);
		listaComment = quitarListas(listaComment, idPubl);
		return new ResponseEntity<List<Comentario>>(listaComment, HttpStatus.OK);
		
	}
	
	//Vaciar listas
	private ArrayList<Comentario> quitarListas(List<Comentario> listaComentarios, Integer idPubl){
		
		ArrayList<Comentario> listaComentariosDev = new ArrayList<Comentario>();
		BuscarDatosUsuario busUsu = new BuscarDatosUsuario();
		
		for(int i = 0; listaComentarios.size() > i ;i++) {
			
			Comentario com = new Comentario();
			com = listaComentarios.get(i);

			com.setUsuarioComent(busUsu.vaciarObjetosUsu(com.getUsuarioComent()));
			
			Publicacion publ = new Publicacion();
			publ.setIdPublicacion(idPubl);
			
			//Poner en la lista y retornar
			com.setPublComent(publ);
			listaComentariosDev.add(com);
			
		}
		
		return listaComentariosDev;
	}
	
}
