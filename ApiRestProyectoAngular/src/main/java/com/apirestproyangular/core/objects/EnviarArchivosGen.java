package com.apirestproyangular.core.objects;

import java.io.File;
import java.io.IOException;
import java.sql.Blob;

import org.springframework.web.multipart.MultipartFile;

// clase generia para enviar archivos al front end
public class EnviarArchivosGen {
	
	//sino se utiliza esta clase, borrar en el futuro
	
	private Object obj;
	private File fichero;
	private byte[] byteArch;
	private String tipoArch;
	private String rutaArch;
	private String encodeBase64;
	private String ficheroCompletoString;

	public EnviarArchivosGen() {}
	
	public EnviarArchivosGen(Object obj, File fichero, byte[] byteArch, String tipoArch, String rutaArch, String encodeBase64, String ficheroCompletoString) {

		this.obj = obj;
		this.fichero = fichero;
		this.byteArch = byteArch;
		this.tipoArch = tipoArch;
		this.rutaArch = rutaArch;
		this.encodeBase64 = encodeBase64;
		this.ficheroCompletoString = ficheroCompletoString;
 	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public File getFichero() {
		return fichero;
	}

	public void setFichero(File fichero) {
		this.fichero = fichero;
	}

	public byte[] getByteArch() {
		return byteArch;
	}

	public void setByteArch(byte[] byteArch) {
		this.byteArch = byteArch;
	}

	public String getTipoArch() {
		return tipoArch;
	}

	public void setTipoArch(String tipoArch) {
		this.tipoArch = tipoArch;
	}

	public String getRutaArch() {
		return rutaArch;
	}

	public void setRutaArch(String rutaArch) {
		this.rutaArch = rutaArch;
	}

	public String getEncodeBase64() {
		return encodeBase64;
	}

	public void setEncodeBase64(String encodeBase64) {
		this.encodeBase64 = encodeBase64;
	}

	public String getFicheroCompletoString() {
		return ficheroCompletoString;
	}

	public void setFicheroCompletoString(String ficheroCompletoString) {
		this.ficheroCompletoString = ficheroCompletoString;
	}

}
