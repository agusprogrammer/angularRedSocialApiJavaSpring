package com.apirestproyangular.core.entity;

import java.io.Serializable;
import java.sql.Blob;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor


@Entity(name="Fotos")
@Table(name = "fotos")
public class Fotos implements Serializable {
	
	@Id
	@Column(name = "id_foto")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer idFoto;
	

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_usu") //id_usu campo de la tabla
	private Usuario usuarioFotos;
	
	@Column(name = "foto_string")
	private String fotoString;
	
	@Column(name = "fecha_subida_fot")
	private LocalDateTime fechaSubidaFoto;
	
	@Column(name = "foto_blob")
	private Blob fotoBlob;

	public Integer getIdFoto() {
		return idFoto;
	}

	public void setIdFoto(Integer idFoto) {
		this.idFoto = idFoto;
	}

	public Usuario getUsuarioFotos() {
		return usuarioFotos;
	}

	public void setUsuarioFotos(Usuario usuarioFotos) {
		this.usuarioFotos = usuarioFotos;
	}

	public String getFotoString() {
		return fotoString;
	}

	public void setFotoString(String fotoString) {
		this.fotoString = fotoString;
	}

	public LocalDateTime getFechaSubidaFoto() {
		return fechaSubidaFoto;
	}

	public void setFechaSubidaFoto(LocalDateTime fechaSubidaFoto) {
		this.fechaSubidaFoto = fechaSubidaFoto;
	}
	
	public Blob getFotoBlob() {
		return fotoBlob;
	}

	public void setFotoBlob(Blob fotoBlob) {
		this.fotoBlob = fotoBlob;
	}

	@Override
	public String toString() {
		return "Fotos [idFoto=" + idFoto + ", usuarioFotos=" + usuarioFotos + ", fotoString=" + fotoString
				+ ", fechaSubidaFoto=" + fechaSubidaFoto + "]";
	}
	
	
}
