package ejb.bussines.administracion;

import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import clases.login.QualifierUsuarioSesion;
import clases.login.UsuarioSesion;
import clases.persistence.jpa.factory.qualifier.MetalesEM;
import ejb.bussines.venta.CotizadorEJB;

/**
 * Session Bean implementation class PreciosEJB
 */
@Stateless
@LocalBean
public class PreciosEJB {

	@Inject @MetalesEM
	private EntityManager metalesEM;
	
	@Resource
	private SessionContext context;
	
	private static final Logger log = LogManager .getLogger(PreciosEJB.class);
	
	@Inject @QualifierUsuarioSesion
	private UsuarioSesion usuarioSesion;
	
	
    public PreciosEJB() {
        // TODO Auto-generated constructor stub
    }

}
