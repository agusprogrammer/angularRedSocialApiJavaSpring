package com.apirestproyangular.core.objects;

public class Response {

	private String respuesta;
	
	public Response() {}

	public Response(String respuesta) {
		
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
