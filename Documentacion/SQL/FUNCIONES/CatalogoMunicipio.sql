CREATE OR REPLACE FUNCTION public.conCatalogoMunicipio(idMunicipio integer)
  RETURNS SETOF catalogomunicipio
  
AS
$BODY$
BEGIN
	
	RETURN QUERY 
				SELECT *
				FROM public.catalogoMunicipio
				WHERE fiIdMunicipio=idMunicipio;

END;
$BODY$
LANGUAGE plpgsql;

/
CREATE OR REPLACE FUNCTION public.conCatalogoMunicipioPorEstado(idEstado integer)
  RETURNS SETOF catalogomunicipio
  
AS
$BODY$
BEGIN
	
	RETURN QUERY 
				SELECT *
				FROM public.catalogoMunicipio
				WHERE fiIdEstado=idEstado;

END;
$BODY$
LANGUAGE plpgsql;

/
CREATE OR REPLACE FUNCTION public.inscatalogomunicipio(idestado integer, nombre varchar, usuariomodifico varchar)
  RETURNS integer
  LANGUAGE plpgsql
AS
$body$
DECLARE
  fecha timestamp;
  secuencia int;
BEGIN
			  fecha = current_timestamp;
				secuencia= nextval('sec_direccion') ;
		
				INSERT INTO catalogomunicipio
				(
					fiidmunicipio,
					fiidestado,
				  fcnombre,
				  fdfechamodificacion,
				  fcusuariomodifico
				)
				VALUES
				(
				  secuencia,
				  idestado,
				  nombre,
				  fecha,
				  usuariomodifico
				);

				RETURN secuencia;
END;
$body$
LANGUAGE plpgsql;
/

commit;