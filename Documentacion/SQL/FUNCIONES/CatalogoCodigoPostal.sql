CREATE OR REPLACE FUNCTION public.conCatalogoCodigoPostalPorMunicipio(idMunicipio integer)
  RETURNS SETOF catalogocodigopostal
  
AS
$BODY$
BEGIN
	
	RETURN QUERY 
				SELECT *
				FROM public.catalogocodigopostal
				WHERE fiIdMunicipio=idMunicipio;

END;
$BODY$
LANGUAGE plpgsql;

/

CREATE OR REPLACE FUNCTION public.conCatalogoCodigoPostalPorCP(codigoPostal char(6))
  RETURNS SETOF catalogocodigopostal
  
AS
$BODY$
BEGIN
	
	RETURN QUERY 
				SELECT *
				FROM public.catalogocodigopostal
				WHERE fcCodigoPostal=codigoPostal;

END;
$BODY$
LANGUAGE plpgsql;

/
CREATE OR REPLACE FUNCTION public.conCatalogoCodigoPostalPorID(idCodigoPostal integer)
  RETURNS SETOF catalogocodigopostal
  
AS
$BODY$
BEGIN
	
	RETURN QUERY 
				SELECT *
				FROM public.catalogocodigopostal
				WHERE fiIdcodigoPostal=idCodigoPostal;

END;
$BODY$
LANGUAGE plpgsql;

/

CREATE OR REPLACE FUNCTION public.inscatalogocodigopostal(codigopostal character, idmunicipio integer, idestado integer, usuariomodifico varchar)
  RETURNS integer
  
AS
$body$
DECLARE
  fecha timestamp;
  secuencia int;
BEGIN
			  fecha = current_timestamp;
				secuencia= nextval('sec_direccion') ;
		
				INSERT INTO catalogocodigopostal
				(
					fiidcodigopostal,
					fccodigopostal,
					fiidmunicipio,
					fiidestado,
				  fdfechamodificacion,
				  fcusuariomodifico
				)
				VALUES
				(
				  secuencia,
				  codigopostal,
				  idmunicipio,
				  idestado,
				  fecha,
				  usuariomodifico
				);

				RETURN secuencia;
END;
$body$
LANGUAGE plpgsql;
/
