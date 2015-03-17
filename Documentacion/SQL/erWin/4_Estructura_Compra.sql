



CREATE  SEQUENCE seq_pagoCompa INCREMENT  BY 20
     MINVALUE 1  
     START  WITH 1;

CREATE  SEQUENCE seq_compra INCREMENT  BY 50
     MINVALUE 1  
     START  WITH 1;

     
 CREATE  SEQUENCE seq_bitacoraCompra INCREMENT  BY 50
     MINVALUE 1  
     START  WITH 1;    
 
 
CREATE  SEQUENCE seq_articuloCompra INCREMENT  BY 50
     MINVALUE 1  
     START  WITH 1;

     
     CREATE  SEQUENCE seq_seguribolsa INCREMENT  BY 10
     MINVALUE 1  
     START  WITH 1;

  

CREATE TABLE Compra
(
	fiIdCompra	  INT  NOT NULL ,
	fiIdTienda	  INT  NOT NULL ,
	fiIdPersona	  INT  NOT NULL ,
	fiIdEstatus	  INT  NOT NULL ,
	fcUsuarioModifico  VARCHAR(20)  NOT NULL ,
	fdFechaModificacion  TIMESTAMP  NOT NULL ,
 PRIMARY KEY (fiIdCompra),
 FOREIGN KEY (fiIdTienda) REFERENCES Tienda(fiIdTienda),
 FOREIGN KEY (fiIdPersona) REFERENCES Cliente(fiIdPersona),
 FOREIGN KEY (fiIdEstatus) REFERENCES CatalogoEstatus(fiIdEstatus)
);



CREATE INDEX XIE1Compra ON Compra
(fiIdTienda  ASC);



CREATE INDEX XIE2Compra ON Compra
(fiIdPersona  ASC);



CREATE INDEX XIE3Compra ON Compra
(fdFechaModificacion  ASC);






CREATE TABLE PagoCompra
(
	fiIdPagoCompra	  INT  NOT NULL ,
	fiIdCompra	  INT  NOT NULL ,
	fdFechaCompra	  DATE  NULL ,
	fiIdEstatus	  INT  NOT NULL ,
	fiIdEstatusTipoCompra  INT  NOT NULL ,
	fiidMoneda	  INT  NOT NULL ,
	fdFechaModificacion  TIMESTAMP  NOT NULL ,
	fcUsuarioModifico  VARCHAR(20)  NOT NULL ,
 PRIMARY KEY (fiIdPagoCompra),
 FOREIGN KEY (fiIdCompra) REFERENCES Compra(fiIdCompra),
 FOREIGN KEY (fiIdEstatus) REFERENCES CatalogoEstatus(fiIdEstatus),
 FOREIGN KEY (fiIdEstatusTipoCompra) REFERENCES CatalogoEstatus(fiIdEstatus),
 FOREIGN KEY (fiidMoneda) REFERENCES Moneda(fiidMoneda)
);



CREATE UNIQUE INDEX XAK1PagoCompra ON PagoCompra
(fiIdPagoCompra  ASC,fiIdCompra  ASC);



CREATE INDEX XIE1PagoCompra ON PagoCompra
(fdFechaCompra  ASC,fiIdEstatus  ASC,fiIdEstatusTipoCompra  ASC);



CREATE INDEX XIE2PagoCompra ON PagoCompra
(fiIdEstatusTipoCompra  ASC);



CREATE TABLE PagoCompraEfectivo
(
	fiIdPagoCompra	  INT  NOT NULL ,
	fiCantidad	  INT  NULL ,
	fnDenominacion	  DECIMAL(12,2)  NULL ,
 PRIMARY KEY (fiIdPagoCompra),
 FOREIGN KEY (fiIdPagoCompra) REFERENCES PagoCompra(fiIdPagoCompra)
);






CREATE TABLE PagoCompraSPEI
(
	fiIdPagoCompra	  INT  NOT NULL ,
	fnMonto		  DECIMAL(12,2)  NULL ,
	fcReferencia	  VARCHAR(20)  NOT NULL ,
	fcBancoOrigen	  VARCHAR(35)  NOT NULL ,
	fcBancoDestino	  VARCHAR(35)  NULL ,
	fcTitular	  VARCHAR(100)  NOT NULL ,
	fcCuenta	  VARCHAR(25)  NOT NULL ,
	fcTipoCuenta	  VARCHAR(25)  NOT NULL ,
	fcCodigoRastreo	  VARCHAR(25)  NULL ,
 PRIMARY KEY (fiIdPagoCompra),
 FOREIGN KEY (fiIdPagoCompra) REFERENCES PagoCompra(fiIdPagoCompra)
);



CREATE INDEX XIE1PagoCompraSPEI ON PagoCompraSPEI
(fcTitular  ASC);



CREATE INDEX XIE2PagoCompraSPEI ON PagoCompraSPEI
(fcBancoDestino  ASC);






CREATE TABLE BitacoraCompra
(
	fiIdBitacoraCompra  INT  NOT NULL ,
	fiIdCompra	  INT  NOT NULL ,
	fiIdEstatus	  INT  NOT NULL ,
	fcUsuarioModifico  VARCHAR(20)  NOT NULL ,
	fdFechaModificacion  TIMESTAMP  NOT NULL ,
 PRIMARY KEY (fiIdBitacoraCompra),
 FOREIGN KEY (fiIdCompra) REFERENCES Compra(fiIdCompra),
 FOREIGN KEY (fiIdEstatus) REFERENCES CatalogoEstatus(fiIdEstatus)
);





CREATE TABLE SeguriBolsa
(
	fiIdSeguribolsa	  INT  NOT NULL ,
	fiIdCompra	  INT  NOT NULL ,
	fcCodigoBolsa	  VARCHAR(10)  NULL ,
	fiIdEstatus	  INT  NOT NULL ,
	fdFechaModificacion  TIMESTAMP  NOT NULL ,
	fcUsuarioModifico  VARCHAR(20)  NOT NULL ,
 PRIMARY KEY (fiIdSeguribolsa),
 FOREIGN KEY (fiIdEstatus) REFERENCES CatalogoEstatus(fiIdEstatus),
 FOREIGN KEY (fiIdCompra) REFERENCES Compra(fiIdCompra)
);



CREATE UNIQUE INDEX XAK1SeguriBolsa ON SeguriBolsa
(fcCodigoBolsa  ASC);



CREATE TABLE ArticuloCompra
(
	fiIdArticuloCompra  INT  NOT NULL ,
	fiIdSeguribolsa	  INT  NOT NULL ,
	fiIdEstatus	  INT  NOT NULL ,
	fcDescripcion	  VARCHAR(100)  NULL ,
	fnValorMonetario  DECIMAL(12,2)  NULL ,
	fiidMoneda	  INT  NULL ,
	fiIdTipoArticulo  INT  NOT NULL ,
	fcUsuarioModifico  VARCHAR(20)  NOT NULL ,
	fdFechaModificacion  TIMESTAMP  NOT NULL ,
 PRIMARY KEY (fiIdArticuloCompra),
 FOREIGN KEY (fiIdEstatus) REFERENCES CatalogoEstatus(fiIdEstatus),
 FOREIGN KEY (fiIdTipoArticulo) REFERENCES CatalogoEstatus(fiIdEstatus),
 FOREIGN KEY (fiidMoneda) REFERENCES Moneda(fiidMoneda),
 FOREIGN KEY (fiIdSeguribolsa) REFERENCES SeguriBolsa(fiIdSeguribolsa)
);



CREATE UNIQUE INDEX XAK1ArticuloCompra ON ArticuloCompra
(fiIdArticuloCompra  ASC);



CREATE INDEX XIE1ArticuloCompra ON ArticuloCompra
(fiIdTipoArticulo  ASC);



CREATE INDEX XIE2ArticuloCompra ON ArticuloCompra
(fiIdTipoArticulo  ASC);




CREATE TABLE ArticuloCompraMetal
(
	fiIdArticuloCompra  INT  NOT NULL ,
	fnPesoNeto	  DECIMAL(12,3)  NOT NULL ,
	fnPesoBruto	  DECIMAL(12,3)  NULL ,
	fiIdPrecioMetalGramoFino  INT  NOT NULL ,
	fnPorcentajePureza  DECIMAL(12,2)  NOT NULL ,
	fnPesoFino	  DECIMAL(14,4)  NULL ,
 PRIMARY KEY (fiIdArticuloCompra),
 FOREIGN KEY (fiIdPrecioMetalGramoFino) REFERENCES PrecioMetalGramoFino(fiIdPrecioMetalGramoFino),
 FOREIGN KEY (fiIdArticuloCompra) REFERENCES ArticuloCompra(fiIdArticuloCompra)
);



