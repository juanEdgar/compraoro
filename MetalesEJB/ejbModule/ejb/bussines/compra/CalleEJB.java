package ejb.bussines.compra;

import java.security.InvalidParameterException;
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
import clases.vo.cliente.Calle;
import clases.vo.cliente.Colonia;
import ejb.bussines.exception.RDNException;

@Stateless
@LocalBean
public class CalleEJB {
private static final Logger log = LogManager .getLogger(CalleEJB.class);
	
	@Inject @MetalesEM
	private EntityManager metalesEM;
	
	@Inject 
	private UsuarioSesion usuarioSesion;
	
	@Resource
	private SessionContext context;
	
	public CalleEJB() {}

	public Calle altaCalle(Calle calle) throws RDNException, Exception {
		try {
			log.info("Alta de Calle");
			
			UsuarioSesion usuarioTienda =  this.usuarioSesion;
    		
    			if (usuarioTienda.getTienda() == null || usuarioTienda.getTienda().getId() <= 0) {
    				throw new Exception("La tienda en sesion es invalida, no se puede dar de alta");
    			}
    			
    			log.info("Tienda de sesion correcta, ID: "+usuarioTienda.getTienda().getId());
        		log.info("Se manda a validar la informacion");
        		
        		this.validarInformacionAlta(calle);
        		
        		this.metalesEM.persist(calle);
        		
        		log.info("alta exitosa");
        		
        		return calle;
    		
		} catch (Exception e) {
			if (!(e  instanceof RDNException)) {
	    			log.error("Error al dar de alta la Calle",e);
	    		}
	    		this.context.setRollbackOnly();
	    		throw e;
		}
	}
	
	public Calle find(int id) {
		return metalesEM.find(Calle.class, id);
	}
	
	public boolean validarInformacionAlta(Calle calle) throws Exception {
		log.info("Se validan los parametros");
		
	    	if (calle == null) {
	    		throw new InvalidParameterException("El parametro calle es nulo"); 
	    	}
	    	
	    	if ( calle.getNombre() == null) {
	    	    	throw new InvalidParameterException("El campo de nombre es obligarorio");
	    	}
	    	
	    	if (calle.getColonia() == null) {
	    		throw new InvalidParameterException("El campo de colonia es obligarorio");
	    	}
	    	
	    	return true;
	}
	
	public List<Calle> obtenerCallePorColonia(Colonia colonia) throws RDNException, Exception {
    	
    	List<Calle> listaCalles = null;
    	
    	try {
    		log.info("Recuperando la lista de calles");
    		
    		TypedQuery<Calle> query=null;
    		boolean sinResultados= false;
    		
    		try {
	        	StringBuilder consulta= new StringBuilder("SELECT C FROM Calle C where C.colonia = :colonia");
	    		query = metalesEM.createQuery( consulta.toString(), Calle.class);
	    		query.setParameter("colonia", colonia);
	    	    	 
	    		listaCalles = query.getResultList();
	    	    	 
	    		} catch(javax.persistence.NoResultException nre){
	    			sinResultados=true;
	    		}
	    		if (listaCalles==null || sinResultados || listaCalles.size()==0){
	    			return null;
	    		}
    		
	    		log.info("Lista de clientes recuperados: " + listaCalles.size());
	    		return listaCalles;
	    		
		} catch (Exception e) {
				log.error("Error al buscar el cliente",e);
				throw e;
		}
	}
	
	public List<Calle> obtenerCallesPorColoniaYNombre(Colonia colonia, String nombreCalle) throws RDNException, Exception {
    	
    	List<Calle> listaCalles = null;
    	
    	try {
    		log.info("Recuperando la lista de calles");
    		
    		TypedQuery<Calle> query=null;
    		boolean sinResultados= false;
    		
    		try {
	        	StringBuilder consulta= new StringBuilder("SELECT C FROM Calle C where C.colonia = :colonia and lower(C.nombre)  LIKE :name");
	    		query = metalesEM.createQuery( consulta.toString(), Calle.class);
	    		query.setParameter("colonia", colonia);
	    		query.setParameter("name","%" + nombreCalle.toLowerCase() + "%");
	    		listaCalles = query.getResultList();
	    	    	 
	    		} catch(javax.persistence.NoResultException nre){
	    			sinResultados=true;
	    		}
	    		if (listaCalles==null || sinResultados || listaCalles.size()==0){
	    			return null;
	    		}
    		
	    		log.info("Lista de colonias recuperados: " + listaCalles.size());
	    		return listaCalles;
	    		
	    		
		} catch (Exception e) {
				log.error("Error al buscar la colonia",e);
				throw e;
		}
	}

}


