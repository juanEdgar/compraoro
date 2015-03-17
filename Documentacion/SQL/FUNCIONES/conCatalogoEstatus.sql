CREATE OR REPLACE FUNCTION public.conCatalogoEstatus(idEstatus integer)
  RETURNS SETOF catalogoestatus
  
AS
$BODY$
BEGIN
	
	RETURN QUERY 
				SELECT *
				FROM public.catalogoEstatus
				WHERE fiIdEstatus=idEstatus;

END;
$BODY$
LANGUAGE plpgsql;
/

CREATE OR REPLACE FUNCTION public.updCatalogoEstatus(idEstatus integer, idGrupoEstatus int, descripcion varchar(100),usuario varchar(20))
  RETURNS VOID
AS
$BODY$
DECLARE
  fecha timestamp;
BEGIN
	 
			  fecha = current_timestamp;

				UPDATE public.catalogoEstatus
					SET fiidgrupoestatus=idGrupoEstatus,
					    fcdescripcion=descripcion,
					    fcusuariomodifico=usuario,
					     fdfechamodificacion=fecha
				WHERE fiIdEstatus=idEstatus;
	
END;
$BODY$
LANGUAGE plpgsql;
/

CREATE OR REPLACE FUNCTION public.insCatalogoGrupoEstatus(descripcion varchar(100),usuario varchar(20))
  RETURNS VOID
AS
$BODY$
DECLARE
  fecha timestamp;
  secuencia int;
BEGIN
	 
			  fecha = current_timestamp;
				secuencia= nextval('sec_grupoestatus') ;
			
				INSERT INTO public.catalogoGrupoEstatus
        VALUES(secuencia,descripcion,usuario,fecha);					


END;
$BODY$
LANGUAGE plpgsql;
/
commit;