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
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import clases.login.QualifierUsuarioSesionTienda;
import clases.login.UsuarioSesion;
import clases.login.UsuarioSesionTienda;
import clases.persistence.jpa.factory.qualifier.MetalesEM;
import clases.vo.cliente.Estado;
import clases.vo.cliente.Municipio;
import ejb.bussines.exception.RDNException;

@Stateless
@LocalBean

public class MunicipioEJB {
	
	private static final Logger log = LogManager .getLogger(MunicipioEJB.class);
	
	@Inject @MetalesEM
	private EntityManager metalesEM;
	
	@Inject @QualifierUsuarioSesionTienda
	private UsuarioSesion usuarioSesion;
	
	@Resource
	private SessionContext context;
	
	public MunicipioEJB() {}

	public Municipio altaMunicipio(Municipio municipio) throws RDNException, Exception {
		try {
			log.info("Alta de Municipio");
			
			UsuarioSesionTienda usuarioTienda = (UsuarioSesionTienda) this.usuarioSesion;
    		
    			if (usuarioTienda.getTienda() == null || usuarioTienda.getTienda().getId() <= 0) {
    				throw new Exception("La tienda en sesion es invalida, no se puede dar de alta al Municipio");
    			}
    			
    			log.info("Tienda de sesion correcta, ID: "+usuarioTienda.getTienda().getId());
        		log.info("Se manda a validar la informacion del Municipio");
        		
        		this.validarInformacionAlta(municipio);
        		
        		this.metalesEM.persist(municipio);
        		
        		log.info("alta de estado exitosa");
        		
        		return municipio;
    		
		} catch (Exception e) {
			if (!(e  instanceof RDNException)) {
	    			log.error("Error al dar de alta el Municipio",e);
	    		}
	    		this.context.setRollbackOnly();
	    		throw e;
		}
	}
	
	public Municipio find(int id) {
		return metalesEM.find(Municipio.class, id);
	}
	
	public boolean validarInformacionAlta(Municipio municipio) throws Exception {
		log.info("Se validan los parametros");
		
	    	if (municipio == null) {
	    		throw new InvalidParameterException("El parametro municipio es nulo"); 
	    	}
	    	
	    	if ( municipio.getNombre() == null) {
	    	    	throw new InvalidParameterException("El campo de nombre es obligarorio");
	    	}
	    	
	    	if (municipio.getEstado() == null) {
	    		throw new InvalidParameterException("El campo de estado es obligarorio");
	    	}
	    	
	    	return true;
	}
	
	public List<Municipio> obtenerMunicipiosPorEstado(Estado estado) throws RDNException, Exception {
    	
    	List<Municipio> listaMunicipios = null;
    	
    	try {
    		log.info("Recuperando la lista de municipios");
    		
    		TypedQuery<Municipio> query=null;
    		boolean sinResultados= false;
    		
    		try {
	        	StringBuilder consulta= new StringBuilder("SELECT M FROM Municipio M where M.estado = :estado");
	    		query = metalesEM.createQuery( consulta.toString(), Municipio.class);
	    		query.setParameter("estado", estado);
	    	    	 
	    		listaMunicipios = query.getResultList();
	    	    	 
	    		} catch(javax.persistence.NoResultException nre){
	    			sinResultados=true;
	    		}
	    		if (listaMunicipios==null || sinResultados || listaMunicipios.size()==0){
	    			return null;
	    		}
    		
	    		log.info("Lista de clientes recuperados: " + listaMunicipios.size());
	    		return listaMunicipios;
	    		
		} catch (Exception e) {
				log.error("Error al buscar el cliente",e);
				throw e;
		}
	}
	
	public List<Municipio> obtenerMunicipiosPorEstadoYNombre(Estado estado, String nombreMunicipio) throws RDNException, Exception {
    	
    	List<Municipio> listaMunicipios = null;
    	
    	try {
    		log.info("Recuperando la lista de municipios");
    		
    		TypedQuery<Municipio> query=null;
    		boolean sinResultados= false;
    		
    		try {
	        	StringBuilder consulta= new StringBuilder("SELECT M FROM Municipio M where M.estado = :estado and lower(M.nombre)  LIKE :name");
	    		query = metalesEM.createQuery( consulta.toString(), Municipio.class);
	    		query.setParameter("estado", estado);
	    		query.setParameter("name","%" + nombreMunicipio.toLowerCase() + "%");
	    		listaMunicipios = query.getResultList();
	    	    	 
	    		} catch(javax.persistence.NoResultException nre){
	    			sinResultados=true;
	    		}
	    		if (listaMunicipios==null || sinResultados || listaMunicipios.size()==0){
	    			return null;
	    		}
    		
	    		log.info("Lista de municipios recuperados: " + listaMunicipios.size());
	    		return listaMunicipios;
	    		
		} catch (Exception e) {
				log.error("Error al buscar el municipio",e);
				throw e;
		}
	}

}
