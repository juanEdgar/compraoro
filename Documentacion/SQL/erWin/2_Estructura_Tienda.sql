

CREATE TABLE Tienda
(
	fiIdTienda	  INT  NOT NULL ,
	fiNumero	  INT  NULL ,
	fcNombre	  VARCHAR(250)  NULL ,
	fiIdEstatus	  INT  NULL ,	
	fcUsuarioModifico  VARCHAR(20)  NOT NULL ,
	fdFechaModificacion  TIMESTAMP  NOT NULL ,
 PRIMARY KEY (fiIdTienda),
 FOREIGN KEY (fiIdEstatus) REFERENCES CatalogoEstatus(fiIdEstatus)

);


CREATE INDEX XIE2Tienda ON Tienda
(fiNumero  ASC);




CREATE TABLE TiendaCajaEfectivo
(
	fiIdTienda	  INT  NOT NULL ,
	fiIdCaja	  INT  NOT NULL ,
	fiIdEstatus	  INT  NOT NULL ,
 PRIMARY KEY (fiIdTienda,fiIdCaja),
 FOREIGN KEY (fiIdTienda) REFERENCES Tienda(fiIdTienda),
 FOREIGN KEY (fiIdEstatus) REFERENCES CatalogoEstatus(fiIdEstatus)
);



CREATE INDEX XIE1TiendaCajaEfectivo ON TiendaCajaEfectivo
(fiIdTienda  ASC,fiIdEstatus  ASC);




CREATE TABLE TiendaCajaEfectivoMovimiento
(
	fiIdCajaEfectivoMovimiento  INT  NOT NULL ,
	fiIdTienda	  INT  NULL ,
	fiIdCaja	  INT  NULL ,
	fnDenominacion	  DECIMAL(12,2)  NOT NULL ,
	fiCantidad	  INT  NOT NULL ,
	fiIdEstatus	  INT  NOT NULL ,
	fcUsuarioModifico  VARCHAR(20)  NOT NULL ,
	fdFechaModificacion  TIMESTAMP  NOT NULL ,
 PRIMARY KEY (fiIdCajaEfectivoMovimiento),
 FOREIGN KEY (fiIdTienda,fiIdCaja) REFERENCES TiendaCajaEfectivo(fiIdTienda,fiIdCaja),
 FOREIGN KEY (fiIdEstatus) REFERENCES CatalogoEstatus(fiIdEstatus)
);



CREATE INDEX XIE1TiendaCajaEfectivoMovimien ON TiendaCajaEfectivoMovimiento
(fiIdTienda  ASC,fdFechaModificacion  ASC);







CREATE TABLE TiendaCajaEfectivoSaldo
(
	fiIdTienda	  INT  NOT NULL ,
	fiIdCaja	  INT  NOT NULL ,
	fnDenominacion	  DECIMAL(12,2)  NOT NULL ,
	fiIdEstatus	  INT  NOT NULL ,
	fiCantidad	  INT  NOT NULL ,
 PRIMARY KEY (fiIdTienda,fiIdCaja,fnDenominacion),
 FOREIGN KEY (fiIdTienda,fiIdCaja) REFERENCES TiendaCajaEfectivo(fiIdTienda,fiIdCaja),
 FOREIGN KEY (fiIdEstatus) REFERENCES CatalogoEstatus(fiIdEstatus)
);



CREATE INDEX XIE1TiendaCajaEfectivoSaldo ON TiendaCajaEfectivoSaldo
(fnDenominacion  ASC,fiIdEstatus  ASC,fiIdCaja  ASC,fiIdTienda  ASC);



CREATE INDEX XIE2TiendaCajaEfectivoSaldo ON TiendaCajaEfectivoSaldo
(fiIdCaja  ASC,fiIdTienda  ASC,fiIdEstatus  ASC);
