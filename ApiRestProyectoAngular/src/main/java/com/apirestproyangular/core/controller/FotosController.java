package com.apirestproyangular.core.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.apirestproyangular.core.entity.Comentario;
import com.apirestproyangular.core.entity.Entradas;
import com.apirestproyangular.core.entity.Fotos;
import com.apirestproyangular.core.objects.BuscarDatosUsuario;
import com.apirestproyangular.core.objects.Response;
import com.apirestproyangular.core.service.FotosService;

@RestController
public class FotosController {
	
	@Autowired
	private FotosService service;
	
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/addFoto")
	public Response addFoto(@RequestBody Fotos foto) {
		String mensajeOperacion = ""; //Si esta vacio, problema con insertar la entrada
		
		if(foto != null) {
			
			//Comprobar errores de insertado
			try {
				
				Fotos fotoDevuelta = new Fotos();
				
				//Insertar y devolver usuario
				fotoDevuelta = service.saveFoto(foto);
				
				//Ver si se ha insertado en caso de no dar excepcion
				if(fotoDevuelta != null) {
					mensajeOperacion = "Foto insertada";
					
				}else {
					mensajeOperacion = "Problemas, foto no insertada";
				}
				
				
			}catch(Exception e) {
				System.out.println("Problemas en la inserccion" + e.toString());
				mensajeOperacion = "Problemas, foto no insertada";
			}
			
		} else {
			mensajeOperacion = "Foto vacia";
		}
		
		Response resp = new Response();
		resp.setRespuesta(mensajeOperacion);
		
		return resp;
		
	}
	
	
	@PostMapping("/addFotos")
	public List<Fotos> addFotos (@RequestBody List<Fotos> fotos){
		return service.saveFotos(fotos);
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/fotos")
	public List<Fotos> findAllFotos(){
		return service.getFotos();
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/fotos/{id}")
	public Fotos findFotosById(@PathVariable int id) {
		return service.getFotoById(id);
	}
	
	@PutMapping("/updateFoto")
	public Fotos updateFoto(@RequestBody Fotos fot) {
		return service.updateFoto(fot);
	}
	
	@DeleteMapping("/deleteFoto/{id}")
	public String deleteFoto(@PathVariable int id) {
		return service.deleteFoto(id);
	}
	
	//Metodos propios ------------------------------------------------
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/getFotosUsuario/{idUsu}")
	public List<Fotos> getFotosUsuario(@PathVariable Integer idUsu) {
		
		ArrayList<Fotos> listaFotos = new ArrayList<Fotos>();
		listaFotos = (ArrayList<Fotos>) service.findFotosUsuario(idUsu);
		listaFotos = quitarListas(listaFotos);
		return listaFotos;
		
	}
	
	//Vaciar listas
		private ArrayList<Fotos> quitarListas(ArrayList<Fotos> listaFotos) {
			
			ArrayList<Fotos> listaFotosDev = new ArrayList<>();
			BuscarDatosUsuario busUsu = new BuscarDatosUsuario();
			
			for(int i = 0; listaFotos.size() > i ;i++) {
				Fotos fot = new Fotos();
				fot = listaFotos.get(i);
				fot.setUsuarioFotos(busUsu.vaciarObjetosUsu(fot.getUsuarioFotos()));
				listaFotosDev.add(fot);
			}
			
			return listaFotosDev;
			
		}
	
}
