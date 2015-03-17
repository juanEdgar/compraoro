
CREATE  SEQUENCE seq_tienda INCREMENT  BY 1
     MINVALUE 1  
     START  WITH 2;

     
     
CREATE  SEQUENCE seq_tiendacajaefectivomovimiento INCREMENT  BY 5
     MINVALUE 1  
     START  WITH 1;



INSERT INTO catalogogrupoestatus
(
  fiidgrupoestatus,
  fcdescripcion,
  fcusuariomodifico,
  fdfechamodificacion
)
VALUES
(
  2,
  'Estatus de tiendas',
  'SYS',
  '2015-01-15'
);


INSERT INTO catalogoestatus
(
  fiidestatus,
  fiidgrupoestatus,
  fcdescripcion,
  fcusuariomodifico,
  fdfechamodificacion
)
VALUES
(
  2,
  2,
  'ABIERTA',
  'SYS',
  '2015-01-15'
);

INSERT INTO catalogoestatus
(
  fiidestatus,
  fiidgrupoestatus,
  fcdescripcion,
  fcusuariomodifico,
  fdfechamodificacion
)
VALUES
(
  3,
  2,
  'CERRADA',
  'SYS',
  '2015-01-15'
);

INSERT INTO catalogoestatus
(
  fiidestatus,
  fiidgrupoestatus,
  fcdescripcion,
  fcusuariomodifico,
  fdfechamodificacion
)
VALUES
(
  4,
  2,
  'FUSIONADA',
  'SYS',
  '2015-01-15'
);


INSERT INTO tienda
(
  fiidtienda,
  finumero,
  fcnombre,
  fiidestatus,
  fcusuariomodifico,
  fdfechamodificacion
)
VALUES
(
  1,
  1,
  'TIENDA UNO',
  2,
  'SYS',
  '20150115'
);

INSERT INTO tienda
(
  fiidtienda,
  finumero,
  fcnombre,
  fiidestatus,
  fcusuariomodifico,
  fdfechamodificacion
)
VALUES
(
  0,
  1,
  'CENTRAL',
  2,
  'SYS',
  '20150115'
);




INSERT INTO catalogogrupoestatus
(
  fiidgrupoestatus,
  fcdescripcion,
  fcusuariomodifico,
  fdfechamodificacion
)
VALUES
(
  6,
  'Dotaciones de caja en efectivo',
  'SYS',
  '2015-01-15'
);


INSERT INTO catalogoestatus
(
  fiidestatus,
  fiidgrupoestatus,
  fcdescripcion,
  fcusuariomodifico,
  fdfechamodificacion
)
VALUES
(
  5,
  6,
  'Dotacion a caja',
  'SYS',
  '2015-01-15'
);

INSERT INTO catalogoestatus
(
  fiidestatus,
  fiidgrupoestatus,
  fcdescripcion,
  fcusuariomodifico,
  fdfechamodificacion
)
VALUES
(
  6,
  6,
  'Saldar caja',
  'SYS',
  '2015-01-15'
);


INSERT INTO catalogoestatus
(
  fiidestatus,
  fiidgrupoestatus,
  fcdescripcion,
  fcusuariomodifico,
  fdfechamodificacion
)
VALUES
(
  7,
  6,
  'Egreso en caja por compra',
  'SYS',
  '2015-01-15'
);




INSERT INTO tiendacajaefectivo
(
  fiidtienda,
  fiidcaja,
  fiidestatus
)
VALUES
(
  1,
  1,
  1
);



INSERT INTO tiendacajaefectivosaldo
(
  fiidtienda,
  fiidcaja,
  fndenominacion,
  fiidestatus,
  ficantidad
)
VALUES
(
  1,
  1,
  10,
  1,
  1
);

INSERT INTO tiendacajaefectivosaldo
(
  fiidtienda,
  fiidcaja,
  fndenominacion,
  fiidestatus,
  ficantidad
)
VALUES
(
  1,
  1,
  20,
  1,
  1
);


INSERT INTO tiendacajaefectivosaldo
(
  fiidtienda,
  fiidcaja,
  fndenominacion,
  fiidestatus,
  ficantidad
)
VALUES
(
  1,
  1,
  50,
  1,
  1
);

INSERT INTO tiendacajaefectivosaldo
(
  fiidtienda,
  fiidcaja,
  fndenominacion,
  fiidestatus,
  ficantidad
)
VALUES
(
  1,
  1,
  100,
  1,
  1
);


INSERT INTO tiendacajaefectivosaldo
(
  fiidtienda,
  fiidcaja,
  fndenominacion,
  fiidestatus,
  ficantidad
)
VALUES
(
  1,
  1,
  200,
  1,
  1
);


INSERT INTO tiendacajaefectivosaldo
(
  fiidtienda,
  fiidcaja,
  fndenominacion,
  fiidestatus,
  ficantidad
)
VALUES
(
  1,
  1,
  500,
  1,
  1
);


INSERT INTO tiendacajaefectivosaldo
(
  fiidtienda,
  fiidcaja,
  fndenominacion,
  fiidestatus,
  ficantidad
)
VALUES
(
  1,
  1,
  1000,
  1,
  0
);

