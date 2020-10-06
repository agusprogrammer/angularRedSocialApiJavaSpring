/* Esta es la base de datos definitiva, 
pero en el futuro pueden haber cambios en la base de datos*/

/* comandos para la creacion del usuario de la base de datos del proyecto*/

/* Nota: si en la API, en el application.properties esta el
spring.jpa.hibernate.ddl-auto=update , si es igual a update, nos generara
de manera automatica las tablas de la base de datos */

/* base de datos utilizada en el proyecto Angular-Java redsocial-angular-app*/

CREATE SCHEMA `bd_red_social_3` ; /*Crear base de datos del proyecto de la red social*/


create table usuario(
id_usu int auto_increment,
nombre_usu varchar(60) not null unique key,
fecha_alta datetime not null,

pais varchar(100),
ciudad varchar(100),
region varchar(100),

email varchar(100),

nombre varchar(100),
apellidos varchar(200),
fecha_nacimiento date not null,
telefono varchar(20),

email_entrada varchar(100) not null unique key,
contrasenya varchar(100) not null,

perfil_privado bool default false,
aceptada_pol_priva bool default false,
es_administrador bool default false,
usuario_activo bool default true,
usuario_baneado bool default false,
fecha_fin_baneo datetime,

estado varchar(200),
num_visitas bigint default 0,
fecha_ultimo_login datetime,
foto_perfil varchar(100),
foto_portada varchar(100),

primary key(id_usu)
); 


/*Tabla amigos usuarios*/
create table amigos_usu(
id_solicitante int,
id_receptor int,
fecha_enviada datetime not null,
solicitud_aceptada bool default null,
primary key(id_solicitante, id_receptor),
foreign key(id_solicitante) references usuario(id_usu),
foreign key(id_receptor) references usuario(id_usu)
);

/*Tabla fotos*/
create table fotos(
id_foto int auto_increment,
id_usu int not null,
foto_string varchar(100) not null,
fecha_subida_fot datetime not null,
primary key (id_foto),
foreign key(id_usu) references usuario(id_usu)
);

/*Tabla videos*/
create table videos(
id_video int auto_increment,
id_usu int,
video_string varchar(100) not null,
fecha_subida_vid datetime not null,
primary key (id_video),
foreign key(id_usu) references usuario(id_usu)
);

/*Tabla entradas*/
create table entradas(
id_entrada int auto_increment,
id_usu int,
titulo_entrada varchar(200) not null,
texto_entrada varchar(500),
fecha_creacion_ent datetime not null,
primary key(id_entrada),
foreign key(id_usu) references usuario(id_usu)
);

/*Tabla publicaciones*/
create table publicacion(
id_publicacion int auto_increment,
id_usu int,
titulo_publicacion varchar(200) not null,
texto_publicacion varchar(500),
fecha_creacion_pub datetime not null,
pub_es_privada bool default false,
primary key(id_publicacion),
foreign key(id_usu) references usuario(id_usu)
);

/*Tabla comentarios*/
create table comentario(
id_comentario int auto_increment,
id_usuario int not null,
id_publicacion int not null,
texto_comentario varchar(500) not null,
fecha_creacion_com datetime not null,
primary key(id_comentario),
foreign key(id_usuario) references usuario(id_usu),
foreign key(id_publicacion) references publicacion(id_publicacion)
);

/* modificacion de blob para las tablas fotos y videos*/
/*tablas fotos y videos*/
alter table fotos add column foto_blob blob;
alter table videos add column video_blob blob;

/* Inserts  de prueba ----------------------------------------------------- */

/* usuario admin de prueba*/
INSERT INTO `bd_red_social_3`.`usuario` (`id_usu`, `nombre_usu`, `fecha_alta`, `email`, `fecha_nacimiento`, `email_entrada`, `contrasenya`, `es_administrador`) VALUES ('1', 'admin', '2020-04-30 17:26:00', 'soporteADM@gmail.com', '1994-04-30', 'soporteADM@gmail.com', '123456789', '0');

UPDATE `bd_red_social_3`.`usuario` SET `es_administrador`='1' WHERE `id_usu`='1';

INSERT INTO `bd_red_social_3`.`entradas` (`id_entrada`, `id_usu`, `titulo_entrada`, `texto_entrada`, `fecha_creacion_ent`) VALUES ('1', '1', 'Entrada de prueba 1', 'Entrada 1', '2020-04-30 17:26:00');

INSERT INTO `bd_red_social_3`.`publicacion` (`id_publicacion`, `id_usu`, `titulo_publicacion`, `texto_publicacion`, `fecha_creacion_pub`, `pub_es_privada`) VALUES ('1', '1', 'Publicacion 1', 'Publicacion de prueba', '2020-04-30 17:26:00', '0');

INSERT INTO `bd_red_social_3`.`comentario` (`id_comentario`, `id_usuario`, `id_publicacion`, `texto_comentario`, `fecha_creacion_com`) VALUES ('1', '1', '1', 'Esto es un comentario', '2020-04-30 17:26:00');


/*Ampliacion usuario admin de prueba*/
UPDATE `bd_red_social_3`.`usuario` SET `pais`='España', `ciudad`='Valencia', `region`='CValenciana', `nombre`='adm', `apellidos`='admin', `telefono`='9663015', `estado`='estadoAdmin' WHERE `id_usu`='1';

Update `usuario` SET `fecha_ultimo_login`='2020-04-30 17:26:00' WHERE `id_usu`='1';


/* usuarios de prueba para las selects*/

INSERT INTO `bd_red_social_3`.`usuario` (`id_usu`, `nombre_usu`, `fecha_alta`, `email`, `fecha_nacimiento`, `email_entrada`, `contrasenya`, `es_administrador`) VALUES ('2', 'admin', '2020-04-30 17:26:00', 'soporteADM@gmail.com', '1994-04-30', 'soporteADM@gmail.com', '123456789', '0');

INSERT INTO `bd_red_social_3`.`usuario` (`id_usu`, `nombre_usu`, `fecha_alta`, `email`, `fecha_nacimiento`, `email_entrada`, `contrasenya`, `es_administrador`) VALUES ('3', 'admin', '2020-04-30 17:26:00', 'soporteADM@gmail.com', '1994-04-30', 'soporteADM@gmail.com', '123456789', '0');


/* Datos para crear varios usuarios */
INSERT INTO `usuario` (`id_usu`, `nombre_usu`, `fecha_alta`, `email`, `fecha_nacimiento`, `email_entrada`, `contrasenya`, `es_administrador`) VALUES ('1', 'admin', '2020-04-30 17:26:00', 'soporteADM@gmail.com', '1994-04-30', 'soporteADM@gmail.com', '123456789', '0');

UPDATE `usuario` SET `es_administrador`='1' WHERE `id_usu`='1';

INSERT INTO `entradas` (`id_entrada`, `id_usu`, `titulo_entrada`, `texto_entrada`, `fecha_creacion_ent`) VALUES ('1', '1', 'Entrada de prueba 1', 'Entrada 1', '2020-04-30 17:26:00');

INSERT INTO `publicacion` (`id_publicacion`, `id_usu`, `titulo_publicacion`, `texto_publicacion`, `fecha_creacion_pub`, `pub_es_privada`) VALUES ('1', '1', 'Publicacion 1', 'Publicacion de prueba', '2020-04-30 17:26:00', '0');

INSERT INTO `comentario` (`id_comentario`, `id_usuario`, `id_publicacion`, `texto_comentario`, `fecha_creacion_com`) VALUES ('1', '1', '1', 'Esto es un comentario', '2020-04-30 17:26:00');

INSERT INTO `usuario` (`id_usu`, `nombre_usu`, `fecha_alta`, `email`, `fecha_nacimiento`, `email_entrada`, `contrasenya`, `es_administrador`) VALUES ('2', 'admin2', '2020-04-30 17:26:00', 'soporteADM2@gmail.com', '1994-04-30', 'soporteADM2@gmail.com', '123456789', '0');

INSERT INTO `usuario` (`id_usu`, `nombre_usu`, `fecha_alta`, `email`, `fecha_nacimiento`, `email_entrada`, `contrasenya`, `es_administrador`) VALUES ('3', 'admin3', '2020-04-30 17:26:00', 'soporteADM3@gmail.com', '1994-04-30', 'soporteADM3@gmail.com', '123456789', '0');

UPDATE `usuario` SET `pais`='España', `ciudad`='Valencia', `region`='CValenciana', `nombre`='adm', `apellidos`='admin', `telefono`='9663015', `estado`='estadoAdmin' WHERE `id_usu`='1';

Update `usuario` SET `fecha_ultimo_login`='2020-04-30 17:26:00' WHERE `id_usu`='1';


/*Actualizacion usuario 1*/

UPDATE `bd_red_social_3`.`usuario` SET `pais`='España', `ciudad`='Valencia', `region`='CValenciana', `nombre`='adm', `apellidos`='admin', `telefono`='9663015', `estado`='estadoAdmin' WHERE `id_usu`='1';

Update `usuario` SET `fecha_ultimo_login`='2020-04-30 17:26:00' WHERE `id_usu`='1';

/* datos de prueba para comprobar (Nota: crear mas usuarios antes)*/

/* amigos usu (amistades entre usuarios)*/
INSERT INTO `bd_red_social_3`.`amigos_usu` (`id_solicitante`, `id_receptor`, `fecha_enviada`) VALUES ('1', '5', '2020-04-30 17:26:00');
INSERT INTO `bd_red_social_3`.`amigos_usu` (`id_solicitante`, `id_receptor`, `fecha_enviada`, `solicitud_aceptada`) VALUES ('1', '4', '2020-04-30 17:26:00', '1');
INSERT INTO `bd_red_social_3`.`amigos_usu` (`id_solicitante`, `id_receptor`, `fecha_enviada`, `solicitud_aceptada`) VALUES ('2', '4', '2020-04-30 17:26:00', '1');
INSERT INTO `bd_red_social_3`.`amigos_usu` (`id_solicitante`, `id_receptor`, `fecha_enviada`) VALUES ('2', '5', '2020-04-30 17:26:00');
INSERT INTO `bd_red_social_3`.`amigos_usu` (`id_solicitante`, `id_receptor`, `fecha_enviada`, `solicitud_aceptada`) VALUES ('6', '1', '2020-04-30 17:26:00', '1');
INSERT INTO `bd_red_social_3`.`amigos_usu` (`id_solicitante`, `id_receptor`, `fecha_enviada`, `solicitud_aceptada`) VALUES ('4', '1', '2020-04-30 17:26:00', '0');

UPDATE `bd_red_social_3`.`amigos_usu` SET `solicitud_aceptada`='0' WHERE `id_solicitante`='1' and`id_receptor`='5';
UPDATE `bd_red_social_3`.`amigos_usu` SET `solicitud_aceptada`='0' WHERE `id_solicitante`='2' and`id_receptor`='5';

/*entradas*/
INSERT INTO `bd_red_social_3`.`entradas` (`id_entrada`, `id_usu`, `titulo_entrada`, `texto_entrada`, `fecha_creacion_ent`) VALUES ('2', '4', 'Entrada usu 4', 'entrada 4', '2020-04-30 17:26:00');
INSERT INTO `bd_red_social_3`.`entradas` (`id_entrada`, `id_usu`, `titulo_entrada`, `texto_entrada`, `fecha_creacion_ent`) VALUES ('3', '1', 'Entrada de prueba 2', 'entr 2', '2020-04-30 17:26:00');

/*publicaciones*/
INSERT INTO `bd_red_social_3`.`publicacion` (`id_publicacion`, `id_usu`, `titulo_publicacion`, `texto_publicacion`, `fecha_creacion_pub`, `pub_es_privada`) VALUES ('2', '4', 'Publicacion del 4 priv', 'Publicacion del 4 priv', '2020-04-30 17:26:00', '1');
INSERT INTO `bd_red_social_3`.`publicacion` (`id_publicacion`, `id_usu`, `titulo_publicacion`, `texto_publicacion`, `fecha_creacion_pub`, `pub_es_privada`) VALUES ('3', '4', 'Publicacion del 4 pub', 'Publicacion del 4 pub', '2020-04-30 17:26:00', '0');
INSERT INTO `bd_red_social_3`.`publicacion` (`id_publicacion`, `id_usu`, `titulo_publicacion`, `texto_publicacion`, `fecha_creacion_pub`, `pub_es_privada`) VALUES ('4', '5', 'Publicacion del 5 pub', 'Publicacion del 5 pub', '2020-04-30 17:26:00', '0');
INSERT INTO `bd_red_social_3`.`publicacion` (`id_publicacion`, `id_usu`, `titulo_publicacion`, `texto_publicacion`, `fecha_creacion_pub`, `pub_es_privada`) VALUES ('5', '4', 'Publicacion del 4 priv com', 'Publicacion del 4 priv com', '2020-04-30 17:26:00', '1');
INSERT INTO `bd_red_social_3`.`publicacion` (`id_publicacion`, `id_usu`, `titulo_publicacion`, `texto_publicacion`, `fecha_creacion_pub`, `pub_es_privada`) VALUES ('6', '4', 'Publicacion del 4 pub com', 'Publicacion del 4 pub com', '2020-04-30 17:26:00', '0');

/*comentarios*/
INSERT INTO `bd_red_social_3`.`comentario` (`id_comentario`, `id_usuario`, `id_publicacion`, `texto_comentario`, `fecha_creacion_com`) VALUES ('2', '1', '5', 'comm pub privada', '2020-04-30 17:26:00');
INSERT INTO `bd_red_social_3`.`comentario` (`id_comentario`, `id_usuario`, `id_publicacion`, `texto_comentario`, `fecha_creacion_com`) VALUES ('3', '1', '6', 'comm pub publ', '2020-04-30 17:26:00');
INSERT INTO `bd_red_social_3`.`comentario` (`id_comentario`, `id_usuario`, `id_publicacion`, `texto_comentario`, `fecha_creacion_com`) VALUES ('4', '1', '5', 'comm pub privada 2', '2020-04-30 17:26:00');



/* Consulas SQL del proyecto ----------------------------------------------- */

/* --consultas BD red social-- */
/* estas consultas sirven para realizar distintos tipos de busquedas que realizara el programa*/

/* buscar todas las entradas de un usuario */
select e.id_usu, e.id_entrada, e.titulo_entrada, e.texto_entrada, e.fecha_creacion_ent
from entradas e, usuario u 
where u.id_usu = e.id_usu
and u.id_usu = 1;


/* buscar amigos del usuario */
select am.id_solicitante, am.id_receptor, am.solicitud_aceptada,
u.id_usu 
from amigos_usu am, usuario u 
where u.id_usu = am.id_solicitante
and am.solicitud_aceptada = 1 and am.id_solicitante = 1;


select am.id_solicitante, am.id_receptor, am.solicitud_aceptada,
u.id_usu 
from amigos_usu am, usuario u 
where u.id_usu = am.id_receptor
and am.solicitud_aceptada = 1 and am.id_receptor = 1;

/*En el programa se usara ids para buscar y luego se obtendra los objetos usuario*/
select am.id_solicitante, am.id_receptor, am.solicitud_aceptada from amigos_usu am 
where (am.id_receptor = 1 or am.id_solicitante = 1) and am.solicitud_aceptada = 1;

/* buscar nuevos amigos, sin incluir los que ya estan en peticion de amistad*/
/*manera optima*/
select u.id_usu from usuario u where u.id_usu 
not in (select amigos_usu.id_receptor from amigos_usu where amigos_usu.id_receptor = 1 or amigos_usu.id_solicitante = 1)
and u.id_usu not in (select am.id_solicitante from amigos_usu am where am.id_receptor = 1 or am.id_solicitante = 1)
order by u.fecha_alta DESC;


/*por programa revisar cuales son aceptadas y cuales no*/


/* Nota: en el programa hay poner la opcion de rechazar o aceptar todas
ademas de la manera individual */

/* peticiones, si son rechazadas son null  y se borran
   si las peticiones son false se mantienen*/

/* buscar peticiones de amistad en general (sin identificar enviadas recibidas)*/
select am.id_solicitante, am.id_receptor, am.solicitud_aceptada from amigos_usu am 
where (am.id_receptor = 1 or am.id_solicitante = 1) and am.solicitud_aceptada = 0;


/* buscar peticiones de amistad realizadas por el usuario*/
select am.id_solicitante, am.id_receptor, am.solicitud_aceptada,
u.id_usu 
from amigos_usu am, usuario u 
where u.id_usu = am.id_solicitante
and am.id_solicitante = 1 
and am.solicitud_aceptada = 0;


/* buscar peticiones recibidas por el usuario*/
select am.id_solicitante, am.id_receptor, am.solicitud_aceptada,
u.id_usu 
from amigos_usu am, usuario u 
where u.id_usu = am.id_receptor
and am.id_receptor = 1
and am.solicitud_aceptada = 0;


/* buscar fotos del usuario limite de 9 ultimas */
select * from usuario u, fotos f 
where u.id_usu = f.id_usu and u.id_usu = 1 
order by f.fecha_subida_fot desc limit 9;


/* buscar fotos de un usuario*/
select * from usuario u, fotos f 
where u.id_usu = f.id_usu and u.id_usu = 1 
order by f.fecha_subida_fot desc;

/* buscar videos del usuario limite de 9 ultimas*/
select * from usuario u, videos v 
where u.id_usu = v.id_usu and u.id_usu = 1 
order by v.fecha_subida_vid desc limit 9;

/* buscar videos de un usuario*/
select * from usuario u, videos v 
where u.id_usu = v.id_usu and u.id_usu = 1 
order by v.fecha_subida_vid desc;

/*obtener las publicaciones de un usuario (para el perfil)*/
select publicacion.titulo_publicacion, publicacion.texto_publicacion, publicacion.id_usu
from publicacion where publicacion.id_usu = 4 order by publicacion.fecha_creacion_pub desc;

/*obtener publicaciones para el inicio (publicas, todos usuarios)*/
select publicacion.titulo_publicacion, publicacion.texto_publicacion, 
publicacion.id_usu, publicacion.pub_es_privada, publicacion.fecha_creacion_pub
from publicacion where publicacion.pub_es_privada = 0 
order by publicacion.fecha_creacion_pub desc;

/* buscar todas las publicaciones ordenadas por 
fecha que sean publicas con un limite de 10 ultimas */
select publicacion.titulo_publicacion, publicacion.texto_publicacion, 
publicacion.id_usu, publicacion.pub_es_privada, publicacion.fecha_creacion_pub
from publicacion where publicacion.pub_es_privada = 0 
order by publicacion.fecha_creacion_pub desc limit 10;


/* buscar publicaciones de los amigos del usuario */
select publicacion.titulo_publicacion, publicacion.texto_publicacion, 
publicacion.id_usu, publicacion.pub_es_privada, publicacion.fecha_creacion_pub
from publicacion where publicacion.id_usu = 1 and publicacion.id_usu 
in(select am.id_solicitante from amigos_usu am where am.solicitud_aceptada = 1)
or 
publicacion.id_usu in(select a.id_receptor from amigos_usu a where a.solicitud_aceptada = 1)
order by publicacion.fecha_creacion_pub desc;

/*
select publicacion.titulo_publicacion, publicacion.texto_publicacion, publicacion.id_usu
from publicacion where publicacion.id_usu = 4;

select publicacion.titulo_publicacion, publicacion.texto_publicacion, publicacion.id_usu
from publicacion where publicacion.id_usu in(select u.id_usu from usuario u, amigos_usu am 
where u.id_usu = am.id_receptor or u.id_usu = am.id_solicitante 
and am.solicitud_aceptada = 1);

select * from usuario u, amigos_usu am 
where u.id_usu = am.id_receptor or u.id_usu = am.id_solicitante 
and am.solicitud_aceptada = 1;
*/

/* buscar comentarios de una publicacion */
select * from comentario where comentario.id_publicacion = 5;

/*Seleccionar el ultimo id usuario (seleccionar el maximo)*/
SELECT max(id_usu) FROM bd_red_social_3.usuario;