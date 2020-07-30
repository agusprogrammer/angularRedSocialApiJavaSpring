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

@Entity(name="Videos")
@Table(name = "videos")
public class Videos implements Serializable {
	
	@Id
	@Column(name = "id_video")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer idVideo;
	
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_usu")
	private Usuario usuarioVideos;
	
	@Column(name = "video_string")
	private String videoString;
	
	@Column(name = "fecha_subida_vid")
	private LocalDateTime fechaSubidaVid;
	
	@Column(name = "video_blob")
	private Blob videoBlob;

	public Integer getIdVideo() {
		return idVideo;
	}

	public void setIdVideo(Integer idVideo) {
		this.idVideo = idVideo;
	}

	public Usuario getUsuarioVideos() {
		return usuarioVideos;
	}

	public void setUsuarioVideos(Usuario usuarioVideos) {
		this.usuarioVideos = usuarioVideos;
	}

	public String getVideoString() {
		return videoString;
	}

	public void setVideoString(String videoString) {
		this.videoString = videoString;
	}

	public LocalDateTime getFechaSubidaVid() {
		return fechaSubidaVid;
	}

	public void setFechaSubidaVid(LocalDateTime fechaSubidaVid) {
		this.fechaSubidaVid = fechaSubidaVid;
	}
	
	public Blob getVideoBlob() {
		return videoBlob;
	}

	public void setVideoBlob(Blob videoBlob) {
		this.videoBlob = videoBlob;
	}

	@Override
	public String toString() {
		return "Videos [idVideo=" + idVideo + ", usuarioVideos=" + usuarioVideos + ", videoString=" + videoString
				+ ", fechaSubidaVid=" + fechaSubidaVid + "]";
	}
	
	
}
