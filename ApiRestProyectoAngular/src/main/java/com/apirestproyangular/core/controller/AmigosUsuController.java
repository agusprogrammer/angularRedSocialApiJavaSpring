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

import com.apirestproyangular.core.entity.AmigosUsu;
import com.apirestproyangular.core.entity.AmigosUsuId;
import com.apirestproyangular.core.entity.Usuario;
import com.apirestproyangular.core.objects.BuscarDatosUsuario;
import com.apirestproyangular.core.objects.Response;
import com.apirestproyangular.core.service.AmigosUsuService;
import com.apirestproyangular.core.service.UsuarioService;

@RestController
public class AmigosUsuController {
	
	@Autowired
	private AmigosUsuService service;
	
	//private UsuarioController usuController; //Para buscar datos de los usuarios
	
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/addAmigosUsus")
	public List<AmigosUsu> addAmigosUsus(@RequestBody List<AmigosUsu> amigosUsus){
		return service.saveAmigosUsu(amigosUsus);
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/amigosUsu")
	public List<AmigosUsu> findAllAmigosUsu() {
		return service.getAmigosUsu();
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/amigosUsu/{idSol}/{idReciv}")
	public AmigosUsu findAmigosUsuById(@PathVariable Integer idSol, @PathVariable Integer idReciv) {
		
		AmigosUsuId amUsuId = new AmigosUsuId();
		amUsuId.setIdSolicitante(idSol);
		amUsuId.setIdReceptor(idReciv);
		
		return service.getAmigosUsuById(amUsuId);
	}
	
	
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/addAmigosUsu")
	public Response addAmigosUsu(@RequestBody AmigosUsu amUsu) {
		String mensajeOperacion = ""; //Si esta vacio, problema con insertar la entrada
		
		if(amUsu != null) {
			
			//Comprobar errores de insertado
			try {
				
				AmigosUsu amUsuDevuelta = new AmigosUsu();
				
				//Insertar y devolver usuario
				amUsuDevuelta = service.saveAmigosUsu(amUsu);
				
				//Ver si se ha insertado en caso de no dar excepcion
				if(amUsuDevuelta != null) {
					mensajeOperacion = "Amigo usuario insertado";
					
				}else {
					mensajeOperacion = "Problemas, Amigo usuario no insertada";
				}
				
				
			}catch(Exception e) {
				System.out.println("Problemas en la inserccion" + e.toString());
				mensajeOperacion = "Problemas, Amigo usuario no insertada";
			}
			
		} else {
			mensajeOperacion = "Amigo usuario vacio";
		}
		
		Response resp = new Response();
		resp.setRespuesta(mensajeOperacion);
		
		return resp;
		
	}
	
	@PutMapping("/updateAmigoUsuario")
	public AmigosUsu updateAmigoUsuario(@RequestBody AmigosUsu amUsu) {
		return service.updateAmigosUsu(amUsu);
	}
	
	@DeleteMapping("/deleteAmigoUsuario/{idSol}/{idRecv}")
	public String deleteAmigoUsuario(@PathVariable int idSol, @PathVariable int idRecv) {
		
		AmigosUsuId id = new AmigosUsuId();
		
		id.setIdReceptor(idRecv);
		id.setIdSolicitante(idSol);
		
		return service.deleteAmigosUsu(id);
	}
	
	//Metodos propios -------------------------------------------------------
	
	// http://localhost:9191/getAmigosUsu
	
	//Amigos de un usuario
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/getAmigosUsu/{idUsu}")
	public List<AmigosUsu> getAmigosUsuario(@PathVariable int idUsu){
		
		ArrayList<AmigosUsu> listaAmigosUsu = new ArrayList<AmigosUsu>();
		
		listaAmigosUsu = (ArrayList<AmigosUsu>) service.findAmigosUsuario(idUsu, idUsu);
		listaAmigosUsu = quitarListasUsu(listaAmigosUsu);
		return listaAmigosUsu;
		
	}
	
	// Obtener peticiones en general de un usuario 
	// (tanto si son realizadas por este como no realizadas por este)
	
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/getPeticionesGenUsu/{idUsu}")
	public List<AmigosUsu> getPeticionesEnGeneralUsuario(@PathVariable int idUsu){
		
		ArrayList<AmigosUsu> listaAmigosUsu = new ArrayList<AmigosUsu>();
		
		listaAmigosUsu = (ArrayList<AmigosUsu>) service.findPeticionesEnGeneralUsuario(idUsu, idUsu);
		listaAmigosUsu = quitarListasUsu(listaAmigosUsu);
		return listaAmigosUsu;
	}
	
	
	//Peticiones de amistad realizadas por el usuario
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/getPeticionesRealizadasUsuario/{idUsu}")
	public List<AmigosUsu> getPeticionesRealizadasUsuario(@PathVariable int idUsu){
		
		ArrayList<AmigosUsu> listaAmigosUsu = new ArrayList<AmigosUsu>();
		
		listaAmigosUsu = (ArrayList<AmigosUsu>) service.findPeticionesRealizadasUsuario(idUsu);
		listaAmigosUsu = quitarListasUsu(listaAmigosUsu);
		return listaAmigosUsu;
	}
	
	//Peticiones de amistad recibidas por el usuario
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/getPeticionesRecibidasUsuario/{idUsu}")
	public List<AmigosUsu> getPeticionesRecibidasUsuario(@PathVariable int idUsu){
		
		ArrayList<AmigosUsu> listaAmigosUsu = new ArrayList<AmigosUsu>();
		
		listaAmigosUsu = (ArrayList<AmigosUsu>) service.findPeticionesRecibidasUsuario(idUsu);
		listaAmigosUsu = quitarListasUsu(listaAmigosUsu);
		return listaAmigosUsu;
		
	}
	
	//Borrar objetos de usuario ---------------------------------------------
	private ArrayList<AmigosUsu> quitarListasUsu(List<AmigosUsu> listaAmigosUsu) {
		
		BuscarDatosUsuario busUsu = new BuscarDatosUsuario();
		ArrayList<AmigosUsu> listaAmigosUsuDevuelta = new ArrayList<AmigosUsu>();
		
		for(int i = 0; listaAmigosUsu.size() > i ;i++) {
			
			AmigosUsu amUsu = new AmigosUsu(); 
			amUsu = listaAmigosUsu.get(i);
			
			amUsu.setUsuAmIdReceptor(busUsu.vaciarObjetosUsu(amUsu.getUsuAmIdReceptor()));
			amUsu.setUsuAmIdSolicitante(busUsu.vaciarObjetosUsu(amUsu.getUsuAmIdSolicitante()));
			
			listaAmigosUsuDevuelta.add(amUsu);
		}
		
		return listaAmigosUsuDevuelta;
		
	}
	
	
}
