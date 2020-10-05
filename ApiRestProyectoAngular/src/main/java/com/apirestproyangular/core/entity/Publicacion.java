package com.apirestproyangular.core.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity(name="Publicacion")
@Table(name = "publicacion")

public class Publicacion implements Serializable {
	
	@Id
	@Column(name = "id_publicacion")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer idPublicacion;
	
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_usu")
	private Usuario usuarioPubl;
	
	// mappedBy = " publComent"
	//@OneToMany(targetEntity = Comentario.class, fetch = FetchType.LAZY)
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "publComent")
	private Set<Comentario> comentariosPublicacion = new HashSet<Comentario>();
	
	@Column(name = "titulo_publicacion")
	private String tituloPublicacion;
	
	@Column(name = "texto_publicacion")
	private String textoPublicacion;
	
	@Column(name = "fecha_creacion_pub")
	private LocalDateTime fechaCreacionPub;
	
	@Column(name = "pub_es_privada")
	private Integer pubEsPrivada;

	public Integer getIdPublicacion() {
		return idPublicacion;
	}

	public void setIdPublicacion(Integer idPublicacion) {
		this.idPublicacion = idPublicacion;
	}

	public Usuario getUsuarioPubl() {
		return usuarioPubl;
	}

	public void setUsuarioPubl(Usuario usuarioPubl) {
		this.usuarioPubl = usuarioPubl;
	}

	public Set<Comentario> getComentariosPublicacion() {
		return comentariosPublicacion;
	}

	public void setComentariosPublicacion(Set<Comentario> comentariosPublicacion) {
		this.comentariosPublicacion = comentariosPublicacion;
	}

	public String getTituloPublicacion() {
		return tituloPublicacion;
	}

	public void setTituloPublicacion(String tituloPublicacion) {
		this.tituloPublicacion = tituloPublicacion;
	}

	public String getTextoPublicacion() {
		return textoPublicacion;
	}

	public void setTextoPublicacion(String textoPublicacion) {
		this.textoPublicacion = textoPublicacion;
	}

	public LocalDateTime getFechaCreacionPub() {
		return fechaCreacionPub;
	}

	public void setFechaCreacionPub(LocalDateTime fechaCreacionPub) {
		this.fechaCreacionPub = fechaCreacionPub;
	}

	public Integer getPubEsPrivada() {
		return pubEsPrivada;
	}

	public void setPubEsPrivada(Integer pubEsPrivada) {
		this.pubEsPrivada = pubEsPrivada;
	}

	@Override
	public String toString() {
		return "Publicacion [idPublicacion=" + idPublicacion + ", usuarioPubl=" + usuarioPubl
				+ ", comentariosPublicacion=" + comentariosPublicacion + ", tituloPublicacion=" + tituloPublicacion
				+ ", textoPublicacion=" + textoPublicacion + ", fechaCreacionPub=" + fechaCreacionPub
				+ ", pubEsPrivada=" + pubEsPrivada + "]";
	}
	
	
}
