package com.apirestproyangular.core.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import com.apirestproyangular.core.entity.Publicacion;
import com.apirestproyangular.core.entity.Videos;
import com.apirestproyangular.core.objects.BuscarDatosUsuario;
import com.apirestproyangular.core.objects.EnviarArchivosGen;
import com.apirestproyangular.core.objects.GestionarArchivos;
import com.apirestproyangular.core.objects.PonerFecha;
import com.apirestproyangular.core.objects.Respon;
import com.apirestproyangular.core.service.UsuarioService;
import com.apirestproyangular.core.service.VideosService;

@RestController
public class VideosController {

	@Autowired ServletContext context;
	@Autowired private VideosService service;
	@Autowired private UsuarioService usuServ;
	
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/addVideo")
	public ResponseEntity<Respon> addVideos(@RequestBody Videos video) {
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
		
		Respon resp = new Respon();
		resp.setRespuesta(mensajeOperacion);
		
		return new ResponseEntity<Respon>(resp, HttpStatus.OK);
		
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/addVideos")
	public ResponseEntity<List<Videos>> addVideos (@RequestBody List<Videos> videos){
		return new ResponseEntity<List<Videos>>(service.saveVideos(videos), HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/videos")
	public ResponseEntity<List<Videos>> findAllVideos(){
		return new ResponseEntity<List<Videos>>(service.getVideos(), HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/videos/{id}")
	public ResponseEntity<Videos> findVideosById(@PathVariable int id) {
		return new ResponseEntity<Videos>(service.getVideoById(id), HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@PutMapping("/updateVideos")
	public ResponseEntity<Videos> updateVideos(@RequestBody Videos vid) {
		return new ResponseEntity<Videos>(service.updateVideo(vid), HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@DeleteMapping("/deleteVideo/{id}")
	public ResponseEntity<Respon> deleteVideo(@PathVariable int id) {
		
		Respon resp = new Respon();
		resp.setRespuesta(service.deleteVideo(id));
		
		return new ResponseEntity<Respon>(resp, HttpStatus.OK);
	}
	
	// Metodos para los archivos -----------------------------------
	
	//Subir video
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/addArchivoVid/{idUsuVid}")
	public ResponseEntity<Respon> postSubirArchivoVid(@RequestPart("file") MultipartFile file, @PathVariable Integer idUsuVid) {
		
		String mensajeOperacion = "";
		GestionarArchivos gesArch = new GestionarArchivos();
		
		if(file != null && idUsuVid != null) {
			
			if(idUsuVid > 0) {
				
				Videos vid = new Videos();
				vid = crearObjetoVidBD (file, idUsuVid);
				
				if(vid != null) {
					
					String filesPath = context.getRealPath("/ficheros/vid/" + vid.getIdVideo() + "-" + vid.getUsuarioVideos().getIdUsu() + "-" + file.getOriginalFilename().toLowerCase());
					File fichGuardar = new File(filesPath);
					System.out.println("Fichero imagen: " + fichGuardar.getPath() + " " + fichGuardar.getName());
					mensajeOperacion = gesArch.guardarArchivo(fichGuardar, file);
					
				} else {
					mensajeOperacion = "video no creado en la BD";
				}
				
			} else {
				mensajeOperacion = "El id de usuario tiene que ser mayor a 0";
			}
		
		} else {
			mensajeOperacion = "El fichero y el id de usuario no pueden estar vacios";
		}
		
		Respon resp = new Respon();
		resp.setRespuesta(mensajeOperacion);
		
		return new ResponseEntity<Respon>(resp, HttpStatus.OK);
	}
	
	//Guardar video en la BD
	private Videos crearObjetoVidBD(MultipartFile file, Integer idUsuVid) {
		
		String mensajeOperacion = "";
		
		PonerFecha ponerFecha = new PonerFecha();
		
		Videos vid = new Videos();
		vid.setUsuarioVideos(usuServ.getUsuarioById(idUsuVid));
		vid.setFechaSubidaVid(ponerFecha.ponerFechaHoraActual());
		vid.setVideoString(file.getOriginalFilename().toLowerCase());
		
		Videos vidDevuelta = new Videos();
		
		try {
			
			vidDevuelta = service.saveVideo(vid);
			
			if(vidDevuelta != null) mensajeOperacion = "objeto video insertado";
			else mensajeOperacion = "Problemas, objeto video no insertado";
			
		} catch(Exception e) {
			
			System.out.println("Problemas en la inserccion" + e.toString());
			mensajeOperacion = "Problemas, video no insertado";
		}
		
		return vidDevuelta;
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/getArchivosVideoUsuario/{idUsu}")
	public ResponseEntity<List<EnviarArchivosGen>> getArchivosVideoUsuario(@PathVariable Integer idUsu) {
		
		List<EnviarArchivosGen> listaVideosEnv = new ArrayList<EnviarArchivosGen>();
		GestionarArchivos gesArch = new GestionarArchivos();
		
		//Obtener videos
		ArrayList<Videos> listaVideos = new ArrayList<Videos>();
		
		try {
			listaVideos = (ArrayList<Videos>) service.findVideosUsuario(idUsu);
		} catch(Exception e) {
			System.out.println("Problema al buscar los videos de un usuario: " + e.toString());
		}
		
		listaVideos = quitarListas(listaVideos);
		
		for(Videos vid: listaVideos) {
			
			EnviarArchivosGen envGen = new EnviarArchivosGen();
			envGen.setObj(vid);
			
			String filesPath = context.getRealPath("/ficheros/vid/" + vid.getIdVideo() + "-" + idUsu + "-" + vid.getVideoString());
			File fich = new File(filesPath);
			
			if(!fich.isDirectory()) {
				
				envGen.setFichero(fich);
				envGen.setRutaArch(filesPath);
				envGen.setTipoArch(FilenameUtils.getExtension(fich.getName()));
				envGen.setFicheroCompletoString(gesArch.obtenerUnFichVid(fich));
				listaVideosEnv.add(envGen);
			}
		}
		
		return new ResponseEntity<List<EnviarArchivosGen>>(listaVideosEnv, HttpStatus.OK);
	}
	
	// metodo para comprobar obtener archivo de la api --------------------------------------------
	// obtiene un archivo especifico
	
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/getArchVidUsuario/{idVideo}/{idUsu}/{vidString}")
	public ResponseEntity<Resource> getArchVidUsuario(@PathVariable Integer idVideo, @PathVariable Integer idUsu, @PathVariable String vidString) {
		
		String filesPath = context.getRealPath("/ficheros/vid/" + idVideo + "-" + idUsu + "-" + vidString);
		File file = new File(filesPath);
		
		Path path = Paths.get(file.getAbsolutePath());
        ByteArrayResource resource = null;
        
		try {
			
			resource = new ByteArrayResource(Files.readAllBytes(path));
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
												
        HttpHeaders header = new HttpHeaders();	// filename=img.jpg  //+ file.getName()
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=video.mp4");
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");
        
		return ResponseEntity.ok().headers(header).contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
	}
	
	
	// mirar esto: https://stackoverflow.com/questions/35680932/download-a-file-from-spring-boot-rest-service
	
	@CrossOrigin(origins = "http://localhost:4200")
	@DeleteMapping("/deleteArchivosVid/{idVid}")
	public ResponseEntity<Respon> deleteArchivosVid(@PathVariable int idVid) {
		
		Videos vid = new Videos();
		vid = service.getVideoById(idVid);
		
		String filesPath = context.getRealPath("/ficheros/vid/" + vid.getIdVideo() + "-" + vid.getUsuarioVideos().getIdUsu() + "-" + vid.getVideoString());
		File fichBorrar = new File(filesPath);
		System.out.println("Fichero a borrar: " + fichBorrar.getPath() + " " + fichBorrar.getName());
		
		GestionarArchivos gesArch = new GestionarArchivos();
		
		String fichRespBorrar = "";
		fichRespBorrar = gesArch.borrarFichero(fichBorrar);
		
		if(fichRespBorrar.equals("fichero borrado")) {
			
			//Borrar en BD
			String respServ = "";
			respServ = service.deleteVideo(idVid);
			
			if(respServ.equals("no")) {
				fichRespBorrar = "problemas al borrar";
			}
		
		} else {
			fichRespBorrar = "fichero no borrado";
		}
		
		Respon resp = new Respon();
		return new ResponseEntity<Respon>(resp, HttpStatus.OK);
		
	}
	
	//metodos propios --------------------------------------------------------
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/getVideosUsuario/{idPubl}")
	public ResponseEntity<List<Videos>> getVideosUsuario(@PathVariable Integer idUsu) {
		
		ArrayList<Videos> listaVideos = new ArrayList<>();
		listaVideos = (ArrayList<Videos>) service.findVideosUsuario(idUsu);
		listaVideos = quitarListas(listaVideos);
		return new ResponseEntity<List<Videos>>(listaVideos, HttpStatus.OK);
		
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
