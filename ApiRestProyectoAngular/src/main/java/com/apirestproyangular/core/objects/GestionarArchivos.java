package com.apirestproyangular.core.objects;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

public class GestionarArchivos {
	
	private FileInputStream fis;
	private FileOutputStream fos;

	public GestionarArchivos() {}
	
	//Metodo para obtener un archivo de imagen
	public String obtenerUnFichImg(File archivo) {
		
		String ficheroString = "";
		String encodeBase64 = null;
		
		try {
			
			String extension = FilenameUtils.getExtension(archivo.getName());
			fis = new FileInputStream(archivo);
			byte[] bytes = new byte[(int)archivo.length()];
			fis.read(bytes);
			encodeBase64 = Base64.getEncoder().encodeToString(bytes);
			ficheroString = "data:image/" + extension + ";base64," + encodeBase64;
			
			fis.close();
			
		} catch(Exception e) {
			System.out.println("Problemas al obtener el archivo: " + e.toString());
		}
		
		return ficheroString;
	}
	
	//metodo para obtener un fichero de video
	public String obtenerUnFichVid(File archivo) {
		
		String ficheroString = "";
		String encodeBase64 = null;
		
		try {
			
			String extension = FilenameUtils.getExtension(archivo.getName());
			fis = new FileInputStream(archivo);
			byte[] bytes = new byte[(int)archivo.length()];
			fis.read(bytes);
			encodeBase64 = Base64.getEncoder().encodeToString(bytes);
			ficheroString = "data:video/" + extension + ";base64," + encodeBase64;
			
			fis.close();
			
		} catch(Exception e) {
			System.out.println("Problemas al obtener el archivo: " + e.toString());
		}
		
		return ficheroString;
	}
	
	//Borrar este metodo si eso
	/*
	public byte[] abrirArchivo(File archivo) {
		byte[] imagenBytes = new byte[1024*100];
		
		try{
			
			fis = new FileInputStream(archivo);
			fis.read(imagenBytes);
			fis.close();
			
		} catch(Exception e) {
			System.out.println("Problemas al abrir la imagen:");
			System.out.println(e.toString());
		}
		
		return imagenBytes;
	}
	*/
	
	public String guardarArchivo(File archivo, MultipartFile file) {
		
		String respuesta = "archivo no guardado";
		
		// obtener bytes
		byte[] fileBytes = null;
		
		try {
			
			fileBytes = ((MultipartFile) file).getBytes();
			
		} catch (IOException e1) {
			System.out.println("Problemas al obtener los bytes de la imagen:");
			System.out.println(e1.toString());
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			
			fos = new FileOutputStream(archivo);
			fos.write(fileBytes);
			fos.close();
			
			respuesta = "archivo guardado";
			
		}catch(Exception e) {
			System.out.println("Problemas al guardar la imagen:");
			System.out.println(e.toString());
		}
		
		return respuesta;
	}

	
	// obtener un fichero de un usuario
	/*
	public MultipartFile obtenerFichMult(File folder) {
		
		byte[] content = null;
		content = obtenerFichBytes(folder);
		
		String name = folder.getName();
		String originalFileName = folder.getName();
		String contentType = "";
		
		
		MultipartFile result = new MultipartFile(name,
                originalFileName, contentType, content);
		
		return result;	
	}
	*/
	
	/*
	public byte[] obtenerFichBytes(File folder) {
		
		//File fichero = new File("");
		
		byte[] imagenBytes = new byte[1024*100];
		
		try{
			
			fis = new FileInputStream(folder);
			fis.read(imagenBytes);
			fis.close();
			
			// poner bytes al archivo, o devolver los bytes para recomponer en type script
			// folder.
			
		} catch(Exception e) {
			System.out.println("Problemas al abrir la imagen:");
			System.out.println(e.toString());
		}
		
		return imagenBytes;
	}
	*/
	
	//Borrar fichero
	public String borrarFichero(File fichBorrar) {
		
		String respuesta = "";
		
		if(fichBorrar.delete()) {
			respuesta = "fichero borrado";
		} else {
			respuesta = "fichero no borrado";
		}
		
		return respuesta;
		
	}
	
	
}
