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

import clases.login.QualifierUsuarioSesionTienda;
import clases.login.UsuarioSesion;
import clases.login.UsuarioSesionTienda;
import clases.persistence.jpa.factory.qualifier.MetalesEM;
import clases.vo.cliente.CodigoPostal;
import clases.vo.cliente.Colonia;
import clases.vo.cliente.Municipio;
import ejb.bussines.exception.RDNException;

@Stateless
@LocalBean
public class CodigoPostalEJB {
private static final Logger log = LogManager .getLogger(CodigoPostalEJB.class);
	
	@Inject @MetalesEM
	private EntityManager metalesEM;
	
	@Inject @QualifierUsuarioSesionTienda
	private UsuarioSesion usuarioSesion;
	
	@Resource
	private SessionContext context;
	
	public CodigoPostalEJB() {}

	public CodigoPostal altaCodigo(CodigoPostal codigo) throws RDNException, Exception {
		try {
			log.info("Alta de Codigo");
			
			UsuarioSesionTienda usuarioTienda = (UsuarioSesionTienda) this.usuarioSesion;
    		
    		
    			if (usuarioTienda.getTienda() == null || usuarioTienda.getTienda().getId() <= 0) {
    				throw new Exception("La tienda en sesion es invalida, no se puede dar de alta a la codigo");
    			}
    			
    			log.info("Tienda de sesion correcta, ID: "+usuarioTienda.getTienda().getId());
        		log.info("Se manda a validar la informacion de la codigo");
        		
        		this.validarInformacionAlta(codigo);
        		
        		this.metalesEM.persist(codigo);
        		
        		log.info("alta de codigo postal exitosa");
        		
        		return codigo;
    		
		} catch (Exception e) {
			if (!(e  instanceof RDNException)) {
	    			log.error("Error al dar de alta la codigo",e);
	    		}
	    		this.context.setRollbackOnly();
	    		throw e;
		}
	}
	
	public boolean validarInformacionAlta(CodigoPostal codigo) throws Exception {
		log.info("Se validan los parametros");
		
	    	if (codigo == null) {
	    		throw new InvalidParameterException("El parametro colonia es nulo"); 
	    	}
	    	
	    	if ( codigo.getCodigoPostal() == null) {
	    	    	throw new InvalidParameterException("El campo de nombre es obligarorio");
	    	}
	    	
	    	if (codigo.getMunicipio() == null) {
	    		throw new InvalidParameterException("El campo de codigo es obligarorio");
	    	}
	    	
	    	return true;
	}
	
	public List<CodigoPostal> obtenerCodigoPorMunicipio(Municipio municipio) throws RDNException, Exception {
    	
    	List<CodigoPostal> listaCodigos = null;
    	
    	try {
    		log.info("Recuperando la lista de colonias");
    		
    		TypedQuery<CodigoPostal> query=null;
    		boolean sinResultados= false;
    		
    		try {
	        	StringBuilder consulta= new StringBuilder("SELECT C FROM CodigoPostal C where C.municipio = :municipio");
	    		query = metalesEM.createQuery( consulta.toString(), CodigoPostal.class);
	    		query.setParameter("municipio", municipio);
	    	    	 
	    		listaCodigos = query.getResultList();
	    	    	 
	    		} catch(javax.persistence.NoResultException nre){
	    			sinResultados=true;
	    		}
	    		if (listaCodigos==null || sinResultados || listaCodigos.size()==0){
	    			return null;
	    		}
    		
	    		log.info("Lista de clientes recuperados: " + listaCodigos.size());
	    		return listaCodigos;
	    		
		} catch (Exception e) {
				log.error("Error al buscar el cliente",e);
				throw e;
		}
	}
	
	public List<CodigoPostal> obtenerCodigosPorMunicipioYCodigo(Municipio municipio, String codigoPostal) throws RDNException, Exception {
    	
    	List<CodigoPostal> listaCodigos = null;
    	
    	try {
    		log.info("Recuperando la lista de codigos");
    		
    		TypedQuery<CodigoPostal> query=null;
    		boolean sinResultados= false;
    		
    		try {
	        	StringBuilder consulta= new StringBuilder("SELECT C FROM CodigoPostal C where C.municipio = :municipio and lower(C.codigoPostal)  LIKE :codigo");
	    		query = metalesEM.createQuery( consulta.toString(), CodigoPostal.class);
	    		query.setParameter("municipio", municipio);
	    		query.setParameter("codigo","%" + codigoPostal.toLowerCase() + "%");
	    		listaCodigos = query.getResultList();
	    	    	 
	    		} catch(javax.persistence.NoResultException nre){
	    			sinResultados=true;
	    		}
	    		if (listaCodigos==null || sinResultados || listaCodigos.size()==0){
	    			return null;
	    		}
    		
	    		log.info("Lista de colonias recuperados: " + listaCodigos.size());
	    		return listaCodigos;
	    		
		} catch (Exception e) {
				log.error("Error al buscar la colonia",e);
				throw e;
		}
	}

}

