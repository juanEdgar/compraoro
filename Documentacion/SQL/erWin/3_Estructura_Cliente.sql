
CREATE TABLE Persona
(
	fiIdPersona	  INT  NOT NULL ,
	fcNombre	  VARCHAR(50)  NOT NULL ,
	fcSegundoNombre	  VARCHAR(50)  NULL ,
	fcApellidoPaterno  VARCHAR(50)  NOT NULL ,
	fcApellidoMaterno  VARCHAR(50)  NULL ,
	fdFechaNacimiento  DATE  NOT NULL ,
	fcRFC		  VARCHAR(12)  NULL ,
	fiIdEstatus	  INT  NOT NULL ,
	fiIdEstatusTipoPersona  INT  NOT NULL ,
	fcUsuarioModifico  VARCHAR(20)  NOT NULL ,
	fdFechaModificacion  TIMESTAMP  NOT NULL ,
 PRIMARY KEY (fiIdPersona),
 FOREIGN KEY (fiIdEstatus) REFERENCES CatalogoEstatus(fiIdEstatus),
 FOREIGN KEY (fiIdEstatusTipoPersona) REFERENCES CatalogoEstatus(fiIdEstatus)
);



CREATE INDEX XIE1Persona ON Persona
(fdFechaNacimiento  ASC,fcApellidoPaterno  ASC,fcApellidoMaterno  ASC,fcNombre  ASC,fcSegundoNombre  ASC,fiIdEstatusTipoPersona  ASC);



CREATE INDEX XIE2Persona ON Persona
(fcRFC  ASC,fiIdEstatusTipoPersona  ASC);





CREATE TABLE Cliente
(
	fiIdPersona	  INT  NOT NULL ,
	fiIdTienda	  INT  NOT NULL ,
	fiIdEstatus	  INT  NULL ,
	fdFechaModificacion  TIMESTAMP  NOT NULL ,
	fcUsuarioModifico  VARCHAR(20)  NOT NULL ,
 PRIMARY KEY (fiIdPersona),
 FOREIGN KEY (fiIdTienda) REFERENCES Tienda(fiIdTienda),
 FOREIGN KEY (fiIdEstatus) REFERENCES CatalogoEstatus(fiIdEstatus),
 FOREIGN KEY (fiIdPersona) REFERENCES Persona(fiIdPersona)
);



CREATE UNIQUE INDEX XAK1Cliente ON Cliente
(fiIdPersona  ASC,fiIdTienda  ASC);



CREATE INDEX XIE1Cliente ON Cliente
(fiIdTienda  ASC);


