CREATE OR REPLACE FUNCTION public.conCatalogoColiniaPorID(idColonia integer)
  RETURNS SETOF catalogocolonia
  
AS
$BODY$
BEGIN
	
	RETURN QUERY 
				SELECT *
				FROM public.catalogocolonia
				WHERE fiIdcolonia=idColonia;

END;
$BODY$
LANGUAGE plpgsql;
/

CREATE OR REPLACE FUNCTION public.conCatalogoColiniaPorIdCP(idCodigoPostal integer)
  RETURNS SETOF catalogocolonia
  
AS
$BODY$
BEGIN
	
	RETURN QUERY 
				SELECT c.*
				 FROM public.catalogocolonia c			
				WHERE c.fiIdCodigoPostal=idCodigoPostal;

END;
$BODY$
LANGUAGE plpgsql;
/

CREATE OR REPLACE FUNCTION public.conCatalogoColiniaPorCP(codigoPostal varchar(6))
  RETURNS SETOF catalogocolonia
  
AS
$BODY$
BEGIN
	
	RETURN QUERY 
				SELECT c.*
				 FROM public.catalogocolonia c
				 JOIN public.catalogocodigopostal cp on cp.fiidcodigostal=c.fiidcodigopostal
				WHERE cp.fcCodiPostal=codigoPostal;

END;
$BODY$
LANGUAGE plpgsql;
/

CREATE OR REPLACE FUNCTION public.inscatalogocolonia(idcodigopostal integer, idmunicipio integer, idestado integer, nombre varchar, idestatus integer, usuariomodifico varchar)
  RETURNS integer

AS
$body$
DECLARE
  fecha timestamp;
  secuencia int;
BEGIN
			  fecha = current_timestamp;
				secuencia= nextval('sec_direccion') ;
		
				INSERT INTO catalogocolonia
				(
					fiidcolonia,
					fiidcodigopostal,
					fiidmunicipio,
					fiidestado,
					fcnombre,
					fiidestatus,
				  fdfechamodificacion,
				  fcusuariomodifico
				)
				VALUES
				(
				  secuencia,
				  idcodigopostal,
				  idmunicipio,
				  idestado,
				  nombre,
				  idestatus,
				  fecha,
				  usuariomodifico
				);

				RETURN secuencia;
END;
$body$
LANGUAGE plpgsql;
/

