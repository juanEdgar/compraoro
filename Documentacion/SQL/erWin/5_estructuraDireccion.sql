
CREATE TABLE CatalogoEstado
(
	fiIdEstado	  INT  NOT NULL ,
	fcNombre	  VARCHAR(200)  NULL ,
 PRIMARY KEY (fiIdEstado)
);



CREATE TABLE CatalogoMunicipio
(
	fiIdMunicipio	  INT  NOT NULL ,
	fiIdEstado	  INT  NOT NULL ,
	fcNombre	  VARCHAR(250)  NULL ,
 PRIMARY KEY (fiIdMunicipio,fiIdEstado),
 FOREIGN KEY (fiIdEstado) REFERENCES CatalogoEstado(fiIdEstado)
);



CREATE INDEX IN001CatalogoMunicipio ON CatalogoMunicipio
(fiIdEstado  ASC);



CREATE TABLE CatalogoCodigoPostal
(
	fiIdCodigoPostal  INT  NOT NULL ,
	fiIdMunicipio	  INT  NOT NULL ,
	fiIdEstado	  INT  NOT NULL ,
	fcCodigoPostal	  CHAR(6)  NULL ,
 PRIMARY KEY (fiIdCodigoPostal,fiIdMunicipio,fiIdEstado),
 FOREIGN KEY (fiIdMunicipio,fiIdEstado) REFERENCES CatalogoMunicipio(fiIdMunicipio,fiIdEstado)
);



CREATE INDEX IN0021CatalogoCodigoPostal ON CatalogoCodigoPostal
(fcCodigoPostal  ASC);



CREATE INDEX IN0022CatalogoCodigoPostal ON CatalogoCodigoPostal
(fiIdEstado  ASC,fiIdMunicipio  ASC);




CREATE TABLE CatalogoColonia
(
	fiIdColonia	  INT  NOT NULL ,
	fiIdCodigoPostal  INT  NOT NULL ,
	fiIdMunicipio	  INT  NOT NULL ,
	fiIdEstado	  INT  NOT NULL ,
	fiIdEstatus	  INT  NOT NULL ,
	fcNombre	  VARCHAR(250)  NULL ,
 PRIMARY KEY (fiIdColonia,fiIdCodigoPostal,fiIdMunicipio,fiIdEstado),
 FOREIGN KEY (fiIdCodigoPostal,fiIdMunicipio,fiIdEstado) REFERENCES CatalogoCodigoPostal(fiIdCodigoPostal,fiIdMunicipio,fiIdEstado),
 FOREIGN KEY (fiIdEstatus) REFERENCES CatalogoEstatus(fiIdEstatus)
);



CREATE INDEX IN001CatalogoColonia ON CatalogoColonia
(fiIdCodigoPostal  ASC,fiIdMunicipio  ASC,fiIdEstado  ASC);



CREATE TABLE CatalogoCalle
(
	fiIdColonia	  INT  NOT NULL ,
	fiIdCodigoPostal  INT  NOT NULL ,
	fiIdMunicipio	  INT  NOT NULL ,
	fiIdEstado	  INT  NOT NULL ,
	fcCalle		  VARCHAR(300)  NOT NULL ,
	fiIdEstatus	  INT  NULL ,
 PRIMARY KEY (fiIdColonia,fiIdCodigoPostal,fiIdMunicipio,fiIdEstado,fcCalle),
 FOREIGN KEY (fiIdColonia,fiIdCodigoPostal,fiIdMunicipio,fiIdEstado) REFERENCES CatalogoColonia(fiIdColonia,fiIdCodigoPostal,fiIdMunicipio,fiIdEstado),
 FOREIGN KEY (fiIdEstatus) REFERENCES CatalogoEstatus(fiIdEstatus)
);



CREATE INDEX IN001CatalogoCalle ON CatalogoCalle
(fiIdEstado  ASC,fiIdMunicipio  ASC,fiIdCodigoPostal  ASC,fiIdColonia  ASC);



CREATE TABLE Direccion
(
	fiIdDireccion	  INT  NOT NULL ,
	fcNumeroInterior  INT  NULL ,
	fcNumeroExperior  INT  NOT NULL ,
	fcUsuarioModifico  VARCHAR(20)  NULL ,
	fdFechaModificacion  TIMESTAMP  NULL ,
	fiIdColonia	  INT  NULL ,
	fiIdCodigoPostal  INT  NULL ,
	fiIdMunicipio	  INT  NULL ,
	fiIdEstado	  INT  NULL ,
	fcCalle		  VARCHAR(300)  NULL ,
 PRIMARY KEY (fiIdDireccion),
 FOREIGN KEY (fiIdColonia,fiIdCodigoPostal,fiIdMunicipio,fiIdEstado,fcCalle) REFERENCES CatalogoCalle(fiIdColonia,fiIdCodigoPostal,fiIdMunicipio,fiIdEstado,fcCalle)
);


