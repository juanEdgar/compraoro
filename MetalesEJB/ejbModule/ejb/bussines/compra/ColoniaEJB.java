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
import clases.vo.cliente.Colonia;
import clases.vo.cliente.Estado;
import clases.vo.cliente.Municipio;
import ejb.bussines.exception.RDNException;

@Stateless
@LocalBean
public class ColoniaEJB {
private static final Logger log = LogManager .getLogger(ColoniaEJB.class);
	
	@Inject @MetalesEM
	private EntityManager metalesEM;
	
	@Inject @QualifierUsuarioSesionTienda
	private UsuarioSesion usuarioSesion;
	
	@Resource
	private SessionContext context;
	
	public ColoniaEJB() {}

	public Colonia altaColonia(Colonia colonia) throws RDNException, Exception {
		try {
			log.info("Alta de Colonia");
			
			UsuarioSesionTienda usuarioTienda = (UsuarioSesionTienda) this.usuarioSesion;
    		
    		
    			if (usuarioTienda.getTienda() == null || usuarioTienda.getTienda().getId() <= 0) {
    				throw new Exception("La tienda en sesion es invalida, no se puede dar de alta a la Colonia");
    			}
    			
    			log.info("Tienda de sesion correcta, ID: "+usuarioTienda.getTienda().getId());
        		log.info("Se manda a validar la informacion de la Colonia");
        		
        		this.validarInformacionAlta(colonia);
        		
        		this.metalesEM.persist(colonia);
        		
        		log.info("alta de colonia exitosa");
        		
        		return colonia;
    		
		} catch (Exception e) {
			if (!(e  instanceof RDNException)) {
	    			log.error("Error al dar de alta la Colonia",e);
	    		}
	    		this.context.setRollbackOnly();
	    		throw e;
		}
	}
	
	public Colonia find(int id) {
		return metalesEM.find(Colonia.class, id);
	}
	
	public boolean validarInformacionAlta(Colonia colonia) throws Exception {
		log.info("Se validan los parametros");
		
	    	if (colonia == null) {
	    		throw new InvalidParameterException("El parametro colonia es nulo"); 
	    	}
	    	
	    	if ( colonia.getNombre() == null) {
	    	    	throw new InvalidParameterException("El campo de nombre es obligarorio");
	    	}
	    	
	    	if (colonia.getMunicipio() == null) {
	    		throw new InvalidParameterException("El campo de municipio es obligarorio");
	    	}
	    	
	    	return true;
	}
	
	public List<Colonia> obtenerColoniaPorMunicipio(Municipio municipio) throws RDNException, Exception {
    	
    	List<Colonia> listaColonias = null;
    	
    	try {
    		log.info("Recuperando la lista de colonias");
    		
    		TypedQuery<Colonia> query=null;
    		boolean sinResultados= false;
    		
    		try {
	        	StringBuilder consulta= new StringBuilder("SELECT C FROM Colonia C where C.municipio = :municipio");
	    		query = metalesEM.createQuery( consulta.toString(), Colonia.class);
	    		query.setParameter("municipio", municipio);
	    	    	 
	    		listaColonias = query.getResultList();
	    	    	 
	    		} catch(javax.persistence.NoResultException nre){
	    			sinResultados=true;
	    		}
	    		if (listaColonias==null || sinResultados || listaColonias.size()==0){
	    			return null;
	    		}
    		
	    		log.info("Lista de clientes recuperados: " + listaColonias.size());
	    		return listaColonias;
	    		
		} catch (Exception e) {
				log.error("Error al buscar el cliente",e);
				throw e;
		}
	}
	
	public List<Colonia> obtenerColoniasPorMunicipioYNombre(Municipio municipio, String nombreColonia) throws RDNException, Exception {
    	
    	List<Colonia> listaColonias = null;
    	
    	try {
    		log.info("Recuperando la lista de colonias");
    		
    		TypedQuery<Colonia> query=null;
    		boolean sinResultados= false;
    		
    		try {
	        	StringBuilder consulta= new StringBuilder("SELECT C FROM Colonia C where C.municipio = :municipio and lower(C.nombre)  LIKE :name");
	    		query = metalesEM.createQuery( consulta.toString(), Colonia.class);
	    		query.setParameter("municipio", municipio);
	    		query.setParameter("name","%" + nombreColonia.toLowerCase() + "%");
	    		listaColonias = query.getResultList();
	    	    	 
	    		} catch(javax.persistence.NoResultException nre){
	    			sinResultados=true;
	    		}
	    		if (listaColonias==null || sinResultados || listaColonias.size()==0){
	    			return null;
	    		}
    		
	    		log.info("Lista de colonias recuperados: " + listaColonias.size());
	    		return listaColonias;
	    		
		} catch (Exception e) {
				log.error("Error al buscar la colonia",e);
				throw e;
		}
	}

}

