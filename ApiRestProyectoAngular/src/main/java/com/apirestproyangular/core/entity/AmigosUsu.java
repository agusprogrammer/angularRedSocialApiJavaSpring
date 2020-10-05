package com.apirestproyangular.core.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity(name="AmigosUsu")
@Table(name="amigos_usu")
public class AmigosUsu implements Serializable {
	
	@EmbeddedId
	@AttributeOverrides({													//Nota: el length es para los Strings
			@AttributeOverride(name = "id_solicitante", column = @Column(name = "id_solicitante", nullable = false)),
			@AttributeOverride(name = "id_receptor", column = @Column(name = "id_receptor", nullable = false)) })
	private AmigosUsuId idUsuAm;
	
	//Relaciones
	//Usuario que envia peticion de amistad
	@ManyToOne(fetch = FetchType.EAGER) // nullable = false, insertable = false, updatable = false
	@JoinColumn(name="id_solicitante", nullable = false, insertable = false, updatable = false)
	private Usuario usuAmIdSolicitante;
	
	//Amigo del usuario
	@ManyToOne(fetch = FetchType.EAGER) // nullable = false, insertable = false, updatable = false
	@JoinColumn(name = "id_receptor", nullable = false, insertable = false, updatable = false)
	private Usuario usuAmIdReceptor;
	
	//Fecha que se envio la solicitud
	@Column(name = "fecha_enviada")
	private LocalDateTime fechaEnviada;
	
	//Estado de la solicitud
	// 1 aceptada
	// 0 no aceptada (De momento)
	// si se borra es rechazada
	@Column(name = "solicitud_aceptada")
	private Integer solicitudAceptada; // Boolean

	public AmigosUsuId getIdUsuAm() {
		return idUsuAm;
	}

	
	public void setIdUsuAm(AmigosUsuId idUsuAm) {
		this.idUsuAm = idUsuAm;
	}

	public Usuario getUsuAmIdSolicitante() {
		return usuAmIdSolicitante;
	}

	public void setUsuAmIdSolicitante(Usuario usuAmIdSolicitante) {
		this.usuAmIdSolicitante = usuAmIdSolicitante;
	}

	public Usuario getUsuAmIdReceptor() {
		return usuAmIdReceptor;
	}

	public void setUsuAmIdReceptor(Usuario usuAmIdReceptor) {
		this.usuAmIdReceptor = usuAmIdReceptor;
	}

	public LocalDateTime getFechaEnviada() {
		return fechaEnviada;
	}

	public void setFechaEnviada(LocalDateTime fechaEnviada) {
		this.fechaEnviada = fechaEnviada;
	}

	public Integer getSolicitudAceptada() {
		return solicitudAceptada;
	}

	public void setSolicitudAceptada(Integer solicitudAceptada) {
		this.solicitudAceptada = solicitudAceptada;
	}

	@Override
	public String toString() {
		return "AmigosUsu [idUsuAm=" + idUsuAm + ", usuAmIdSolicitante=" + usuAmIdSolicitante + ", usuAmIdReceptor="
				+ usuAmIdReceptor + ", fechaEnviada=" + fechaEnviada + ", solicitudAceptada=" + solicitudAceptada + "]";
	}
	
}
