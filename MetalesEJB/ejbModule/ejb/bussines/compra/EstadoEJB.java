package ejb.bussines.compra;

import java.security.InvalidParameterException;
import java.util.ArrayList;
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
import clases.vo.cliente.Estado;
import ejb.bussines.exception.RDNException;

@Stateless
@LocalBean
public class EstadoEJB {
	
	private static final Logger log = LogManager .getLogger(EstadoEJB.class);
	
	@Inject @MetalesEM
	private EntityManager metalesEM;
	
	@Inject 
	private UsuarioSesion usuarioSesion;
	
	@Resource
	private SessionContext context;
	
	public EstadoEJB() {}

	public Estado altaEstado(Estado estado) throws RDNException, Exception {
		try {
			log.info("Alta de estado");
			
			UsuarioSesion usuarioTienda =  this.usuarioSesion;
    		
    		
    			if (usuarioTienda.getTienda() == null || usuarioTienda.getTienda().getId() <= 0) {
    				throw new Exception("La tienda en sesion es invalida, no se puede dar de alta al estado");
    			}
    			
    			log.info("Tienda de sesion correcta, ID: "+usuarioTienda.getTienda().getId());
        		log.info("Se manda a validar la informacion del estado");
        		
        		this.validarInformacionAlta(estado);
        		
        		this.metalesEM.persist(estado);
        		
        		log.info("alta de estado exitosa");
        		
        		return estado;
    		
		} catch (Exception e) {
			if (!(e  instanceof RDNException)) {
	    			log.error("Error al dar de alta el estado",e);
	    		}
	    		this.context.setRollbackOnly();
	    		throw e;
		}
	}
	
	public Estado find(int id) {
		return metalesEM.find(Estado.class, id);
	}
	
	public boolean validarInformacionAlta(Estado estado) throws Exception {
		log.info("Se validan los parametros");
	    	if(estado == null){
	    		throw new InvalidParameterException("El parametro estado es nulo"); 
	    	}
	    	if ( estado.getNombre() == null){
	    	    		throw new InvalidParameterException("El campo de nombre es obligarorio");
	    	    	}
	    	
	    	return true;
	}
	
	public List<Estado> obtenerEstados() throws RDNException, Exception {
	    	
	    	List<Estado> listaEstados = null;
	    	
	    	try {
	    		log.info("Recuperando la lista de clientes");
	    		
	    		TypedQuery<Estado> query=null;
	    		boolean sinResultados= false;
	    		
	    		try {
		        	StringBuilder consulta= new StringBuilder("SELECT E FROM Estado E");
		    		query = metalesEM.createQuery( consulta.toString(), Estado.class);
		    	    	 
		    		listaEstados = query.getResultList();
		    	    	 
		    		} catch(javax.persistence.NoResultException nre){
		    			sinResultados=true;
		    		}
		    		if (listaEstados==null || sinResultados || listaEstados.size()==0){
		    			return null;
		    		}
	    		
		    		log.info("Lista de clientes recuperados: " + listaEstados.size());
		    		return listaEstados;
		    		
			} catch (Exception e) {
					log.error("Error al buscar el cliente",e);
					throw e;
			}
		}
	
	public List<Estado> buscarEstadosPorNombre(String nombre) throws RDNException, Exception {
    	
    	List<Estado> listaEstados = new ArrayList<Estado>();
    	
    	try {
    		log.info("Recuperando la lista de estados");
    		
    		TypedQuery<Estado> query;
    		
    		try {
	        	StringBuilder consulta= new StringBuilder("SELECT E FROM Estado E where lower(E.nombre) LIKE :nombre");
	    		query = metalesEM.createQuery( consulta.toString(), Estado.class);
	    		query.setParameter("nombre", "%" + nombre.toLowerCase() + "%");
	    	    	 
	    		listaEstados = query.getResultList();
	    	    	 
	    		} catch (javax.persistence.NoResultException nre) { }
    		
	    		log.info("Lista de clientes recuperados: " + listaEstados.size());
	    		return listaEstados;
	    		
		} catch (Exception e) {
				log.error("Error al buscar el estado",e);
				throw e;
		}
	}
}
