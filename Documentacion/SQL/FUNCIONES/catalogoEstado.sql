CREATE OR REPLACE FUNCTION public.concatalogoestado()
  RETURNS SETOF catalogoestado

AS
$body$
BEGIN
	
	RETURN QUERY 
				SELECT *
				FROM public.catalogoEstado;				

END;
$body$
LANGUAGE plpgsql;

/

CREATE OR REPLACE FUNCTION public.concatalogoestado(idestado integer)
  RETURNS SETOF catalogoestado

AS
$body$
BEGIN
	
	RETURN QUERY 
				SELECT *
				FROM public.catalogoEstado
				WHERE fiIdEstado=idEstado;

END;
$body$
LANGUAGE plpgsql;

/

CREATE OR REPLACE FUNCTION public.inscatalogoestado(nombre varchar, usuariomodifico varchar)
  RETURNS integer
AS
$body$
DECLARE
  fecha timestamp;
  secuencia int;
BEGIN
			  fecha = current_timestamp;
				secuencia= nextval('sec_direccion') ;
		
				INSERT INTO catalogoestado
				(
					fiidestado,
				  fcnombre,
				  fdfechamodificacion,
				  fcusuariomodifico
				)
				VALUES
				(
				  secuencia,
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