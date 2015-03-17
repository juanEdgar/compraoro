
CREATE  SEQUENCE seq_persona INCREMENT  BY 1
     MINVALUE 1  
     START  WITH 2;



INSERT INTO catalogogrupoestatus
(
  fiidgrupoestatus,
  fcdescripcion,
  fcusuariomodifico,
  fdfechamodificacion
)
VALUES
(
  3,
  'TIPO DE PERSONAS',
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
  -1,
  3,
  'CLIENTE',
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
  -2,
  3,
  'USUARIO TIENDA',
  'SYS',
  '2015-01-15'
);








