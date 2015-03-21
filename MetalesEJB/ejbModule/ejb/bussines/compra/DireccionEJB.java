package ejb.bussines.compra;

import java.security.InvalidParameterException;

import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import clases.login.QualifierUsuarioSesionTienda;
import clases.login.UsuarioSesion;
import clases.login.UsuarioSesionTienda;
import clases.persistence.jpa.factory.qualifier.MetalesEM;
import clases.vo.cliente.Direccion;
import ejb.bussines.exception.RDNException;

@Stateless
@LocalBean
public class DireccionEJB {
private static final Logger log = LogManager .getLogger(DireccionEJB.class);
	
	@Inject @MetalesEM
	private EntityManager metalesEM;
	
	@Inject @QualifierUsuarioSesionTienda
	private UsuarioSesion usuarioSesion;
	
	@Resource
	private SessionContext context;
	
	public DireccionEJB() {}

	public Direccion altaDireccion(Direccion direccion) throws RDNException, Exception {
		try {
			log.info("Alta de Direccion");
			
			UsuarioSesionTienda usuarioTienda = (UsuarioSesionTienda) this.usuarioSesion;
    		
    			if (usuarioTienda.getTienda() == null || usuarioTienda.getTienda().getId() <= 0) {
    				throw new Exception("La tienda en sesion es invalida, no se puede dar de alta a la codigo");
    			}
    			
    			log.info("Tienda de sesion correcta, ID: "+usuarioTienda.getTienda().getId());
        		log.info("Se manda a validar la informacion de la codigo");
        		
        		this.validarInformacionAlta(direccion);
        		
        		this.metalesEM.persist(direccion);
        		
        		log.info("alta de estado exitosa");
        		
        		return direccion;
    		
		} catch (Exception e) {
			if (!(e  instanceof RDNException)) {
	    			log.error("Error al dar de alta la codigo",e);
	    		}
	    		this.context.setRollbackOnly();
	    		throw e;
		}
	}
	
	public boolean validarInformacionAlta(Direccion direccion) throws Exception {
		log.info("Se validan los parametros");
		
	    	if (direccion == null) {
	    		throw new InvalidParameterException("El parametro colonia es nulo"); 
	    	}
	    	
	    	if ( direccion.getCodigo() == null) {
	    	    	throw new InvalidParameterException("El campo de codigo es obligarorio");
	    	}
	    	
	    	if (direccion.getCalle() == null) {
	    		throw new InvalidParameterException("El campo de calle es obligarorio");
	    	}
	    	
	    	if (direccion.getColonia() == null) {
	    		throw new InvalidParameterException("El campo de colonia es obligarorio");
	    	}
	    	
	    	if (direccion.getNumeroExterior() == null || direccion.getNumeroExterior().length() < 1) {
	    		throw new InvalidParameterException("El campo de numero exterior es obligarorio");
	    	}
	    	
	    	return true;
	}

}
