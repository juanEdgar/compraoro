
CREATE  SEQUENCE SEQ_TC INCREMENT  BY 1
     MINVALUE 1  
     START  WITH 4;
          

CREATE  SEQUENCE SEQ_CATLOGOS INCREMENT  BY 1
     MINVALUE 1  
     START  WITH 1;
          
     
CREATE  SEQUENCE SEQ_PRECIOMETAL INCREMENT  BY 1
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
  1,
  'Estatus de registro',
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
  1,
  1,
  'REGISTRO ACTIVO',
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
  0,
  1,
  'REGISTRO INACTIVO',
  'SYS',
  DATE '2015-01-15'
);







INSERT INTO moneda
(
  fiidmoneda,
  fcunidad,
  fcnombre,
  fcSimbolo
  
)
VALUES
(
  1,
  'MXP',
  'PESO MEXICANO',
  '$'
);

INSERT INTO moneda
(
  fiidmoneda,
  fcunidad,
  fcnombre,
  fcSimbolo
)
VALUES
(
   2,
  'USD',
  'DOLAR AMERICANO',
  '$'
);

INSERT INTO moneda
(
  fiidmoneda,
  fcunidad,
  fcnombre,
  fcSimbolo
)
VALUES
(
  3,
  'GTQ',
  'Quetzal Guatemala',
  'Q'
);

INSERT INTO moneda
(
  fiidmoneda,
  fcunidad,
  fcnombre,
  fcSimbolo
)
VALUES
(
  4,
  'EUR',
  'EURO',
  '€'
);


INSERT INTO tipocambio
( fiIdTipoDeCambio,
  fiidmoneda,
  fiidmonedacambio,
  fdtipodecambio,
  fiidestatus,
  fcusuariomodifico,
  fdfechamodificacion
)
VALUES
(1,
  2,
  1,
  15.5000,
  1,
  'SYS','2015-01-15'
);

INSERT INTO tipocambio
( fiIdTipoDeCambio,
  fiidmoneda,
  fiidmonedacambio,
  fdtipodecambio,
  fiidestatus,
  fcusuariomodifico,
  fdfechamodificacion
)
VALUES
(2,
  2,
  3,
  7.3500,
  1,
  'SYS','2015-01-15'
);

INSERT INTO tipocambio
( fiIdTipoDeCambio,
  fiidmoneda,
  fiidmonedacambio,
  fdtipodecambio,
  fiidestatus,
  fcusuariomodifico,
  fdfechamodificacion
)
VALUES
( 3,
  4,
  1,
  16.7500,
  1,
  'SYS','2015-01-15'
);





INSERT INTO metal
(
  fiidmetal,
  fcnombre,
  fiidestatus,
  fnpurezabase
)
VALUES
(
  1,
  'ORO',
  1,
  24.0000
);




INSERT INTO MetalPureza
(
  fiidtipopureza,
  fiidmetal,
  fcnombre,
  fiidestatus,
  fnproporcionpureza
)
VALUES
(
  1,
  1,
  '0 k',
  1,
  0.0000
);

INSERT INTO MetalPureza
(
  fiidtipopureza,
  fiidmetal,
  fcnombre,
  fiidestatus,
  fnproporcionpureza
)
VALUES
(
  2,
  1,
  '8 k',
  1,
  8.0000
);

INSERT INTO MetalPureza
(
  fiidtipopureza,
  fiidmetal,
  fcnombre,
  fiidestatus,
  fnproporcionpureza
)
VALUES
(
  3,
  1,
  '10 k',
  1,
  10.0000
);

INSERT INTO MetalPureza
(
  fiidtipopureza,
  fiidmetal,
  fcnombre,
  fiidestatus,
  fnproporcionpureza
)
VALUES
(
  4,
  1,
  '14 k',
  1,
  14.0000
);

INSERT INTO MetalPureza
(
  fiidtipopureza,
  fiidmetal,
  fcnombre,
  fiidestatus,
  fnproporcionpureza
)
VALUES
(
  5,
  1,
  '18 k',
  1,
  18.0000
);

INSERT INTO MetalPureza
(
  fiidtipopureza,
  fiidmetal,
  fcnombre,
  fiidestatus,
  fnproporcionpureza
)
VALUES
(
  6,
  1,
  '21 k',
  1,
  21.0000
);

INSERT INTO MetalPureza
(
  fiidtipopureza,
  fiidmetal,
  fcnombre,
  fiidestatus,
  fnproporcionpureza
)
VALUES
(
  7,
  1,
  '24 k',
  1,
  24.0000
);




INSERT INTO metal
(
  fiidmetal,
  fcnombre,
  fiidestatus,
  fnpurezabase
)
VALUES
(
  2,
  'PLATA',
  1,
  999
);




INSERT INTO MetalPureza
(
  fiidtipopureza,
  fiidmetal,
  fcnombre,
  fiidestatus,
  fnproporcionpureza
)
VALUES
(
  -1,
  2,
  '999 L',
  1,
  999
);

INSERT INTO MetalPureza
(
  fiidtipopureza,
  fiidmetal,
  fcnombre,
  fiidestatus,
  fnproporcionpureza
)
VALUES
(
  -2,
  2,
  '800 L',
  1,
  800
);


INSERT INTO metal
(
  fiidmetal,
  fcnombre,
  fiidestatus,
  fnpurezabase
)
VALUES
(
  3,
  'PLATINO',
  1,
  999
);


INSERT INTO metal
(
  fiidmetal,
  fcnombre,
  fiidestatus,
  fnpurezabase
)
VALUES
(
  4,
  'PALADIO',
  1,
  999
);




CREATE VIEW V_tipocambio AS
SELECT *
FROM TIPOCAMBIO
WHERE FIIDESTATUS=1;



CREATE VIEW V_preciometalgramofino AS
SELECT *
FROM preciometalgramofino
WHERE FIIDESTATUS=1;


CREATE VIEW V_metalpureza AS
SELECT *
FROM metalpureza
WHERE FIIDESTATUS=1;








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
  -1,
  1,
  10,
  2,
  1,
  'sys',
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
  -2,
  2,
  10,
  2,
  1,
  'sys',
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
  -3,
  3,
  10,
  2,
  1,
  'sys',
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
  -4,
  4,
  10,
  2,
  1,
  'sys',
  '20150101'
);




