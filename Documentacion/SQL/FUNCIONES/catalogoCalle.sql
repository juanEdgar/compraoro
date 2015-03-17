CREATE OR REPLACE FUNCTION public.conCatalogoCallePorIdColonia(idColonia integer)
  RETURNS SETOF catalogocalle
  
AS
$BODY$
BEGIN
	
	RETURN QUERY 
				    SELECT *
				      FROM public.catalogocalle calle 
				     WHERE calle.fiIdcolonia=idColonia;

END;
$BODY$
LANGUAGE plpgsql;
/

CREATE OR REPLACE FUNCTION public.inscatalogocalle(idcolonia integer, idcodigopostal integer, idmunicipio integer, idestado integer, calle varchar, idestatus integer, usuariomodifico varchar)
  RETURNS void
 
AS
$body$
DECLARE
  fecha timestamp;
BEGIN
			  fecha = current_timestamp;
		
				INSERT INTO catalogocalle
				(
					fiidcolonia,
					fiidcodigopostal,
					fiidmunicipio,
					fiidestado,
					fccalle,
					fiidestatus,
				  fdfechamodificacion,
				  fcusuariomodifico
				)
				VALUES
				(
				  idcolonia,
				  idcodigopostal,
				  idmunicipio,
				  idestado,
				  calle,
				  idestatus,
				  fecha,
				  usuariomodifico
				);

END;
$body$
LANGUAGE plpgsql;
/