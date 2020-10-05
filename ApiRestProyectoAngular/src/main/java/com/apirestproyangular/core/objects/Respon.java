package com.apirestproyangular.core.objects;

public class Respon {

	private String respuesta;
	
	public Respon() {}

	public Respon(String respuesta) {
		
		this.respuesta = respuesta;
	}

	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

	@Override
	public String toString() {
		return "Response [respuesta=" + respuesta + "]";
	}
	
}
