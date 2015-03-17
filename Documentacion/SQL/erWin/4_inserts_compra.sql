
     


INSERT INTO catalogogrupoestatus
(
  fiidgrupoestatus,
  fcdescripcion,
  fcusuariomodifico,
  fdfechamodificacion
)
VALUES
(
  4,
  'TIPO ARTICULO',
  'SYS',
  DATE '2015-01-15'
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
  5,
  'TIPO PAGO',
  'SYS',
  DATE '2015-01-15'
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
  -100,
  4,
  'ARTICULO METAL',
  'SYS',
  DATE '2015-01-15'
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
  -101,
  4,
  'ARTICULO DIAMANTES',
  'SYS',
  DATE '2015-01-15'
);
psqlusrcompraoro

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
  -200,
  5,
  'PAGO EFECTIVO',
  'SYS',
  DATE '2015-01-15'
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
  -201,
  5,
  'PAGO SPEI',
  'SYS',
  DATE '2015-01-15'
);



INSERT INTO preciometalgramofino
(
  fiidpreciometalgramofino,
  fiidmetal,
  fdprecio,
  fiidmoneda,
  fiidestatus,
  fcusuariomodifico,
  fdfechamodificacion
)
VALUES
(
  1,
  1,
  1,
  2,
  1,
  'SYS',
  '20150101'
);


INSERT INTO preciometalgramofino
(
  fiidpreciometalgramofino,
  fiidmetal,
  fdprecio,
  fiidmoneda,
  fiidestatus,
  fcusuariomodifico,
  fdfechamodificacion
)
VALUES
(
  2,
  2,
  1,
  2,
  1,
  'SYS',
  '20150101'
);



