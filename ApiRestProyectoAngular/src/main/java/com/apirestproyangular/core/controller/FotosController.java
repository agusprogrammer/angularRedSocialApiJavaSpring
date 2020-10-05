package com.apirestproyangular.core.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.ServletContext;

import org.apache.commons.io.FilenameUtils;
import org.hibernate.type.BlobType;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.apirestproyangular.core.entity.Comentario;
import com.apirestproyangular.core.entity.Entradas;
import com.apirestproyangular.core.entity.Fotos;
import com.apirestproyangular.core.objects.BuscarDatosUsuario;
import com.apirestproyangular.core.objects.EnviarArchivosGen;
import com.apirestproyangular.core.objects.GestionarArchivos;
import com.apirestproyangular.core.objects.PonerFecha;
import com.apirestproyangular.core.objects.Respon;
import com.apirestproyangular.core.service.FotosService;
import com.apirestproyangular.core.service.UsuarioService;

import ch.qos.logback.core.Context;

@RestController
public class FotosController {
	
	@Autowired ServletContext context;
	@Autowired private FotosService service;
	@Autowired private UsuarioService usuServ;
	
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/addFoto")
	public ResponseEntity<Respon> addFoto(@RequestBody Fotos foto) {
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
		
		Respon resp = new Respon();
		resp.setRespuesta(mensajeOperacion);
		
		return new ResponseEntity<Respon>(resp, HttpStatus.OK);
		
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/addFotos")
	public ResponseEntity<List<Fotos>> addFotos (@RequestBody List<Fotos> fotos){
		return new ResponseEntity<List<Fotos>>(service.saveFotos(fotos), HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/fotos")
	public ResponseEntity<List<Fotos>> findAllFotos(){
		return new ResponseEntity<List<Fotos>>(service.getFotos(), HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/fotos/{id}")
	public ResponseEntity<Fotos> findFotosById(@PathVariable int id) {
		return new ResponseEntity<Fotos>(service.getFotoById(id), HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@PutMapping("/updateFoto")
	public ResponseEntity<Fotos> updateFoto(@RequestBody Fotos fot) {
		return new ResponseEntity<Fotos>(service.updateFoto(fot), HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@DeleteMapping("/deleteFoto/{id}")
	public ResponseEntity<Respon> deleteFoto(@PathVariable int id) {
		
		Respon resp = new Respon();
		resp.setRespuesta(service.deleteFoto(id));
		
		return new ResponseEntity<Respon>(resp, HttpStatus.OK);
	}
	
	// Metodos para los archivos -----------------------------------
	
	// Guardar Archivo
	// recibir una foto nueva que subimos
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/addArchivoFoto/{idUsuFot}")
	public ResponseEntity<Respon> postSubirArchivoFoto(@RequestPart("file") MultipartFile file, @PathVariable Integer idUsuFot) {
		
		String mensajeOperacion = "";
		GestionarArchivos gesArch = new GestionarArchivos();
		
		if(file != null && idUsuFot != null) {
			
			if(idUsuFot > 0) {
				
				Fotos foto = new Fotos();
				
				//Crear objeto foto
				foto = crearObjetoFotoBD(file, idUsuFot);
				
				//Obtener el objeto foto creado
				
				if(foto != null) {
					
					//Guardar fichero
					String filesPath = context.getRealPath("/ficheros/img/" + foto.getIdFoto() + "-" + foto.getUsuarioFotos().getIdUsu() + "-" + file.getOriginalFilename().toLowerCase());
					File fichGuardar = new File(filesPath);
					//ruta anterior //File fichGuardar = new File("./ficheros/img/", foto.getIdFoto() + "-" + foto.getUsuarioFotos().getIdUsu() + "-" + file.getOriginalFilename().toLowerCase());
					//Guardar archivo
					System.out.println("Fichero foto: " + fichGuardar.getPath() + " " + fichGuardar.getName());
					mensajeOperacion = gesArch.guardarArchivo(fichGuardar, file);
					
				} else {
					mensajeOperacion = "foto no creada en BD";
				}
				
			} else {
				mensajeOperacion = "El id de usuario tiene que ser mayor a 0";
			}
			
			
		} else {
			mensajeOperacion = "El fichero y el id de usuario no pueden estar vacios";
		}
		
		//Respuesta
		Respon resp = new Respon();
		resp.setRespuesta(mensajeOperacion);
		
		return new ResponseEntity<Respon>(resp, HttpStatus.OK);
	}
	
	// Crea el objeto foto en la base de datos
	private Fotos crearObjetoFotoBD(MultipartFile file, Integer idUsu) {
		
		String mensajeOperacion = ""; //Si esta vacio, problema con insertar la entrada
		
		PonerFecha ponerFecha = new PonerFecha();
		
		Fotos foto = new Fotos();
		foto.setUsuarioFotos(usuServ.getUsuarioById(idUsu));
		foto.setFechaSubidaFoto(ponerFecha.ponerFechaHoraActual());
		foto.setFotoString(file.getOriginalFilename().toLowerCase());
		
		System.out.println("Obj foto: " + foto.toString());
		
		Fotos fotoDevuelta = new Fotos();
		
		//subir foto a BD
		
		try {
		
			fotoDevuelta = service.saveFoto(foto);
		
			if(fotoDevuelta != null) mensajeOperacion = "objeto foto insertado";
			else mensajeOperacion = "Problemas, objeto foto no insertado";
			
		}catch(Exception e) {
			System.out.println("Problemas en la inserccion" + e.toString());
			mensajeOperacion = "Problemas, foto no insertada";
		}
		
		
		return fotoDevuelta;
		
	}
	
	// buscar archivos
	/*
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/getArchivosFoto/{idUsu}")
	public ArrayList<EnviarArchivosGen> getArchivosFoto(@PathVariable Integer idUsu) {
		
		//Obtener fotos
		ArrayList<Fotos> listaFotos = new ArrayList<Fotos>();
		listaFotos = (ArrayList<Fotos>) service.findFotosUsuario(idUsu);
		listaFotos = quitarListas(listaFotos);
		
		GestionarArchivos gesArch = new GestionarArchivos();
		
		
		ArrayList<EnviarArchivosGen> listaFichEnv = new ArrayList<>();
		
		for(int i = 0; listaFotos.size() > i ;i++) {
			
			File envFile = new File("./ficheros/img/" + idUsu + "-"  + listaFotos.get(i).getIdFoto() + "-" + listaFotos.get(i).getFotoString());
			byte[] archBytes;
			
			//archBytes = gesArch.obtenerFichBytes(envFile);
			
			EnviarArchivosGen envGen = new EnviarArchivosGen();
			//envGen.setByteArch(archBytes);
			envGen.setFichero(envFile);
			envGen.setObj(listaFotos.get(i));
			
			listaFichEnv.add(envGen);
		}
		
		return listaFichEnv;
	}
	*/
	
	// buscar archivos de un usuario
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/getArchivosFotoUsuario/{idUsu}")
	public ResponseEntity<List<EnviarArchivosGen>> getArchivosFotoUsuario(@PathVariable Integer idUsu) {
		
		System.out.println("Id de usuario: " + idUsu);
		
		List<EnviarArchivosGen> listaImagenes = new ArrayList<EnviarArchivosGen>();
		GestionarArchivos gesArch = new GestionarArchivos();
		
		//Obtener fotos
		ArrayList<Fotos> listaFotos = new ArrayList<Fotos>();
		
		try {
			listaFotos = (ArrayList<Fotos>) service.findFotosUsuario(idUsu);
		} catch(Exception e) {
			System.out.println("Problema al buscar las fotos de un usuario: " + e.toString());
		}
		
		listaFotos = quitarListas(listaFotos);
		
		for(Fotos fot: listaFotos) {
			
			EnviarArchivosGen envGen = new EnviarArchivosGen();
			envGen.setObj(fot);
			
			String filesPath = context.getRealPath("/ficheros/img/" + fot.getIdFoto() + "-" + idUsu + "-" + fot.getFotoString());
			File fich = new File(filesPath);
			
			if(!fich.isDirectory()) {
				
				envGen.setFicheroCompletoString(gesArch.obtenerUnFichImg(fich));
				listaImagenes.add(envGen);
			}
		}
		
		return new ResponseEntity<List<EnviarArchivosGen>>(listaImagenes, HttpStatus.OK);
	}
	
	// buscar archivos foto todos
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/getArchivosFotoAll")
	public ResponseEntity<List<String>> getArchivosFotoAll() {
		List<String> images = new ArrayList<String>();
		String filesPath = context.getRealPath("/ficheros/img");
		// String filesPath = "./ficheros/img/";
		File fileFolder = new File(filesPath);
		
		if(fileFolder.isDirectory()) {
			
			for(final File file: fileFolder.listFiles()) {
				
				if(!file.isDirectory()) {
					
					String fichString = "";
					GestionarArchivos gesArch = new GestionarArchivos();
					fichString = gesArch.obtenerUnFichImg(file);
					images.add(fichString);
					
				}
			}
		}
		
		return new ResponseEntity<List<String>>(images, HttpStatus.OK);
	}
	
	
	// borrar archivos
	@CrossOrigin(origins = "http://localhost:4200")
	@DeleteMapping("/deleteArchivosFoto/{idFoto}")
	public ResponseEntity<Respon> deleteArchivosFoto(@PathVariable int idFoto) {
		
		Fotos fot = new Fotos();
		fot = service.getFotoById(idFoto);
		
		String filesPath = context.getRealPath("/ficheros/img/" + fot.getIdFoto() + "-" + fot.getUsuarioFotos().getIdUsu() + "-" + fot.getFotoString());
		// File fichBorrar = new File("./ficheros/img/" + fot.getUsuarioFotos().getIdUsu() + "-" + fot.getIdFoto() + "-" + fot.getFotoString());
		File fichBorrar = new File(filesPath);
		System.out.println("Fichero a borrar: " + fichBorrar.getPath() + " " + fichBorrar.getName());
		
		GestionarArchivos gesArch = new GestionarArchivos();
		
		//Borrar en ficheros
		String fichRespBorrar = "";
		fichRespBorrar = gesArch.borrarFichero(fichBorrar);
		
		if(fichRespBorrar.equals("fichero borrado")) {
			
			//Borrar en BD
			String respServ = "";
			
			respServ = service.deleteFoto(idFoto);
			
			if(respServ.equals("no")) {
				fichRespBorrar = "problemas al borrar";
			}
		
		} else {
			fichRespBorrar = "fichero no borrado";
		}
		
		Respon resp = new Respon();
		resp.setRespuesta(fichRespBorrar);
		
		return new ResponseEntity<Respon>(resp, HttpStatus.OK);
	}
	
	//Metodos propios ------------------------------------------------
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/getFotosUsuario/{idUsu}")
	public ResponseEntity<List<Fotos>> getFotosUsuario(@PathVariable Integer idUsu) {
		
		ArrayList<Fotos> listaFotos = new ArrayList<Fotos>();
		listaFotos = (ArrayList<Fotos>) service.findFotosUsuario(idUsu);
		listaFotos = quitarListas(listaFotos);
		return new ResponseEntity<List<Fotos>>(listaFotos, HttpStatus.OK);
		
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
