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

import com.apirestproyangular.core.entity.Publicacion;
import com.apirestproyangular.core.entity.Videos;
import com.apirestproyangular.core.objects.BuscarDatosUsuario;
import com.apirestproyangular.core.objects.Response;
import com.apirestproyangular.core.service.VideosService;

@RestController
public class VideosController {

	@Autowired
	private VideosService service;
	
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/addVideo")
	public Response addVideos(@RequestBody Videos video) {
		String mensajeOperacion = ""; //Si esta vacio, problema con insertar la entrada
		
		if(video != null) {
			
			//Comprobar errores de insertado
			try {
				
				Videos videoDevuelta = new Videos();
				
				//Insertar y devolver usuario
				videoDevuelta = service.saveVideo(video);
				
				//Ver si se ha insertado en caso de no dar excepcion
				if(videoDevuelta != null) {
					mensajeOperacion = "Video insertado";
					
				}else {
					mensajeOperacion = "Problemas, video no insertado";
				}
				
				
			}catch(Exception e) {
				System.out.println("Problemas en la inserccion" + e.toString());
				mensajeOperacion = "Problemas, video no insertado";
			}
			
		} else {
			mensajeOperacion = "Video vacio";
		}
		
		Response resp = new Response();
		resp.setRespuesta(mensajeOperacion);
		
		return resp;
		
	}
	
	@PostMapping("/addVideos")
	public List<Videos> addVideos (@RequestBody List<Videos> videos){
		return service.saveVideos(videos);
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/videos")
	public List<Videos> findAllVideos(){
		return service.getVideos();
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/videos/{id}")
	public Videos findVideosById(@PathVariable int id) {
		return service.getVideoById(id);
	}
	
	@PutMapping("/updateVideos")
	public Videos updateVideos(@RequestBody Videos vid) {
		return service.updateVideo(vid);
	}
	
	@DeleteMapping("/deleteVideo/{id}")
	public String deleteVideo(@PathVariable int id) {
		return service.deleteVideo(id);
	}
	
	//metodos propios --------------------------------------------------------
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/getVideosUsuario/{idPubl}")
	public List<Videos> getVideosUsuario(@PathVariable Integer idUsu) {
		
		ArrayList<Videos> listaVideos = new ArrayList<>();
		listaVideos = (ArrayList<Videos>) service.findVideosUsuario(idUsu);
		listaVideos = quitarListas(listaVideos);
		return listaVideos;
		
	}
	
	//Vaciar listas
	private ArrayList<Videos> quitarListas(ArrayList<Videos> listaVideos) {
		
		ArrayList<Videos> listaVideosDev = new ArrayList<>();
		BuscarDatosUsuario busUsu = new BuscarDatosUsuario();
		
		for(int i = 0; listaVideos.size() > i ;i++) {
			
			Videos vid = new Videos();
			vid = listaVideos.get(i);
			vid.setUsuarioVideos(busUsu.vaciarObjetosUsu(vid.getUsuarioVideos()));
			listaVideosDev.add(vid);
		}
		
		return listaVideosDev;
	}
	
}
