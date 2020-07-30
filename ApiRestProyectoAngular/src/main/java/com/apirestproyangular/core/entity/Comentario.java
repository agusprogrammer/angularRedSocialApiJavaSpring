package com.apirestproyangular.core.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
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

@Entity(name="Comentario")
@Table(name = "comentario")

public class Comentario implements Serializable {
	
	@Id
	@Column(name = "id_comentario")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer idComentario;
	
	//Nota referencedColumnName hace referencia a la otra tabla
	@ManyToOne(fetch = FetchType.EAGER) // cascade = CascadeType.ALL
	@JoinColumn(name = "id_usuario")
	private Usuario usuarioComent;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_publicacion")
	private Publicacion publComent;
	
	@Column(name = "texto_comentario")
	private String textoComentario;
	
	@Column(name = "fecha_creacion_com")
	private LocalDateTime fechaCreacionCom;

	public Integer getIdComentario() {
		return idComentario;
	}

	public void setIdComentario(Integer idComentario) {
		this.idComentario = idComentario;
	}

	public Usuario getUsuarioComent() {
		return usuarioComent;
	}

	public void setUsuarioComent(Usuario usuarioComent) {
		this.usuarioComent = usuarioComent;
	}

	public Publicacion getPublComent() {
		return publComent;
	}

	public void setPublComent(Publicacion publComent) {
		this.publComent = publComent;
	}

	public String getTextoComentario() {
		return textoComentario;
	}

	public void setTextoComentario(String textoComentario) {
		this.textoComentario = textoComentario;
	}

	public LocalDateTime getFechaCreacionCom() {
		return fechaCreacionCom;
	}

	public void setFechaCreacionCom(LocalDateTime fechaCreacionCom) {
		this.fechaCreacionCom = fechaCreacionCom;
	}

	@Override
	public String toString() {
		return "Comentario [idComentario=" + idComentario + ", usuarioComent=" + usuarioComent + ", publComent="
				+ publComent + ", textoComentario=" + textoComentario + ", fechaCreacionCom=" + fechaCreacionCom + "]";
	}
	
	
}
