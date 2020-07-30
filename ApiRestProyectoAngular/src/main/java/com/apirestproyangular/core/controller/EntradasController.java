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
import com.apirestproyangular.core.objects.BuscarDatosUsuario;
import com.apirestproyangular.core.objects.Response;
import com.apirestproyangular.core.service.EntradasService;

@RestController
public class EntradasController {
	
	@Autowired
	private EntradasService service;
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/entradas/{id}")
	public Entradas findEntradaById(@PathVariable Integer id) {
		return service.getEntradaById(id);
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/addEntrada")
	public Response addEntrada(@RequestBody Entradas ent) {
		String mensajeOperacion = ""; //Si esta vacio, problema con insertar la entrada
		
		if(ent != null) {
			
			//Comprobar errores de insertado
			try {
				
				Entradas entDevuelta = new Entradas();
				
				//Insertar y devolver usuario
				entDevuelta = service.saveEntrada(ent);
				
				//Ver si se ha insertado en caso de no dar excepcion
				if(entDevuelta != null) {
					mensajeOperacion = "Entrada insertada";
					
				}else {
					mensajeOperacion = "Problemas, entrada no insertada";
				}
				
				
			}catch(Exception e) {
				System.out.println("Problemas en la inserccion" + e.toString());
				mensajeOperacion = "Problemas, entrada no insertada";
			}
			
		} else {
			mensajeOperacion = "Entrada vacia";
		}
		
		Response resp = new Response();
		resp.setRespuesta(mensajeOperacion);
		
		return resp;
		
	}
	
	@PostMapping("/addEntradas")
	public List<Entradas> addEntradas (@RequestBody List<Entradas> entradas){
		return service.saveEntradas(entradas);
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/entradas")
	public List<Entradas> findAllEntradas(){
		return service.getEntradas();
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@PutMapping("/updateEntrada")
	public Entradas updateEntrada(@RequestBody Entradas ent) {
		return service.updateEntrada(ent);
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@DeleteMapping("/deleteEntrada/{id}")
	public String deleteEntrada(@PathVariable Integer id) {
		return service.deleteEntrada(id);
	}
	
	//Metodos propios -----------------------------------------
	
	//Obtener entradas de un usuario
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/getEntradasUsu/{id}")
	public List<Entradas> getEntradasUsu(@PathVariable Integer id) {
		
		ArrayList<Entradas> listaEnt = new ArrayList<>();
		listaEnt = (ArrayList<Entradas>) service.findEntrUsuario(id);
		listaEnt = quitarListas(listaEnt);
		return listaEnt;
		
	}
	
	//Vaciar listas
	private ArrayList<Entradas> quitarListas(ArrayList<Entradas> listaEntradas) {
		
		ArrayList<Entradas> listaEntDev = new ArrayList<>();
		BuscarDatosUsuario busUsu = new BuscarDatosUsuario();
		
		for(int i = 0; listaEntradas.size() > i ;i++) {
			Entradas ent = new Entradas();
			ent = listaEntradas.get(i);
			ent.setUsuarioEntradas(busUsu.vaciarObjetosUsu(ent.getUsuarioEntradas()));
			listaEntDev.add(ent);
		}
		
		return listaEntDev;
		
	}
	
}
