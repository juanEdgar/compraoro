
CREATE TABLE CatalogoGrupoEstatus
(
	fiIdGrupoEstatus  INT  NOT NULL ,
	fcDescripcion	  VARCHAR(100)  NOT NULL ,
	fcUsuarioModifico  VARCHAR(20)  NOT NULL ,
	fdFechaModificacion  TIMESTAMP  NOT NULL ,
 PRIMARY KEY (fiIdGrupoEstatus)
);



CREATE TABLE CatalogoEstatus
(
	fiIdEstatus	  INT  NOT NULL ,
	fiIdGrupoEstatus  INT  NOT NULL ,
	fcDescripcion	  VARCHAR(100)  NOT NULL ,
	fcUsuarioModifico  VARCHAR(20)  NOT NULL ,
	fdFechaModificacion  TIMESTAMP  NOT NULL ,
 PRIMARY KEY (fiIdEstatus),
 FOREIGN KEY (fiIdGrupoEstatus) REFERENCES CatalogoGrupoEstatus(fiIdGrupoEstatus)
);



CREATE TABLE Metal
(
	fiIdMetal	  INT  NOT NULL ,
	fcNombre	  VARCHAR(150)  NOT NULL ,
	fnPurezaBase	  DECIMAL(12,4)  NOT NULL ,
	fiIdEstatus	  INT  NOT NULL ,
 PRIMARY KEY (fiIdMetal),
 FOREIGN KEY (fiIdEstatus) REFERENCES CatalogoEstatus(fiIdEstatus)
);



CREATE TABLE MetalPureza
(
	fiIdTipoPureza	  INT  NOT NULL ,
	fiIdMetal	  INT  NOT NULL ,
	fcNombre	  VARCHAR(150)  NOT NULL ,
	fiIdEstatus	  INT  NOT NULL ,
	fnProporcionPureza  DECIMAL(12,4)  NOT NULL ,
 PRIMARY KEY (fiIdTipoPureza,fiIdMetal),
 FOREIGN KEY (fiIdEstatus) REFERENCES CatalogoEstatus(fiIdEstatus),
 FOREIGN KEY (fiIdMetal) REFERENCES Metal(fiIdMetal)
);



CREATE TABLE Moneda
(
	fiidMoneda	  INT  NOT NULL ,
	fcUnidad	  VARCHAR(3)  NOT NULL ,
	fcNombre	  VARCHAR(50)  NOT NULL ,
	fcSimbolo	  VARCHAR(5)  NOT NULL ,
 PRIMARY KEY (fiidMoneda)
);



CREATE TABLE TipoCambio
(
	fiIdTipoDeCambio  INT  NOT NULL ,
	fiIdMonedaCambio  INT  NOT NULL ,
	fiidMoneda	  INT  NOT NULL ,
	fdTipoDeCambio	  DECIMAL(12,4)  NOT NULL ,
	fiIdEstatus	  INT  NOT NULL ,
	fcUsuarioModifico  VARCHAR(20)  NOT NULL ,
	fdFechaModificacion  TIMESTAMP  NOT NULL ,
 PRIMARY KEY (fiIdTipoDeCambio),
 FOREIGN KEY (fiIdMonedaCambio) REFERENCES Moneda(fiidMoneda),
 FOREIGN KEY (fiIdEstatus) REFERENCES CatalogoEstatus(fiIdEstatus),
 FOREIGN KEY (fiidMoneda) REFERENCES Moneda(fiidMoneda)
);



CREATE INDEX XIE1TipoCambioEstatus ON TipoCambio
(fiidMoneda  ASC,fiIdMonedaCambio  ASC,fiIdEstatus  ASC);



CREATE TABLE PrecioMetalGramoFino
(
	fiIdPrecioMetalGramoFino  INT  NOT NULL ,
	fiIdMetal	  INT  NOT NULL ,
	fdPrecio	  DECIMAL(12,2)  NOT NULL ,
	fdAforo	  	  DECIMAL(5,2)  NOT NULL ,
	fiidMoneda	  INT  NOT NULL ,
	fiIdEstatus	  INT  NOT NULL ,
	fcUsuarioModifico  VARCHAR(20)  NOT NULL ,
	fdFechaModificacion  TIMESTAMP  NOT NULL ,
 PRIMARY KEY (fiIdPrecioMetalGramoFino),
 FOREIGN KEY (fiidMoneda) REFERENCES Moneda(fiidMoneda),
 FOREIGN KEY (fiIdMetal) REFERENCES Metal(fiIdMetal),
 FOREIGN KEY (fiIdEstatus) REFERENCES CatalogoEstatus(fiIdEstatus)
);



CREATE INDEX XIE1PrecioMetalGramoFino ON PrecioMetalGramoFino
(fiIdMetal  ASC,fiIdEstatus  ASC);


