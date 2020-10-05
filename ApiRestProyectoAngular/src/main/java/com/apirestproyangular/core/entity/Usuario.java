package com.apirestproyangular.core.entity;

import java.io.Serializable;
import java.sql.Blob;
import java.time.LocalDateTime;
import java.util.Date;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity(name = "Usuario")
@Table(name = "usuario")

public class Usuario implements Serializable {
	
	@Id
	@Column(name = "id_usu")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer idUsu;
	
	@Column(name = "nombre_usu")
	private String nombreUsu;
	
	@Column(name = "fecha_alta")
	private Date fechaAlta;
	
	@Column(name = "pais")
	private String pais;
	
	@Column(name = "ciudad")
	private String ciudad;
	
	@Column(name = "region")
	private String region;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "nombre")
	private String nombre;
	
	@Column(name = "apellidos")
	private String apellidos;
	
	@Column(name = "fecha_nacimiento")
	private Date fechaNacimiento;
	
	@Column(name = "telefono")
	private String telefono;
	
	@Column(name = "email_entrada")
	private String emailEntrada;
	
	@Column(name = "contrasenya")
	private String contrasenya;
	
	@Column(name = "perfil_privado")
	private Integer perfilPrivado; //Boolean
	
	@Column(name = "aceptada_pol_priva")
	private Integer aceptadaPolPriva; //Boolean
	
	@Column(name = "es_administrador")
	private Integer esAdministrador; //Boolean
	
	@Column(name = "usuario_activo")
	private Integer usuarioActivo; //Boolean
	
	@Column(name = "usuario_baneado")
	private Integer usuarioBaneado;//Boolean
	
	@Column(name = "fecha_fin_baneo")
	private LocalDateTime fechaFinBaneo;
	
	@Column(name = "estado")
	private String estado;
	
	@Column(name = "num_visitas")
	private Integer numVisitas;
	
	@Column(name = "fecha_ultimo_login")
	private LocalDateTime fechaUltimoLogin;
	
	@Column(name = "foto_perfil")
	//private Blob fotoPerfil;
	private String fotoPerfil;
	
	
	@Column(name = "foto_portada")
	//private Blob fotoPortada;
	private String fotoPortada;
	
	//Relaciones
	// mappedBy = "usuarioComent",
	// @OneToMany(targetEntity = Comentario.class, fetch = FetchType.LAZY)
	//@OneToMany(targetEntity = Comentario.class, fetch = FetchType.LAZY)
	//@JoinColumn(name="comentario_ibfk_1", referencedColumnName = "id_usu")
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioComent")
	private Set<Comentario> comentarios = new HashSet<Comentario>();
	
	// mappedBy = "usuarioPubl",
	// @OneToMany(targetEntity = Publicacion.class, fetch = FetchType.LAZY)
	//@OneToMany(targetEntity = Publicacion.class, fetch = FetchType.LAZY)
	//@JoinColumn(name="", referencedColumnName = "id_usu")
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioPubl")
	private Set<Publicacion> publicaciones = new HashSet<Publicacion>();
	
	// mappedBy = "usuarioFotos",
	// @OneToMany(targetEntity = Fotos.class, fetch = FetchType.LAZY)
	//@OneToMany(targetEntity = Fotos.class, fetch = FetchType.LAZY)
	//@JoinColumn(name="", referencedColumnName = "id_usu")
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioFotos")
	private Set<Fotos> fotos = new HashSet<Fotos>();
	
	// mappedBy = "usuarioVideos",
	// @OneToMany(targetEntity = Videos.class, fetch = FetchType.LAZY)
	//@OneToMany(targetEntity = Videos.class, fetch = FetchType.LAZY)
	//@JoinColumn(name="", referencedColumnName = "id_usu")
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioVideos")
	private Set<Videos> videos = new HashSet<Videos>();
	
	// mappedBy = "usuarioEntradas",
	// @OneToMany(targetEntity = Entradas.class, fetch = FetchType.LAZY)
	//@OneToMany(targetEntity = Entradas.class, fetch = FetchType.LAZY)
	//@JoinColumn(name="", referencedColumnName = "id_usu")
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioEntradas")
	private Set<Entradas> entradas = new HashSet<Entradas>();
	
	//Relacion tabla amigos
	
	// mappedBy = "usuAmIdSolicitante",
	// @OneToMany(targetEntity = AmigosUsu.class, fetch = FetchType.LAZY)
	//@OneToMany(targetEntity = AmigosUsu.class, fetch = FetchType.LAZY)
	//@JoinColumn(name="", referencedColumnName = "")
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "usuAmIdSolicitante")
	private Set<AmigosUsu> amigosUsuSolicitudes = new HashSet<AmigosUsu>();
	
	// mappedBy = "usuAmIdReceptor",
	// @OneToMany(targetEntity = AmigosUsu.class , fetch = FetchType.LAZY)
	//@OneToMany(targetEntity = AmigosUsu.class, fetch = FetchType.LAZY)
	//@JoinColumn(name="", referencedColumnName = "")
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "usuAmIdReceptor")
	private Set<AmigosUsu> amigosUsuRecibidos = new HashSet<AmigosUsu>();

	public Integer getIdUsu() {
		return idUsu;
	}

	public void setIdUsu(Integer idUsu) {
		this.idUsu = idUsu;
	}

	public String getNombreUsu() {
		return nombreUsu;
	}

	public void setNombreUsu(String nombreUsu) {
		this.nombreUsu = nombreUsu;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEmailEntrada() {
		return emailEntrada;
	}

	public void setEmailEntrada(String emailEntrada) {
		this.emailEntrada = emailEntrada;
	}

	public String getContrasenya() {
		return contrasenya;
	}

	public void setContrasenya(String contrasenya) {
		this.contrasenya = contrasenya;
	}

	public Integer getPerfilPrivado() {
		return perfilPrivado;
	}

	public void setPerfilPrivado(Integer perfilPrivado) {
		this.perfilPrivado = perfilPrivado;
	}

	public Integer getAceptadaPolPriva() {
		return aceptadaPolPriva;
	}

	public void setAceptadaPolPriva(Integer aceptadaPolPriva) {
		this.aceptadaPolPriva = aceptadaPolPriva;
	}

	public Integer getEsAdministrador() {
		return esAdministrador;
	}

	public void setEsAdministrador(Integer esAdministrador) {
		this.esAdministrador = esAdministrador;
	}

	public Integer getUsuarioActivo() {
		return usuarioActivo;
	}

	public void setUsuarioActivo(Integer usuarioActivo) {
		this.usuarioActivo = usuarioActivo;
	}

	public Integer getUsuarioBaneado() {
		return usuarioBaneado;
	}

	public void setUsuarioBaneado(Integer usuarioBaneado) {
		this.usuarioBaneado = usuarioBaneado;
	}

	public LocalDateTime getFechaFinBaneo() {
		return fechaFinBaneo;
	}

	public void setFechaFinBaneo(LocalDateTime fechaFinBaneo) {
		this.fechaFinBaneo = fechaFinBaneo;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Integer getNumVisitas() {
		return numVisitas;
	}

	public void setNumVisitas(Integer numVisitas) {
		this.numVisitas = numVisitas;
	}

	public LocalDateTime getFechaUltimoLogin() {
		return fechaUltimoLogin;
	}

	public void setFechaUltimoLogin(LocalDateTime fechaUltimoLogin) {
		this.fechaUltimoLogin = fechaUltimoLogin;
	}

	public String getFotoPerfil() {
		return fotoPerfil;
	}

	public void setFotoPerfil(String fotoPerfil) {
		this.fotoPerfil = fotoPerfil;
	}

	public String getFotoPortada() {
		return fotoPortada;
	}

	public void setFotoPortada(String fotoPortada) {
		this.fotoPortada = fotoPortada;
	}

	public Set<Comentario> getComentarios() {
		return comentarios;
	}

	public void setComentarios(Set<Comentario> comentarios) {
		this.comentarios = comentarios;
	}

	public Set<Publicacion> getPublicaciones() {
		return publicaciones;
	}

	public void setPublicaciones(Set<Publicacion> publicaciones) {
		this.publicaciones = publicaciones;
	}

	public Set<Fotos> getFotos() {
		return fotos;
	}

	public void setFotos(Set<Fotos> fotos) {
		this.fotos = fotos;
	}

	public Set<Videos> getVideos() {
		return videos;
	}

	public void setVideos(Set<Videos> videos) {
		this.videos = videos;
	}

	public Set<Entradas> getEntradas() {
		return entradas;
	}

	public void setEntradas(Set<Entradas> entradas) {
		this.entradas = entradas;
	}

	public Set<AmigosUsu> getAmigosUsuSolicitudes() {
		return amigosUsuSolicitudes;
	}

	public void setAmigosUsuSolicitudes(Set<AmigosUsu> amigosUsuSolicitudes) {
		this.amigosUsuSolicitudes = amigosUsuSolicitudes;
	}

	public Set<AmigosUsu> getAmigosUsuRecibidos() {
		return amigosUsuRecibidos;
	}

	public void setAmigosUsuRecibidos(Set<AmigosUsu> amigosUsuRecibidos) {
		this.amigosUsuRecibidos = amigosUsuRecibidos;
	}

	@Override
	public String toString() {
		return "Usuario [idUsu=" + idUsu + ", nombreUsu=" + nombreUsu + ", fechaAlta=" + fechaAlta + ", pais=" + pais
				+ ", ciudad=" + ciudad + ", region=" + region + ", email=" + email + ", nombre=" + nombre
				+ ", apellidos=" + apellidos + ", fechaNacimiento=" + fechaNacimiento + ", telefono=" + telefono
				+ ", emailEntrada=" + emailEntrada + ", contrasenya=" + contrasenya + ", perfilPrivado=" + perfilPrivado
				+ ", aceptadaPolPriva=" + aceptadaPolPriva + ", esAdministrador=" + esAdministrador + ", usuarioActivo="
				+ usuarioActivo + ", usuarioBaneado=" + usuarioBaneado + ", fechaFinBaneo=" + fechaFinBaneo
				+ ", estado=" + estado + ", numVisitas=" + numVisitas + ", fechaUltimoLogin=" + fechaUltimoLogin
				+ ", fotoPerfil=" + fotoPerfil + ", fotoPortada=" + fotoPortada + ", comentarios=" + comentarios
				+ ", publicaciones=" + publicaciones + ", fotos=" + fotos + ", videos=" + videos + ", entradas="
				+ entradas + ", amigosUsuSolicitudes=" + amigosUsuSolicitudes + ", amigosUsuRecibidos="
				+ amigosUsuRecibidos + "]";
	}
	
}
