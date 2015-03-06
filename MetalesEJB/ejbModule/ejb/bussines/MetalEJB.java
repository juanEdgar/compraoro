package ejb.bussines;

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import clases.business.metales.vo.cotizador.Metal;
import clases.business.metales.vo.cotizador.PrecioMetal;
import clases.business.metales.vo.cotizador.PurezaMetal;
import clases.login.QualifierUsuarioSesion;
import clases.login.UsuarioSesion;
import clases.persistence.jpa.commun.embeddable.UsuarioModifico;
import clases.persistence.jpa.factory.qualifier.MetalesEM;
import clases.vo.dinero.Moneda;
import ejb.bussines.exception.RDNException;

/**
 * Session Bean implementation class MatalEJB
 */
@Stateless
@LocalBean
public class MetalEJB {

	

	@Inject @MetalesEM
	private EntityManager metalesEM;
	
	@Resource
	private SessionContext context;
	
	@EJB
	private PropertiesEJB properties;
	
	private static final Logger log = LogManager .getLogger(MetalEJB.class);
	
	@Inject
	@QualifierUsuarioSesion 
	private UsuarioSesion ejbUsuario;
	
	
    /**
     * Default constructor. 
     */
    public MetalEJB() {
        // TODO Auto-generated constructor stub
    }
    
  public List<Metal> getMetalesComercializables(){
    	
    	log.info("Consultando lista de metales activos");
    	
    	List<Metal> metales= null;
    	
    	try {
			
    		TypedQuery<Metal> query= metalesEM.createQuery("SELECT m FROM Metal m where m.estatus=1" ,Metal.class);
    		metales=query.getResultList();
    		log.info("cantidad de metales recuperados "+metales.size());    		
    		
    	
    		
		} catch (Exception e) {
			log.error("Error al recuperar la lista de metales",e);
			this.context.setRollbackOnly();
			
		}
    	
    
    	
    	return metales;
    }
  
  
  	public void actualizarPrecioMetal(Metal metal, float precioGramo, Moneda moneda) throws RDNException,Exception{
  		
  		log.info("Actualizando el valor del precio de un metal");
  		
  		try{
  			
  			if(metal==null|| metal.getId()==0){
  				throw new RDNException("No se puede actualizar el precio de un metal no existente");
  			}
  			if(precioGramo<=0){
  				throw new RDNException("El precio por gramo debe ser mayor que cero");
  			}
  			if(moneda==null || moneda.getId()==0){
  				throw new RDNException("La moneda del nuevo precio debe existir");
  			}
  			
  			log.info("validaciones exitosas");
  			
  			PrecioMetal precioActual= this.getPrecioMetal(metal);
  			// se cancela el precio actual
  			precioActual.setEstatus(0);
  			this.metalesEM.persist(precioActual);
  			
  			log.info("Precio actual cancelado");
  			
  			// se crea el nuevo precio
  			
  			PrecioMetal precioNuevo=new PrecioMetal();
  			precioNuevo.setEstatus(1);
  			precioNuevo.setUsuarioModifico(new UsuarioModifico(this.ejbUsuario.getNombreUsuario()));
  			precioNuevo.setMoneda(moneda);
  			precioNuevo.setMetal(metal);
  			precioNuevo.setPrecio(precioGramo);
  			this.metalesEM.persist(precioNuevo);
  			
  			log.info("Precio nuevo guardado");
  			
  		}catch(RDNException rdnE){
  			this.context.setRollbackOnly();
  			throw rdnE;
  		}catch(Exception e){
  			this.context.setRollbackOnly();
  		    e = new Exception("Error al realizar el cambio al precio del metal",e);
  			log.error(e);;
  			throw e;
  		}
  		
  	}
  	


  
  	public List<PurezaMetal> getListaPurezas(Metal m){
  		

    	log.info("Consultando lista de metales activos");
    	
    	
    	List<PurezaMetal> purezas= null;
    	
    	try {
			    		
    		purezas=this.metalesEM.find(Metal.class, m.getId()).getListaPurezas();
    		log.info("cantidad de purezas recuperados "+purezas.size());    		
    		
		} catch (Exception e) {
			log.error("Error al recuperar la lista de purezas",e);
			this.context.setRollbackOnly();
			
		}
    	
    
    	
    	return purezas;
  		
  	}
  	

    public PrecioMetal getPrecioMetal(Metal m) throws RDNException{
    	
    	log.info("Ingresando a orecio de metal");
    	
		if(m==null||m.getId()==0){
			this.context.setRollbackOnly();
			throw new RDNException("No se puede mandar un metal nulo para saber su precio");
		}
    	
    	PrecioMetal precioMetal= null;
    	try {
			
    	
        		
    		TypedQuery<PrecioMetal> query=null;
    		query= metalesEM.createQuery("SELECT Precio FROM PrecioMetal Precio where Precio.metal=:metal" ,PrecioMetal.class);
			query.setParameter("metal", m);
	    	precioMetal=query.getSingleResult();
    	    
    		
		} catch (Exception e) {
			this.context.setRollbackOnly();
			log.error("no se pudo recuperar el precio del metal ",e);
			throw e;
		}
    	return precioMetal;
    	
    }
  

}
