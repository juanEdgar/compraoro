


CREATE TABLE DiamanteColor
(
	fiIdDiamanteColor  INT  NOT NULL ,
	fcColor		  VARCHAR(50)  NOT NULL ,
 PRIMARY KEY (fiIdDiamanteColor)
);



CREATE TABLE DiamantePunto
(
	fiIddiamantePunto  INT  NOT NULL ,
	fcRangoPuntos	  VARCHAR(50)  NOT NULL ,
 PRIMARY KEY (fiIddiamantePunto)
);



CREATE TABLE DiamanteLimpieza
(
	fiIdDiamanteLimpieza  INT  NOT NULL ,
	fcLimpieza	  VARCHAR(50)  NOT NULL ,
 PRIMARY KEY (fiIdDiamanteLimpieza)
);



CREATE TABLE Diamante
(
	fiIdTipoDiamante  INT  NOT NULL ,
	fiIdDiamanteColor  INT  NOT NULL ,
	fiIddiamantePunto  INT  NOT NULL ,
	fiIdDiamanteLimpieza  INT  NOT NULL ,
	fiIdEstatus	  INT  NULL ,
 PRIMARY KEY (fiIdTipoDiamante),
 FOREIGN KEY (fiIdDiamanteColor) REFERENCES DiamanteColor(fiIdDiamanteColor),
 FOREIGN KEY (fiIddiamantePunto) REFERENCES DiamantePunto(fiIddiamantePunto),
 FOREIGN KEY (fiIdDiamanteLimpieza) REFERENCES DiamanteLimpieza(fiIdDiamanteLimpieza),
 FOREIGN KEY (fiIdEstatus) REFERENCES CatalogoEstatus(fiIdEstatus)
);



CREATE INDEX XIE1Diamante ON Diamante
(fiIdDiamanteColor  ASC,fiIddiamantePunto  ASC,fiIdDiamanteLimpieza  ASC,fiIdEstatus  ASC);



CREATE INDEX XIE2Diamante ON Diamante
(fiIdEstatus  ASC);



CREATE TABLE PrecioDiamanteQuilate
(
	fiIdPrecioDiamante  INT  NOT NULL ,
	fiIdTipoDiamante  INT  NOT NULL ,
	fnPrecio	  DECIMAL(12,2)  NOT NULL ,
	fiidMoneda	  INT  NOT NULL ,
	fiIdEstatus	  INT  NOT NULL ,
	fcUsuarioModifico  VARCHAR(20)  NOT NULL ,
	fdFechaModificacion  TIMESTAMP  NOT NULL ,
 PRIMARY KEY (fiIdPrecioDiamante),
 FOREIGN KEY (fiIdEstatus) REFERENCES CatalogoEstatus(fiIdEstatus),
 FOREIGN KEY (fiidMoneda) REFERENCES Moneda(fiidMoneda),
 FOREIGN KEY (fiIdTipoDiamante) REFERENCES Diamante(fiIdTipoDiamante)
);



CREATE INDEX XIE1PrecioDiamanteQuilate ON PrecioDiamanteQuilate
(fiIdTipoDiamante  ASC);



CREATE INDEX XIE2PrecioDiamanteQuilate ON PrecioDiamanteQuilate
(fiIdEstatus  ASC);



CREATE TABLE ArticuloCompraDiamante
(
	fiIdArticuloCompra  INT  NOT NULL ,
	fiIdPrecioDiamante  INT  NOT NULL ,
	fnQuilates	  DECIMAL(12,2)  NOT NULL ,
 PRIMARY KEY (fiIdArticuloCompra),
 FOREIGN KEY (fiIdArticuloCompra) REFERENCES ArticuloCompra(fiIdArticuloCompra),
 FOREIGN KEY (fiIdPrecioDiamante) REFERENCES PrecioDiamanteQuilate(fiIdPrecioDiamante)
);


