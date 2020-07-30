package com.apirestproyangular.core.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Embeddable
public class AmigosUsuId implements Serializable {
	
	@Column(name = "id_solicitante", nullable = false)
	private Integer idSolicitante;
	@Column(name = "id_receptor", nullable = false)
	private Integer idReceptor;
	
	
	public Integer getIdSolicitante() {
		return idSolicitante;
	}
	
	public void setIdSolicitante(Integer idSolicitante) {
		this.idSolicitante = idSolicitante;
	}
	
	public Integer getIdReceptor() {
		return idReceptor;
	}
	
	public void setIdReceptor(Integer idReceptor) {
		this.idReceptor = idReceptor;
	}

	@Override
	public String toString() {
		return "AmigosUsuId [idSolicitante=" + idSolicitante + ", idReceptor=" + idReceptor + "]";
	}
	
}
