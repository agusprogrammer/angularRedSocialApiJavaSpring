package com.apirestproyangular.core.controller;

import java.util.ArrayList;
import java.util.List;

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

import com.apirestproyangular.core.entity.AmigosUsu;
import com.apirestproyangular.core.entity.AmigosUsuId;
import com.apirestproyangular.core.entity.Usuario;
import com.apirestproyangular.core.objects.BuscarDatosUsuario;
import com.apirestproyangular.core.objects.Respon;
import com.apirestproyangular.core.service.AmigosUsuService;
import com.apirestproyangular.core.service.UsuarioService;

@RestController
public class AmigosUsuController {
	
	@Autowired
	private AmigosUsuService service;
	
	//private UsuarioController usuController; //Para buscar datos de los usuarios
	
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/addAmigosUsus")
	public ResponseEntity<List<AmigosUsu>> addAmigosUsus(@RequestBody List<AmigosUsu> amigosUsus){
		return new ResponseEntity<List<AmigosUsu>>(service.saveAmigosUsu(amigosUsus), HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/amigosUsu")
	public ResponseEntity<List<AmigosUsu>> findAllAmigosUsu() {
		return new ResponseEntity<List<AmigosUsu>>(service.getAmigosUsu(), HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/amigosUsuId/{idSol}/{idReciv}")
	public ResponseEntity<AmigosUsu> findAmigosUsuById(@PathVariable int idSol, @PathVariable int idReciv) {
		
		AmigosUsuId amUsuId = new AmigosUsuId();
		amUsuId.setIdSolicitante(idSol);
		amUsuId.setIdReceptor(idReciv);
		
		System.out.println(idSol + "/" + idReciv);
		
		return new ResponseEntity<AmigosUsu>(service.getAmigosUsuById(amUsuId), HttpStatus.OK);
	}
	
	
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/addAmigosUsu")
	public ResponseEntity<Respon> addAmigosUsu(@RequestBody AmigosUsu amUsu) {
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
					mensajeOperacion = "Problemas, Amigo usuario no insertado";
				}
				
				
			}catch(Exception e) {
				System.out.println("Problemas en la inserccion" + e.toString());
				mensajeOperacion = "Problemas, Amigo usuario no insertado";
			}
			
		} else {
			mensajeOperacion = "Amigo usuario vacio";
		}
		
		Respon resp = new Respon();
		resp.setRespuesta(mensajeOperacion);
		
		return new ResponseEntity<Respon>(resp, HttpStatus.OK);
		
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@PutMapping("/updateAmigoUsuario")
	public ResponseEntity<AmigosUsu> updateAmigoUsuario(@RequestBody AmigosUsu amUsu) {
		return new ResponseEntity<AmigosUsu>(service.updateAmigosUsu(amUsu), HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@DeleteMapping("/deleteAmigoUsuario/{idSol}/{idRecv}")
	public ResponseEntity<Respon> deleteAmigoUsuario(@PathVariable int idSol, @PathVariable int idRecv) {
		
		AmigosUsuId id = new AmigosUsuId();
		
		id.setIdReceptor(idRecv);
		id.setIdSolicitante(idSol);
		
		Respon resp = new Respon();
		resp.setRespuesta(service.deleteAmigosUsu(id));
		
		return new ResponseEntity<Respon>(resp, HttpStatus.OK);
	}
	
	//Metodos propios -------------------------------------------------------
	
	// http://localhost:9191/getAmigosUsu
	
	//Amigos de un usuario
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/getAmigosUsu/{idUsu}")
	public ResponseEntity<List<AmigosUsu>> getAmigosUsuario(@PathVariable int idUsu){
		
		ArrayList<AmigosUsu> listaAmigosUsu = new ArrayList<AmigosUsu>();
		
		listaAmigosUsu = (ArrayList<AmigosUsu>) service.findAmigosUsuario(idUsu, idUsu);
		listaAmigosUsu = quitarListasUsu(listaAmigosUsu);
		return new ResponseEntity<List<AmigosUsu>>(listaAmigosUsu, HttpStatus.OK);
		
	}
	
	// Obtener peticiones en general de un usuario 
	// (tanto si son realizadas por este como no realizadas por este)
	
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/getPeticionesGenUsu/{idUsu}")
	public ResponseEntity<List<AmigosUsu>> getPeticionesEnGeneralUsuario(@PathVariable int idUsu){
		
		ArrayList<AmigosUsu> listaAmigosUsu = new ArrayList<AmigosUsu>();
		
		listaAmigosUsu = (ArrayList<AmigosUsu>) service.findPeticionesEnGeneralUsuario(idUsu, idUsu);
		listaAmigosUsu = quitarListasUsu(listaAmigosUsu);
		return new ResponseEntity<List<AmigosUsu>>(listaAmigosUsu, HttpStatus.OK);
	}
	
	
	//Peticiones de amistad realizadas por el usuario
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/getPeticionesRealizadasUsuario/{idUsu}")
	public ResponseEntity<List<AmigosUsu>> getPeticionesRealizadasUsuario(@PathVariable int idUsu){
		
		ArrayList<AmigosUsu> listaAmigosUsu = new ArrayList<AmigosUsu>();
		
		listaAmigosUsu = (ArrayList<AmigosUsu>) service.findPeticionesRealizadasUsuario(idUsu);
		listaAmigosUsu = quitarListasUsu(listaAmigosUsu);
		return new ResponseEntity<List<AmigosUsu>>(listaAmigosUsu, HttpStatus.OK);
	}
	
	//Peticiones de amistad recibidas por el usuario
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/getPeticionesRecibidasUsuario/{idUsu}")
	public ResponseEntity<List<AmigosUsu>> getPeticionesRecibidasUsuario(@PathVariable int idUsu){
		
		ArrayList<AmigosUsu> listaAmigosUsu = new ArrayList<AmigosUsu>();
		
		listaAmigosUsu = (ArrayList<AmigosUsu>) service.findPeticionesRecibidasUsuario(idUsu);
		listaAmigosUsu = quitarListasUsu(listaAmigosUsu);
		return new ResponseEntity<List<AmigosUsu>>(listaAmigosUsu, HttpStatus.OK);
		
	}
	
	//Buscar por los ids de usuario (metodo propio, el que esta por defecto falla)
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/getAmigosUsuPorIds/{idSol}/{usuIdRecept}")
	public ResponseEntity<AmigosUsu> getAmigosUsuPorIds(@PathVariable int idSol, @PathVariable int usuIdRecept) {

		ArrayList<AmigosUsu> listaAmigosUsu = new ArrayList<AmigosUsu>();
		AmigosUsu amUsu = new AmigosUsu();
		AmigosUsu amUsuDev = new AmigosUsu();
		
		amUsu = service.findAmigoUsuIds(idSol, usuIdRecept);
		listaAmigosUsu.add(amUsu);
		
		if (amUsu != null) {
			listaAmigosUsu = quitarListasUsu(listaAmigosUsu);
			amUsuDev = listaAmigosUsu.get(0);
		}
		
		
		
		return new ResponseEntity<AmigosUsu>(amUsuDev, HttpStatus.OK);
		
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
