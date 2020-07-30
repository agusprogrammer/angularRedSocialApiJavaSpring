package com.apirestproyangular.core.entity;

import java.io.Serializable;
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

@Entity(name="Entradas")
@Table(name = "entradas")
public class Entradas implements Serializable {
	
	@Id
	@Column(name = "id_entrada")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer idEntrada;
	
	// Nota: el EAGER (cargarlo todo), permite resolver este error a diferencia del LAZY
	// No converter found capable of converting from type [java.lang.Integer] to type [@org.springframework.data.jpa.repository.Query com.apirestproyangular.core.entity.Entradas]
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_usu")
	private Usuario usuarioEntradas;
	
	@Column(name = "titulo_entrada")
	private String tituloEntrada;
	
	@Column(name = "texto_entrada")
	private String textoEntrada;
	
	@Column(name = "fecha_creacion_ent")
	private LocalDateTime fechaCreacionEnt;

	public Integer getIdEntrada() {
		return idEntrada;
	}

	public void setIdEntrada(Integer idEntrada) {
		this.idEntrada = idEntrada;
	}

	public Usuario getUsuarioEntradas() {
		return usuarioEntradas;
	}

	public void setUsuarioEntradas(Usuario usuarioEntradas) {
		this.usuarioEntradas = usuarioEntradas;
	}

	public String getTituloEntrada() {
		return tituloEntrada;
	}

	public void setTituloEntrada(String tituloEntrada) {
		this.tituloEntrada = tituloEntrada;
	}

	public String getTextoEntrada() {
		return textoEntrada;
	}

	public void setTextoEntrada(String textoEntrada) {
		this.textoEntrada = textoEntrada;
	}

	public LocalDateTime getFechaCreacionEnt() {
		return fechaCreacionEnt;
	}

	public void setFechaCreacionEnt(LocalDateTime fechaCreacionEnt) {
		this.fechaCreacionEnt = fechaCreacionEnt;
	}

	@Override
	public String toString() {
		return "Entradas [idEntrada=" + idEntrada + ", usuarioEntradas=" + usuarioEntradas + ", tituloEntrada="
				+ tituloEntrada + ", textoEntrada=" + textoEntrada + ", fechaCreacionEnt=" + fechaCreacionEnt + "]";
	}
	
}
