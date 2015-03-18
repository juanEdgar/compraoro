CREATE VIEW V_PrecioDiamanteQuilate AS
SELECT *
FROM precioDiamanteQuilate
WHERE FIIDESTATUS=1;




INSERT INTO diamantecolor(  fiiddiamantecolor,  fccolor)
VALUES(  1,  'F-G');

INSERT INTO diamantecolor(  fiiddiamantecolor,  fccolor)
VALUES(  2,  'H-I-J');

INSERT INTO diamantecolor(  fiiddiamantecolor,  fccolor)
VALUES(  3,  'K-L-M');

INSERT INTO diamantecolor(  fiiddiamantecolor,  fccolor)
VALUES(  4,  'MUCHO COLOR');


INSERT INTO diamantelimpieza(  fiiddiamantelimpieza,  fclimpieza)
VALUES(  1,  'CASI LIMPIOS VVS-VS1');

INSERT INTO diamantelimpieza(  fiiddiamantelimpieza,  fclimpieza)
VALUES(  2,  'LIGERAS INCLUSIONES.VS2');

INSERT INTO diamantelimpieza(  fiiddiamantelimpieza,  fclimpieza)
VALUES(  3,  'CON INCLUSIONES.SI1 Y SI2');

INSERT INTO diamantelimpieza(  fiiddiamantelimpieza,  fclimpieza)
VALUES(  4,  'CON INCLUSIONES.SI1 Y SI2');


INSERT INTO diamantepunto(  fiiddiamantepunto,  fcrangopuntos)
VALUES(  1,  'DE 0.01  A 0.08 PUNTOS');

INSERT INTO diamantepunto(  fiiddiamantepunto,  fcrangopuntos)
VALUES(  2,  'DE 0.11 A 0.25 PUNTOS');

INSERT INTO diamantepunto(  fiiddiamantepunto,  fcrangopuntos)
VALUES(  3,  'DE 0.30 A 0.50 PUNTOS');

INSERT INTO diamantepunto(  fiiddiamantepunto,  fcrangopuntos)
VALUES(  4,  'DE 0.51 A 1.00 PUNTOS');


INSERT INTO diamante(  fiidtipodiamante,  fiiddiamantecolor,  fiiddiamantepunto,  fiiddiamantelimpieza,  fiidestatus
)
VALUES(  1,  1,  1,	1,1);

INSERT INTO diamante(  fiidtipodiamante,  fiiddiamantecolor,  fiiddiamantepunto,  fiiddiamantelimpieza,  fiidestatus
)
VALUES(  2,  2,  1,	1,1);

INSERT INTO diamante(  fiidtipodiamante,  fiiddiamantecolor,  fiiddiamantepunto,  fiiddiamantelimpieza,  fiidestatus
)
VALUES(  3,  3,  1,	1,1);

INSERT INTO diamante(  fiidtipodiamante,  fiiddiamantecolor,  fiiddiamantepunto,  fiiddiamantelimpieza,  fiidestatus
)
VALUES(  4,  4,  1,	1,1);

INSERT INTO diamante(  fiidtipodiamante,  fiiddiamantecolor,  fiiddiamantepunto,  fiiddiamantelimpieza,  fiidestatus
)
VALUES(  5,  1,  1,	2,1);

INSERT INTO diamante(  fiidtipodiamante,  fiiddiamantecolor,  fiiddiamantepunto,  fiiddiamantelimpieza,  fiidestatus
)
VALUES(  6,  2,  1,	2,1);

INSERT INTO diamante(  fiidtipodiamante,  fiiddiamantecolor,  fiiddiamantepunto,  fiiddiamantelimpieza,  fiidestatus
)
VALUES(  7,  3,  1,	2,1);

INSERT INTO diamante(  fiidtipodiamante,  fiiddiamantecolor,  fiiddiamantepunto,  fiiddiamantelimpieza,  fiidestatus
)
VALUES(  8,  4,  1,	2,1);



INSERT INTO diamante(  fiidtipodiamante,  fiiddiamantecolor,  fiiddiamantepunto,  fiiddiamantelimpieza,  fiidestatus
)
VALUES(  9,  1,  1,	3,1);

INSERT INTO diamante(  fiidtipodiamante,  fiiddiamantecolor,  fiiddiamantepunto,  fiiddiamantelimpieza,  fiidestatus
)
VALUES(  10,  2,  1,	3,1);

INSERT INTO diamante(  fiidtipodiamante,  fiiddiamantecolor,  fiiddiamantepunto,  fiiddiamantelimpieza,  fiidestatus
)
VALUES(  11,  3,  1,	3,1);

INSERT INTO diamante(  fiidtipodiamante,  fiiddiamantecolor,  fiiddiamantepunto,  fiiddiamantelimpieza,  fiidestatus
)
VALUES(  12,  4,  1,	3,1);

INSERT INTO diamante(  fiidtipodiamante,  fiiddiamantecolor,  fiiddiamantepunto,  fiiddiamantelimpieza,  fiidestatus
)
VALUES(  13,  1,  1,	4,1);

INSERT INTO diamante(  fiidtipodiamante,  fiiddiamantecolor,  fiiddiamantepunto,  fiiddiamantelimpieza,  fiidestatus
)
VALUES(  14,  2,  1,	4,1);

INSERT INTO diamante(  fiidtipodiamante,  fiiddiamantecolor,  fiiddiamantepunto,  fiiddiamantelimpieza,  fiidestatus
)
VALUES(  15,  3,  1,	4,1);

INSERT INTO diamante(  fiidtipodiamante,  fiiddiamantecolor,  fiiddiamantepunto,  fiiddiamantelimpieza,  fiidestatus
)
VALUES(  16,  4,  1,	4,1);




INSERT INTO diamante(  fiidtipodiamante,  fiiddiamantecolor,  fiiddiamantepunto,  fiiddiamantelimpieza,  fiidestatus
)
VALUES(  17,  1,  2,	1,1);

INSERT INTO diamante(  fiidtipodiamante,  fiiddiamantecolor,  fiiddiamantepunto,  fiiddiamantelimpieza,  fiidestatus
)
VALUES(  18,  2,  2,	1,1);

INSERT INTO diamante(  fiidtipodiamante,  fiiddiamantecolor,  fiiddiamantepunto,  fiiddiamantelimpieza,  fiidestatus
)
VALUES(  19,  3,  2,	1,1);

INSERT INTO diamante(  fiidtipodiamante,  fiiddiamantecolor,  fiiddiamantepunto,  fiiddiamantelimpieza,  fiidestatus
)
VALUES(  20,  4,  2,	1,1);

INSERT INTO diamante(  fiidtipodiamante,  fiiddiamantecolor,  fiiddiamantepunto,  fiiddiamantelimpieza,  fiidestatus
)
VALUES(  21,  1,  2,	2,1);

INSERT INTO diamante(  fiidtipodiamante,  fiiddiamantecolor,  fiiddiamantepunto,  fiiddiamantelimpieza,  fiidestatus
)
VALUES(  22,  2,  2,	2,1);

INSERT INTO diamante(  fiidtipodiamante,  fiiddiamantecolor,  fiiddiamantepunto,  fiiddiamantelimpieza,  fiidestatus
)
VALUES(  23,  3,  2,	2,1);

INSERT INTO diamante(  fiidtipodiamante,  fiiddiamantecolor,  fiiddiamantepunto,  fiiddiamantelimpieza,  fiidestatus
)
VALUES(  24,  4,  2,	2,1);


INSERT INTO diamante(  fiidtipodiamante,  fiiddiamantecolor,  fiiddiamantepunto,  fiiddiamantelimpieza,  fiidestatus
)
VALUES(  25,  1,  2,	3,1);

INSERT INTO diamante(  fiidtipodiamante,  fiiddiamantecolor,  fiiddiamantepunto,  fiiddiamantelimpieza,  fiidestatus
)
VALUES(  26,  2,  2,	3,1);

INSERT INTO diamante(  fiidtipodiamante,  fiiddiamantecolor,  fiiddiamantepunto,  fiiddiamantelimpieza,  fiidestatus
)
VALUES(  27,  3,  2,	3,1);

INSERT INTO diamante(  fiidtipodiamante,  fiiddiamantecolor,  fiiddiamantepunto,  fiiddiamantelimpieza,  fiidestatus
)
VALUES(  28,  4,  2,	3,1);

INSERT INTO diamante(  fiidtipodiamante,  fiiddiamantecolor,  fiiddiamantepunto,  fiiddiamantelimpieza,  fiidestatus
)
VALUES(  29,  1,  2,	4,1);

INSERT INTO diamante(  fiidtipodiamante,  fiiddiamantecolor,  fiiddiamantepunto,  fiiddiamantelimpieza,  fiidestatus
)
VALUES(  30,  2,  2,	4,1);

INSERT INTO diamante(  fiidtipodiamante,  fiiddiamantecolor,  fiiddiamantepunto,  fiiddiamantelimpieza,  fiidestatus
)
VALUES(  31,  3,  2,	4,1);

INSERT INTO diamante(  fiidtipodiamante,  fiiddiamantecolor,  fiiddiamantepunto,  fiiddiamantelimpieza,  fiidestatus
)
VALUES(  32,  4,  2,	4,1);




INSERT INTO diamante(  fiidtipodiamante,  fiiddiamantecolor,  fiiddiamantepunto,  fiiddiamantelimpieza,  fiidestatus
)
VALUES(  33,  1,  3,	1,1);

INSERT INTO diamante(  fiidtipodiamante,  fiiddiamantecolor,  fiiddiamantepunto,  fiiddiamantelimpieza,  fiidestatus
)
VALUES(  34,  2,  3,	1,1);

INSERT INTO diamante(  fiidtipodiamante,  fiiddiamantecolor,  fiiddiamantepunto,  fiiddiamantelimpieza,  fiidestatus
)
VALUES(  35,  3,  3,	1,1);

INSERT INTO diamante(  fiidtipodiamante,  fiiddiamantecolor,  fiiddiamantepunto,  fiiddiamantelimpieza,  fiidestatus
)
VALUES(  36,  4,  3,	1,1);

INSERT INTO diamante(  fiidtipodiamante,  fiiddiamantecolor,  fiiddiamantepunto,  fiiddiamantelimpieza,  fiidestatus
)
VALUES(  37,  1,  3,	2,1);

INSERT INTO diamante(  fiidtipodiamante,  fiiddiamantecolor,  fiiddiamantepunto,  fiiddiamantelimpieza,  fiidestatus
)
VALUES(  38,  2,  3,	2,1);

INSERT INTO diamante(  fiidtipodiamante,  fiiddiamantecolor,  fiiddiamantepunto,  fiiddiamantelimpieza,  fiidestatus
)
VALUES(  39,  3,  3,	2,1);

INSERT INTO diamante(  fiidtipodiamante,  fiiddiamantecolor,  fiiddiamantepunto,  fiiddiamantelimpieza,  fiidestatus
)
VALUES(  40,  4,  3,	2,1);


INSERT INTO diamante(  fiidtipodiamante,  fiiddiamantecolor,  fiiddiamantepunto,  fiiddiamantelimpieza,  fiidestatus
)
VALUES(  41,  1,  3,	3,1);

INSERT INTO diamante(  fiidtipodiamante,  fiiddiamantecolor,  fiiddiamantepunto,  fiiddiamantelimpieza,  fiidestatus
)
VALUES(  42,  2,  3,	3,1);

INSERT INTO diamante(  fiidtipodiamante,  fiiddiamantecolor,  fiiddiamantepunto,  fiiddiamantelimpieza,  fiidestatus
)
VALUES(  43,  3,  3,	3,1);

INSERT INTO diamante(  fiidtipodiamante,  fiiddiamantecolor,  fiiddiamantepunto,  fiiddiamantelimpieza,  fiidestatus
)
VALUES(  44,  4,  3,	3,1);

INSERT INTO diamante(  fiidtipodiamante,  fiiddiamantecolor,  fiiddiamantepunto,  fiiddiamantelimpieza,  fiidestatus
)
VALUES(  45,  1,  3,	4,1);

INSERT INTO diamante(  fiidtipodiamante,  fiiddiamantecolor,  fiiddiamantepunto,  fiiddiamantelimpieza,  fiidestatus
)
VALUES(  46,  2,  3,	4,1);

INSERT INTO diamante(  fiidtipodiamante,  fiiddiamantecolor,  fiiddiamantepunto,  fiiddiamantelimpieza,  fiidestatus
)
VALUES(  47,  3,  3,	4,1);

INSERT INTO diamante(  fiidtipodiamante,  fiiddiamantecolor,  fiiddiamantepunto,  fiiddiamantelimpieza,  fiidestatus
)
VALUES(  48,  4,  3,	4,1);










INSERT INTO diamante(  fiidtipodiamante,  fiiddiamantecolor,  fiiddiamantepunto,  fiiddiamantelimpieza,  fiidestatus
)
VALUES(  49,  1,  4,	1,1);

INSERT INTO diamante(  fiidtipodiamante,  fiiddiamantecolor,  fiiddiamantepunto,  fiiddiamantelimpieza,  fiidestatus
)
VALUES(  50,  2,  4,	1,1);

INSERT INTO diamante(  fiidtipodiamante,  fiiddiamantecolor,  fiiddiamantepunto,  fiiddiamantelimpieza,  fiidestatus
)
VALUES(  51,  3,  4,	1,1);

INSERT INTO diamante(  fiidtipodiamante,  fiiddiamantecolor,  fiiddiamantepunto,  fiiddiamantelimpieza,  fiidestatus
)
VALUES(  52,  4,  4,	1,1);

INSERT INTO diamante(  fiidtipodiamante,  fiiddiamantecolor,  fiiddiamantepunto,  fiiddiamantelimpieza,  fiidestatus
)
VALUES(  53,  1,  4,	2,1);

INSERT INTO diamante(  fiidtipodiamante,  fiiddiamantecolor,  fiiddiamantepunto,  fiiddiamantelimpieza,  fiidestatus
)
VALUES(  54,  2,  4,	2,1);

INSERT INTO diamante(  fiidtipodiamante,  fiiddiamantecolor,  fiiddiamantepunto,  fiiddiamantelimpieza,  fiidestatus
)
VALUES(  55,  3,  4,	2,1);

INSERT INTO diamante(  fiidtipodiamante,  fiiddiamantecolor,  fiiddiamantepunto,  fiiddiamantelimpieza,  fiidestatus
)
VALUES(  56,  4,  4,	2,1);


INSERT INTO diamante(  fiidtipodiamante,  fiiddiamantecolor,  fiiddiamantepunto,  fiiddiamantelimpieza,  fiidestatus
)
VALUES(  57,  1,  4,	3,1);

INSERT INTO diamante(  fiidtipodiamante,  fiiddiamantecolor,  fiiddiamantepunto,  fiiddiamantelimpieza,  fiidestatus
)
VALUES(  58,  2,  4,	3,1);

INSERT INTO diamante(  fiidtipodiamante,  fiiddiamantecolor,  fiiddiamantepunto,  fiiddiamantelimpieza,  fiidestatus
)
VALUES(  59,  3,  4,	3,1);

INSERT INTO diamante(  fiidtipodiamante,  fiiddiamantecolor,  fiiddiamantepunto,  fiiddiamantelimpieza,  fiidestatus
)
VALUES(  60,  4,  4,	3,1);

INSERT INTO diamante(  fiidtipodiamante,  fiiddiamantecolor,  fiiddiamantepunto,  fiiddiamantelimpieza,  fiidestatus
)
VALUES(  61,  1,  4,	4,1);

INSERT INTO diamante(  fiidtipodiamante,  fiiddiamantecolor,  fiiddiamantepunto,  fiiddiamantelimpieza,  fiidestatus
)
VALUES(  62,  2,  4,	4,1);

INSERT INTO diamante(  fiidtipodiamante,  fiiddiamantecolor,  fiiddiamantepunto,  fiiddiamantelimpieza,  fiidestatus
)
VALUES(  63,  3,  4,	4,1);

INSERT INTO diamante(  fiidtipodiamante,  fiiddiamantecolor,  fiiddiamantepunto,  fiiddiamantelimpieza,  fiidestatus
)
VALUES(  64,  4,  4,	4,1);



INSERT INTO preciodiamantequilate( fiidpreciodiamante,fiidtipodiamante,fnprecio,fiidmoneda,fiidestatus,fcusuariomodifico, fdfechamodificacion) values(-500,1,1,2,1,'sys','20150101');
INSERT INTO preciodiamantequilate( fiidpreciodiamante,fiidtipodiamante,fnprecio,fiidmoneda,fiidestatus,fcusuariomodifico, fdfechamodificacion) values(-501,2,1,2,1,'sys','20150101');
INSERT INTO preciodiamantequilate( fiidpreciodiamante,fiidtipodiamante,fnprecio,fiidmoneda,fiidestatus,fcusuariomodifico, fdfechamodificacion) values(-502,3,1,2,1,'sys','20150101');
INSERT INTO preciodiamantequilate( fiidpreciodiamante,fiidtipodiamante,fnprecio,fiidmoneda,fiidestatus,fcusuariomodifico, fdfechamodificacion) values(-503,4,1,2,1,'sys','20150101');
INSERT INTO preciodiamantequilate( fiidpreciodiamante,fiidtipodiamante,fnprecio,fiidmoneda,fiidestatus,fcusuariomodifico, fdfechamodificacion) values(-504,5,1,2,1,'sys','20150101');
INSERT INTO preciodiamantequilate( fiidpreciodiamante,fiidtipodiamante,fnprecio,fiidmoneda,fiidestatus,fcusuariomodifico, fdfechamodificacion) values(-505,6,1,2,1,'sys','20150101');
INSERT INTO preciodiamantequilate( fiidpreciodiamante,fiidtipodiamante,fnprecio,fiidmoneda,fiidestatus,fcusuariomodifico, fdfechamodificacion) values(-506,7,1,2,1,'sys','20150101');
INSERT INTO preciodiamantequilate( fiidpreciodiamante,fiidtipodiamante,fnprecio,fiidmoneda,fiidestatus,fcusuariomodifico, fdfechamodificacion) values(-507,8,1,2,1,'sys','20150101');
INSERT INTO preciodiamantequilate( fiidpreciodiamante,fiidtipodiamante,fnprecio,fiidmoneda,fiidestatus,fcusuariomodifico, fdfechamodificacion) values(-508,9,1,2,1,'sys','20150101');
INSERT INTO preciodiamantequilate( fiidpreciodiamante,fiidtipodiamante,fnprecio,fiidmoneda,fiidestatus,fcusuariomodifico, fdfechamodificacion) values(-509,10,1,2,1,'sys','20150101');
INSERT INTO preciodiamantequilate( fiidpreciodiamante,fiidtipodiamante,fnprecio,fiidmoneda,fiidestatus,fcusuariomodifico, fdfechamodificacion) values(-510,11,1,2,1,'sys','20150101');
INSERT INTO preciodiamantequilate( fiidpreciodiamante,fiidtipodiamante,fnprecio,fiidmoneda,fiidestatus,fcusuariomodifico, fdfechamodificacion) values(-511,12,1,2,1,'sys','20150101');
INSERT INTO preciodiamantequilate( fiidpreciodiamante,fiidtipodiamante,fnprecio,fiidmoneda,fiidestatus,fcusuariomodifico, fdfechamodificacion) values(-512,13,1,2,1,'sys','20150101');
INSERT INTO preciodiamantequilate( fiidpreciodiamante,fiidtipodiamante,fnprecio,fiidmoneda,fiidestatus,fcusuariomodifico, fdfechamodificacion) values(-513,14,1,2,1,'sys','20150101');
INSERT INTO preciodiamantequilate( fiidpreciodiamante,fiidtipodiamante,fnprecio,fiidmoneda,fiidestatus,fcusuariomodifico, fdfechamodificacion) values(-514,15,1,2,1,'sys','20150101');
INSERT INTO preciodiamantequilate( fiidpreciodiamante,fiidtipodiamante,fnprecio,fiidmoneda,fiidestatus,fcusuariomodifico, fdfechamodificacion) values(-515,16,1,2,1,'sys','20150101');
INSERT INTO preciodiamantequilate( fiidpreciodiamante,fiidtipodiamante,fnprecio,fiidmoneda,fiidestatus,fcusuariomodifico, fdfechamodificacion) values(-516,17,1,2,1,'sys','20150101');
INSERT INTO preciodiamantequilate( fiidpreciodiamante,fiidtipodiamante,fnprecio,fiidmoneda,fiidestatus,fcusuariomodifico, fdfechamodificacion) values(-517,18,1,2,1,'sys','20150101');
INSERT INTO preciodiamantequilate( fiidpreciodiamante,fiidtipodiamante,fnprecio,fiidmoneda,fiidestatus,fcusuariomodifico, fdfechamodificacion) values(-518,19,1,2,1,'sys','20150101');
INSERT INTO preciodiamantequilate( fiidpreciodiamante,fiidtipodiamante,fnprecio,fiidmoneda,fiidestatus,fcusuariomodifico, fdfechamodificacion) values(-519,20,1,2,1,'sys','20150101');
INSERT INTO preciodiamantequilate( fiidpreciodiamante,fiidtipodiamante,fnprecio,fiidmoneda,fiidestatus,fcusuariomodifico, fdfechamodificacion) values(-520,21,1,2,1,'sys','20150101');
INSERT INTO preciodiamantequilate( fiidpreciodiamante,fiidtipodiamante,fnprecio,fiidmoneda,fiidestatus,fcusuariomodifico, fdfechamodificacion) values(-521,22,1,2,1,'sys','20150101');
INSERT INTO preciodiamantequilate( fiidpreciodiamante,fiidtipodiamante,fnprecio,fiidmoneda,fiidestatus,fcusuariomodifico, fdfechamodificacion) values(-522,23,1,2,1,'sys','20150101');
INSERT INTO preciodiamantequilate( fiidpreciodiamante,fiidtipodiamante,fnprecio,fiidmoneda,fiidestatus,fcusuariomodifico, fdfechamodificacion) values(-523,24,1,2,1,'sys','20150101');
INSERT INTO preciodiamantequilate( fiidpreciodiamante,fiidtipodiamante,fnprecio,fiidmoneda,fiidestatus,fcusuariomodifico, fdfechamodificacion) values(-524,25,1,2,1,'sys','20150101');
INSERT INTO preciodiamantequilate( fiidpreciodiamante,fiidtipodiamante,fnprecio,fiidmoneda,fiidestatus,fcusuariomodifico, fdfechamodificacion) values(-525,26,1,2,1,'sys','20150101');
INSERT INTO preciodiamantequilate( fiidpreciodiamante,fiidtipodiamante,fnprecio,fiidmoneda,fiidestatus,fcusuariomodifico, fdfechamodificacion) values(-526,27,1,2,1,'sys','20150101');
INSERT INTO preciodiamantequilate( fiidpreciodiamante,fiidtipodiamante,fnprecio,fiidmoneda,fiidestatus,fcusuariomodifico, fdfechamodificacion) values(-527,28,1,2,1,'sys','20150101');
INSERT INTO preciodiamantequilate( fiidpreciodiamante,fiidtipodiamante,fnprecio,fiidmoneda,fiidestatus,fcusuariomodifico, fdfechamodificacion) values(-528,29,1,2,1,'sys','20150101');
INSERT INTO preciodiamantequilate( fiidpreciodiamante,fiidtipodiamante,fnprecio,fiidmoneda,fiidestatus,fcusuariomodifico, fdfechamodificacion) values(-529,30,1,2,1,'sys','20150101');
INSERT INTO preciodiamantequilate( fiidpreciodiamante,fiidtipodiamante,fnprecio,fiidmoneda,fiidestatus,fcusuariomodifico, fdfechamodificacion) values(-530,31,1,2,1,'sys','20150101');
INSERT INTO preciodiamantequilate( fiidpreciodiamante,fiidtipodiamante,fnprecio,fiidmoneda,fiidestatus,fcusuariomodifico, fdfechamodificacion) values(-531,32,1,2,1,'sys','20150101');
INSERT INTO preciodiamantequilate( fiidpreciodiamante,fiidtipodiamante,fnprecio,fiidmoneda,fiidestatus,fcusuariomodifico, fdfechamodificacion) values(-532,33,1,2,1,'sys','20150101');
INSERT INTO preciodiamantequilate( fiidpreciodiamante,fiidtipodiamante,fnprecio,fiidmoneda,fiidestatus,fcusuariomodifico, fdfechamodificacion) values(-533,34,1,2,1,'sys','20150101');
INSERT INTO preciodiamantequilate( fiidpreciodiamante,fiidtipodiamante,fnprecio,fiidmoneda,fiidestatus,fcusuariomodifico, fdfechamodificacion) values(-534,35,1,2,1,'sys','20150101');
INSERT INTO preciodiamantequilate( fiidpreciodiamante,fiidtipodiamante,fnprecio,fiidmoneda,fiidestatus,fcusuariomodifico, fdfechamodificacion) values(-535,36,1,2,1,'sys','20150101');
INSERT INTO preciodiamantequilate( fiidpreciodiamante,fiidtipodiamante,fnprecio,fiidmoneda,fiidestatus,fcusuariomodifico, fdfechamodificacion) values(-536,37,1,2,1,'sys','20150101');
INSERT INTO preciodiamantequilate( fiidpreciodiamante,fiidtipodiamante,fnprecio,fiidmoneda,fiidestatus,fcusuariomodifico, fdfechamodificacion) values(-537,38,1,2,1,'sys','20150101');
INSERT INTO preciodiamantequilate( fiidpreciodiamante,fiidtipodiamante,fnprecio,fiidmoneda,fiidestatus,fcusuariomodifico, fdfechamodificacion) values(-538,39,1,2,1,'sys','20150101');
INSERT INTO preciodiamantequilate( fiidpreciodiamante,fiidtipodiamante,fnprecio,fiidmoneda,fiidestatus,fcusuariomodifico, fdfechamodificacion) values(-539,40,1,2,1,'sys','20150101');
INSERT INTO preciodiamantequilate( fiidpreciodiamante,fiidtipodiamante,fnprecio,fiidmoneda,fiidestatus,fcusuariomodifico, fdfechamodificacion) values(-540,41,1,2,1,'sys','20150101');
INSERT INTO preciodiamantequilate( fiidpreciodiamante,fiidtipodiamante,fnprecio,fiidmoneda,fiidestatus,fcusuariomodifico, fdfechamodificacion) values(-541,42,1,2,1,'sys','20150101');
INSERT INTO preciodiamantequilate( fiidpreciodiamante,fiidtipodiamante,fnprecio,fiidmoneda,fiidestatus,fcusuariomodifico, fdfechamodificacion) values(-542,43,1,2,1,'sys','20150101');
INSERT INTO preciodiamantequilate( fiidpreciodiamante,fiidtipodiamante,fnprecio,fiidmoneda,fiidestatus,fcusuariomodifico, fdfechamodificacion) values(-543,44,1,2,1,'sys','20150101');
INSERT INTO preciodiamantequilate( fiidpreciodiamante,fiidtipodiamante,fnprecio,fiidmoneda,fiidestatus,fcusuariomodifico, fdfechamodificacion) values(-544,45,1,2,1,'sys','20150101');
INSERT INTO preciodiamantequilate( fiidpreciodiamante,fiidtipodiamante,fnprecio,fiidmoneda,fiidestatus,fcusuariomodifico, fdfechamodificacion) values(-545,46,1,2,1,'sys','20150101');
INSERT INTO preciodiamantequilate( fiidpreciodiamante,fiidtipodiamante,fnprecio,fiidmoneda,fiidestatus,fcusuariomodifico, fdfechamodificacion) values(-546,47,1,2,1,'sys','20150101');
INSERT INTO preciodiamantequilate( fiidpreciodiamante,fiidtipodiamante,fnprecio,fiidmoneda,fiidestatus,fcusuariomodifico, fdfechamodificacion) values(-547,48,1,2,1,'sys','20150101');
INSERT INTO preciodiamantequilate( fiidpreciodiamante,fiidtipodiamante,fnprecio,fiidmoneda,fiidestatus,fcusuariomodifico, fdfechamodificacion) values(-548,49,1,2,1,'sys','20150101');
INSERT INTO preciodiamantequilate( fiidpreciodiamante,fiidtipodiamante,fnprecio,fiidmoneda,fiidestatus,fcusuariomodifico, fdfechamodificacion) values(-549,50,1,2,1,'sys','20150101');
INSERT INTO preciodiamantequilate( fiidpreciodiamante,fiidtipodiamante,fnprecio,fiidmoneda,fiidestatus,fcusuariomodifico, fdfechamodificacion) values(-550,51,1,2,1,'sys','20150101');
INSERT INTO preciodiamantequilate( fiidpreciodiamante,fiidtipodiamante,fnprecio,fiidmoneda,fiidestatus,fcusuariomodifico, fdfechamodificacion) values(-551,52,1,2,1,'sys','20150101');
INSERT INTO preciodiamantequilate( fiidpreciodiamante,fiidtipodiamante,fnprecio,fiidmoneda,fiidestatus,fcusuariomodifico, fdfechamodificacion) values(-552,53,1,2,1,'sys','20150101');
INSERT INTO preciodiamantequilate( fiidpreciodiamante,fiidtipodiamante,fnprecio,fiidmoneda,fiidestatus,fcusuariomodifico, fdfechamodificacion) values(-553,54,1,2,1,'sys','20150101');
INSERT INTO preciodiamantequilate( fiidpreciodiamante,fiidtipodiamante,fnprecio,fiidmoneda,fiidestatus,fcusuariomodifico, fdfechamodificacion) values(-554,55,1,2,1,'sys','20150101');
INSERT INTO preciodiamantequilate( fiidpreciodiamante,fiidtipodiamante,fnprecio,fiidmoneda,fiidestatus,fcusuariomodifico, fdfechamodificacion) values(-555,56,1,2,1,'sys','20150101');
INSERT INTO preciodiamantequilate( fiidpreciodiamante,fiidtipodiamante,fnprecio,fiidmoneda,fiidestatus,fcusuariomodifico, fdfechamodificacion) values(-556,57,1,2,1,'sys','20150101');
INSERT INTO preciodiamantequilate( fiidpreciodiamante,fiidtipodiamante,fnprecio,fiidmoneda,fiidestatus,fcusuariomodifico, fdfechamodificacion) values(-557,58,1,2,1,'sys','20150101');
INSERT INTO preciodiamantequilate( fiidpreciodiamante,fiidtipodiamante,fnprecio,fiidmoneda,fiidestatus,fcusuariomodifico, fdfechamodificacion) values(-558,59,1,2,1,'sys','20150101');
INSERT INTO preciodiamantequilate( fiidpreciodiamante,fiidtipodiamante,fnprecio,fiidmoneda,fiidestatus,fcusuariomodifico, fdfechamodificacion) values(-559,60,1,2,1,'sys','20150101');
INSERT INTO preciodiamantequilate( fiidpreciodiamante,fiidtipodiamante,fnprecio,fiidmoneda,fiidestatus,fcusuariomodifico, fdfechamodificacion) values(-560,61,1,2,1,'sys','20150101');
INSERT INTO preciodiamantequilate( fiidpreciodiamante,fiidtipodiamante,fnprecio,fiidmoneda,fiidestatus,fcusuariomodifico, fdfechamodificacion) values(-561,62,1,2,1,'sys','20150101');
