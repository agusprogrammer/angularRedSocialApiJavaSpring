package com.apirestproyangular.core.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.apirestproyangular.core.entity.Usuario;
import com.apirestproyangular.core.objects.BuscarDatosUsuario;
import com.apirestproyangular.core.objects.Response;
import com.apirestproyangular.core.service.UsuarioService;

@RestController
public class UsuarioController {

	//Ejemplo de las rutas: http://localhost:9191/usuarios
	
	@Autowired
	private UsuarioService service;
	
	
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/addUsuario")
	// public Usuario addUsuario(@RequestBody Usuario usu) { //Lo de antes, devolvia un usuario
	public Response addUsuario(@RequestBody Usuario usu) {
		
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
		
		
		
		Response resp = new Response();
		resp.setRespuesta(mensajeOperacion);
		
		return resp;
		
	}
	
	@PostMapping("/addUsuarios")
	public List<Usuario> addUsuarios (@RequestBody List<Usuario> usu){
		return service.saveUsuarios(usu);
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/usuarios")
	public List<Usuario> findAllUsuarios(){
		
		return service.getUsuarios();
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/usuarios/{id}")
	public Usuario findUsuarioById(@PathVariable int id) {
		// return service.getUsuarioById(id);
		Usuario usu = new Usuario();
		usu = service.getUsuarioById(id);
		return usu;
	}
	
	@PutMapping("/updateUsuario")
	public Usuario updateUsuario(@RequestBody Usuario usu) {
		return service.updateUsuario(usu);
	}
	
	@DeleteMapping("/deleteUsuario/{id}")
	public String deleteUsuario(@PathVariable int id) {
		return service.deleteUsuario(id);
	}
	
	//Metodos propios controlador usuario ---------------------------
	
	//Parte del login -------------------
	
	// http://localhost:9191/usuario/loginUsuario/{emailent}/{contrasenya}
	
	// @CrossOrigin(origins = "http://localhost:4200/home/Network/login")
	//Manera correcta de configurar el origen del cors (mirar mas sobre este tema)
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/loginUsuario/{emailent}/{contrasenya}")
	public Usuario getUsuarioLogin(@PathVariable String emailent, @PathVariable String contrasenya) {
		
		Usuario usu = new Usuario();
		usu = service.findUsuLogin(emailent, contrasenya); //metodo para el login
		return usu;
	}
	
	//Buscar usuarios -------------------------------------------------------
	//Parte buscar nuevos amigos (devolver listas de usuarios completos(sin listas especificas))
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/getNuevosAmigos/{idUsu}")
	public List<Usuario> getNuevosAmigos(@PathVariable Integer idUsu) {
		
		ArrayList<Usuario> listaUsu = new ArrayList<Usuario>(); 
		listaUsu = (ArrayList<Usuario>) service.findNuevosAmigos(idUsu);
		listaUsu = quitarListas(listaUsu);
		return listaUsu;
	} 
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/getAmigosPais/{pais}")
	public List<Usuario> getNuevosAmigosPais(@PathVariable String pais) {
		
		ArrayList<Usuario> listaUsu = new ArrayList<Usuario>(); 
		listaUsu = (ArrayList<Usuario>) service.findNuevosAmigosPais(pais);
		listaUsu = quitarListas(listaUsu);
		
		return listaUsu;
		
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/getAmigosRegion/{region}")
	public List<Usuario> getNuevosAmigosRegion(@PathVariable String region) {
		
		ArrayList<Usuario> listaUsu = new ArrayList<Usuario>(); 
		listaUsu = (ArrayList<Usuario>) service.findNuevosAmigosRegion(region);
		listaUsu = quitarListas(listaUsu);
		
		return listaUsu;
		
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/getAmigosCiudad/{ciudad}")
	public List<Usuario> getNuevosAmigosCiudad(@PathVariable String ciudad) {
		
		ArrayList<Usuario> listaUsu = new ArrayList<Usuario>(); 
		listaUsu = (ArrayList<Usuario>) service.findNuevosAmigosCiudad(ciudad);
		listaUsu = quitarListas(listaUsu);
		
		return listaUsu;
		
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
