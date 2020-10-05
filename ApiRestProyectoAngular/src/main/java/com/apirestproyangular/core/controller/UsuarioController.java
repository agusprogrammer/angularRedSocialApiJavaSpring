package com.apirestproyangular.core.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.apirestproyangular.core.entity.Usuario;
import com.apirestproyangular.core.objects.BuscarDatosUsuario;
import com.apirestproyangular.core.objects.EnviarArchivosGen;
import com.apirestproyangular.core.objects.GestionarArchivos;
import com.apirestproyangular.core.objects.Respon;
import com.apirestproyangular.core.service.UsuarioService;

@RestController
public class UsuarioController {

	//Ejemplo de las rutas: http://localhost:9191/usuarios
	
	@Autowired private UsuarioService service;
	@Autowired ServletContext context;
	
	
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/addUsuario")
	// public Usuario addUsuario(@RequestBody Usuario usu) { //Lo de antes, devolvia un usuario
	public ResponseEntity<Respon> addUsuario(@RequestBody Usuario usu) {
		
		//Nota: debe de devolver un objeto, el String da problemas
		
		//return service.saveUsuario(usu); //Devulelve un usuario
		String mensajeOperacion = "";
		
		//Ver si lo envian vacio
		if(usu != null) {
			
			//Comprobar errores de insertado
			try {
				
				Usuario usuDevuelto = new Usuario();
				
				//Insertar y devolver usuario
				usuDevuelto = service.saveUsuario(usu);
				
				//Ver si se ha insertado en caso de no dar excepcion
				if(usuDevuelto != null) {
					mensajeOperacion = "Usuario insertado";
					
				}else {
					mensajeOperacion = "Problemas, usuario no insertado";
				}
				
				
			}catch(Exception e) {
				System.out.println("Problemas en la inserccion" + e.toString());
				mensajeOperacion = "Problemas, usuario no insertado";
			}
			
		} else {
			mensajeOperacion = "Usuario vacio";
		}
		
		
		
		Respon resp = new Respon();
		resp.setRespuesta(mensajeOperacion);
		
		return new ResponseEntity<Respon>(resp, HttpStatus.OK);
		
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/addUsuarios")
	public ResponseEntity<List<Usuario>> addUsuarios (@RequestBody List<Usuario> usu){
		return new ResponseEntity<List<Usuario>>(service.saveUsuarios(usu), HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/usuarios")
	public ResponseEntity<List<Usuario>> findAllUsuarios(){
		
		return new ResponseEntity<List<Usuario>>(service.getUsuarios(), HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/usuarios/{id}")
	public ResponseEntity<Usuario> findUsuarioById(@PathVariable int id) {
		// return service.getUsuarioById(id);
		Usuario usu = new Usuario();
		usu = service.getUsuarioById(id);
		return new ResponseEntity<Usuario>(usu, HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@PutMapping("/updateUsuario")
	public ResponseEntity<Usuario> updateUsuario(@RequestBody Usuario usu) {
		return new ResponseEntity<Usuario>(service.updateUsuario(usu), HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@DeleteMapping("/deleteUsuario/{id}")
	public ResponseEntity<Respon> deleteUsuario(@PathVariable int id) {
		
		Respon resp = new Respon();
		resp.setRespuesta(service.deleteUsuario(id));
		
		return new ResponseEntity<Respon>(resp, HttpStatus.OK);
	}
	
	//Metodos propios controlador usuario ---------------------------
	
	//Parte del login -------------------
	
	// http://localhost:9191/usuario/loginUsuario/{emailent}/{contrasenya}
	
	// @CrossOrigin(origins = "http://localhost:4200/home/Network/login")
	//Manera correcta de configurar el origen del cors (mirar mas sobre este tema)
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/loginUsuario/{emailent}/{contrasenya}")
	public ResponseEntity<Usuario> getUsuarioLogin(@PathVariable String emailent, @PathVariable String contrasenya) {
		
		Usuario usu = new Usuario();
		usu = service.findUsuLogin(emailent, contrasenya); //metodo para el login
		return new ResponseEntity<Usuario>(usu, HttpStatus.OK);
	}
	
	//Buscar usuarios -------------------------------------------------------
	//Parte buscar nuevos amigos (devolver listas de usuarios completos(sin listas especificas))
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/getNuevosAmigos/{idUsu}")
	public ResponseEntity<List<Usuario>> getNuevosAmigos(@PathVariable Integer idUsu) {
		
		ArrayList<Usuario> listaUsu = new ArrayList<Usuario>(); 
		listaUsu = (ArrayList<Usuario>) service.findNuevosAmigos(idUsu);
		listaUsu = quitarListas(listaUsu);
		return new ResponseEntity<List<Usuario>>(listaUsu, HttpStatus.OK);
	} 
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/getAmigosPais/{pais}")
	public ResponseEntity<List<Usuario>> getNuevosAmigosPais(@PathVariable String pais) {
		
		ArrayList<Usuario> listaUsu = new ArrayList<Usuario>(); 
		listaUsu = (ArrayList<Usuario>) service.findNuevosAmigosPais(pais);
		listaUsu = quitarListas(listaUsu);
		
		return new ResponseEntity<List<Usuario>>(listaUsu, HttpStatus.OK);
		
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/getAmigosRegion/{region}")
	public ResponseEntity<List<Usuario>> getNuevosAmigosRegion(@PathVariable String region) {
		
		ArrayList<Usuario> listaUsu = new ArrayList<Usuario>(); 
		listaUsu = (ArrayList<Usuario>) service.findNuevosAmigosRegion(region);
		listaUsu = quitarListas(listaUsu);
		
		return new ResponseEntity<List<Usuario>>(listaUsu, HttpStatus.OK);
		
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/getAmigosCiudad/{ciudad}")
	public ResponseEntity<List<Usuario>> getNuevosAmigosCiudad(@PathVariable String ciudad) {
		
		ArrayList<Usuario> listaUsu = new ArrayList<Usuario>(); 
		listaUsu = (ArrayList<Usuario>) service.findNuevosAmigosCiudad(ciudad);
		listaUsu = quitarListas(listaUsu);
		
		return new ResponseEntity<List<Usuario>>(listaUsu, HttpStatus.OK);
		
	}
	
	// Fotos del perfil de usuario -------------------------------------------
	// Obtener foto de perfil del usuario
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/getFotoPerfil/{idUsu}")
	public ResponseEntity<EnviarArchivosGen> getFotoPerfil(@PathVariable Integer idUsu) 
	{
		GestionarArchivos gesArch = new GestionarArchivos();
		
		Usuario usu = new Usuario();
		usu = service.getUsuarioById(idUsu);
		
		BuscarDatosUsuario busUsu = new BuscarDatosUsuario();
		usu = busUsu.vaciarObjetosUsu(usu);
		
		String filesPath = context.getRealPath("/ficheros/imgPer/" + usu.getIdUsu() + "-" + usu.getFotoPerfil());
		File fichFotoPerfil = new File(filesPath);
		
		
		EnviarArchivosGen envGen = new EnviarArchivosGen();
		
		if(!fichFotoPerfil.isDirectory()) {
			
			envGen.setFicheroCompletoString(gesArch.obtenerUnFichImg(fichFotoPerfil));
		}
		
		return new ResponseEntity<EnviarArchivosGen>(envGen, HttpStatus.OK);
	}
	
	// Guardar foto de perfil del usuario
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/postFotoPerfil/{idUsu}")
	public ResponseEntity<Respon> postFotoPerfil(@RequestPart("file") MultipartFile file, @PathVariable Integer idUsu) {
		
		Usuario usu = new Usuario();
		usu = service.getUsuarioById(idUsu);
		
		GestionarArchivos gesArch = new GestionarArchivos();
		
		String mensajeOperacion = "";
		
		
		if(usu != null && file != null) {
			
			String filesPath = "";
			File fich = new File("");
				
			// borrar foto anterior
			if(usu.getFotoPerfil() != "" || usu.getFotoPerfil() != null) {
				
				filesPath = context.getRealPath("/ficheros/imgPer/" + usu.getIdUsu() + "-" + usu.getFotoPerfil());
				fich = new File(filesPath);
				mensajeOperacion = gesArch.borrarFichero(fich);
			}
				
			// crear foto
			filesPath = context.getRealPath("/ficheros/imgPer/" + usu.getIdUsu() + "-" + file.getOriginalFilename().toLowerCase());
			fich = new File(filesPath);
			
			mensajeOperacion = gesArch.guardarArchivo(fich, file);
			
			// actualizar usuario
			usu.setFotoPerfil("");
			usu.setFotoPerfil(file.getOriginalFilename().toLowerCase());
			usu = service.updateUsuario(usu);
				
			
		} else {
			mensajeOperacion = "usuario o fichero vacio";
			
		}
		
		System.out.println(mensajeOperacion);
		
		Respon resp = new Respon();
		resp.setRespuesta(mensajeOperacion);
		
		return new ResponseEntity<Respon>(resp, HttpStatus.OK);
		
	}
	
	// Borrar foto de perfil del usuario
	@CrossOrigin(origins = "http://localhost:4200")
	@DeleteMapping("/deleteFotoPerfilUsu/{idUsu}")
	public ResponseEntity<Respon> deleteFotoPerfilUsu (@PathVariable Integer idUsu) 
	{
		String mensajeOperacion = "";
		
		GestionarArchivos gesArch = new GestionarArchivos();
		
		Usuario usu = new Usuario();
		usu = service.getUsuarioById(idUsu);
		
		if(usu != null) {
			
			String fotoPerfilString = "";
			fotoPerfilString = usu.getFotoPerfil();
			
			if(fotoPerfilString != "" || fotoPerfilString != null) {
				
				// borrar foto
				String filesPath = context.getRealPath("/ficheros/imgPer/" + usu.getIdUsu() + "-" + usu.getFotoPerfil());
				File fichBorrar = new File(filesPath);
				
				mensajeOperacion = gesArch.borrarFichero(fichBorrar);
				
				if(mensajeOperacion.equals("fichero borrado")) {
					
					// Actualizar usuario
					usu.setFotoPerfil("");
					usu = service.updateUsuario(usu);
				
				} else {
					mensajeOperacion = "fichero no borrado";
				}
				
			}
		}
		
		Respon resp = new Respon();
		resp.setRespuesta(mensajeOperacion);
		return new ResponseEntity<Respon>(resp, HttpStatus.OK); 
	}
	
	
	// Obtener foto de portada del usuario
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/getFotoPortada/{idUsu}")
	public ResponseEntity<EnviarArchivosGen> getFotoPortada(@PathVariable Integer idUsu) 
	{
		GestionarArchivos gesArch = new GestionarArchivos();
		
		Usuario usu = new Usuario();
		usu = service.getUsuarioById(idUsu);
		
		BuscarDatosUsuario busUsu = new BuscarDatosUsuario();
		usu = busUsu.vaciarObjetosUsu(usu);
		
		String filesPath = context.getRealPath("/ficheros/imgPor/" + usu.getIdUsu() + "-" + usu.getFotoPortada());
		File fichFotoPortada = new File(filesPath);
		
		
		EnviarArchivosGen envGen = new EnviarArchivosGen();
		
		if(!fichFotoPortada.isDirectory()) {
			
			envGen.setFicheroCompletoString(gesArch.obtenerUnFichImg(fichFotoPortada));
		}
		
		return new ResponseEntity<EnviarArchivosGen>(envGen, HttpStatus.OK);
	}
	
	// Guardar foto de portada del usuario
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/postFotoPortada/{idUsu}")
	public ResponseEntity<Respon> postFotoPortada(@RequestPart("file") MultipartFile file, @PathVariable Integer idUsu) {
		
		Usuario usu = new Usuario();
		usu = service.getUsuarioById(idUsu);
		
		GestionarArchivos gesArch = new GestionarArchivos();
		
		String mensajeOperacion = "";
		
		if(usu != null && file != null) {
			
			
			String filesPath = "";
			File fich = new File("");
				
			// borrar foto anterior
			if(usu.getFotoPortada() != "" || usu.getFotoPortada() != null) {
				
				filesPath = context.getRealPath("/ficheros/imgPor/" + usu.getIdUsu() + "-" + usu.getFotoPortada());
				fich = new File(filesPath);
				mensajeOperacion = gesArch.borrarFichero(fich);
			}
				
			// crear foto
			filesPath = context.getRealPath("/ficheros/imgPor/" + usu.getIdUsu() + "-" + file.getOriginalFilename().toLowerCase());
			fich = new File(filesPath);
			
			mensajeOperacion = gesArch.guardarArchivo(fich, file);
			
			// actualizar usuario
			usu.setFotoPortada("");
			usu.setFotoPortada(file.getOriginalFilename().toLowerCase());
			usu = service.updateUsuario(usu);
				
			
		} else {
			mensajeOperacion = "usuario o fichero vacio";
			
		}
		
		System.out.println(mensajeOperacion);
		
		Respon resp = new Respon();
		resp.setRespuesta(mensajeOperacion);
		return new ResponseEntity<Respon>(resp, HttpStatus.OK);
		
	}
	
	// Borrar foto de portada del usuario
	@CrossOrigin(origins = "http://localhost:4200")
	@DeleteMapping("/deleteFotoPortadaUsu/{idUsu}")
	public ResponseEntity<Respon> deleteFotoPortadaUsu (@PathVariable Integer idUsu) 
	{
		String mensajeOperacion = "";
		
		GestionarArchivos gesArch = new GestionarArchivos();
		
		Usuario usu = new Usuario();
		usu = service.getUsuarioById(idUsu);
		
		if(usu != null) {
			
			String fotoPerfilString = "";
			fotoPerfilString = usu.getFotoPortada();
			
			if(fotoPerfilString != "" || fotoPerfilString != null) {
				
				// borrar foto
				String filesPath = context.getRealPath("/ficheros/imgPor/" + usu.getIdUsu() + "-" + usu.getFotoPortada());
				File fichBorrar = new File(filesPath);
				
				mensajeOperacion = gesArch.borrarFichero(fichBorrar);
				
				if(mensajeOperacion.equals("fichero borrado")) {
					
					// Actualizar usuario
					usu.setFotoPortada("");
					usu = service.updateUsuario(usu);
				
				} else {
					mensajeOperacion = "fichero no borrado";
				}
				
			}
		}
		
		Respon resp = new Respon();
		resp.setRespuesta(mensajeOperacion);
		return new ResponseEntity<Respon>(resp, HttpStatus.OK); 
	}
	
	
	// -----------------------------------------------------------------------
	//Metodo para poner los datos de lo usuarios de una lista (Usado en las listas de busqueda de usuarios)
	
	private ArrayList<Usuario> quitarListas(ArrayList<Usuario> listaUsu){
		
		ArrayList<Usuario> listaUsuDev = new ArrayList<>();
		
		BuscarDatosUsuario busUsu = new BuscarDatosUsuario();
		
		for(int i = 0; listaUsu.size() > i; i++) {
			
			Usuario usu = new Usuario();
			usu = listaUsu.get(i);
			busUsu.vaciarObjetosUsu(usu);
			listaUsuDev.add(usu);
			
		}
		
		return listaUsuDev;
		
	}
	
	
	
}
