package ejb.bussines.administracion;

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import clases.login.UsuarioSesion;
import clases.persistence.jpa.factory.qualifier.MetalesEM;
import clases.vo.tienda.Tienda;
import ejb.bussines.exception.RDNException;

/**
 * Session Bean implementation class Tienda
 */
@Stateless
@LocalBean
public class TiendaEJB {

  
	
	@Inject
	private UsuarioSesion usuarioSesion;
	
	@Inject @MetalesEM
	private EntityManager metalesEM;
	
	@Resource
	private SessionContext context;
	
	private static final Logger log = LogManager .getLogger(TiendaEJB.class);
	
	
	
    public TiendaEJB() {
        // TODO Auto-generated constructor stub
    }
    
    
    public List<Tienda> recuperarTindasActivas() throws RDNException, Exception{
    	
    	
    	try{
    		
    		
    		log.info("Se proce recuperar las tiendas activas");
    			TypedQuery<Tienda> query=null;
        		
        		try{
        			
        			List<Tienda>  tiendas=null;
        			
        			 query= metalesEM.createQuery("SELECT tienda FROM Tienda tienda where tienda.estatus.id=2" ,Tienda.class);
        	    	
        			 tiendas=query.getResultList();
        	    	 
        			 log.info("Numero de tiendas recuperadas: "+tiendas.size());
        			 
        			 return tiendas;
        			 
        		}catch(javax.persistence.NoResultException nre){
        			throw new RDNException("No se encontron tiendas activas");
        		}
        		
    		
    		
    	}catch(RDNException e){
    		this.context.setRollbackOnly();
    		throw e;
    	}catch(Exception e){
    		e= new Exception("Error al recuperar las cajas de la tienda.",e);
    		log.error(e,e);
    		this.context.setRollbackOnly();
    		throw e;
    	}
    	
    }

}
