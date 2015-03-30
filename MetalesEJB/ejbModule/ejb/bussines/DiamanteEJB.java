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

import clases.business.metales.vo.cotizador.Diamante;
import clases.business.metales.vo.cotizador.DiamanteColor;
import clases.business.metales.vo.cotizador.DiamanteLimpieza;
import clases.business.metales.vo.cotizador.DiamantePunto;
import clases.business.metales.vo.cotizador.PrecioDiamante;
import clases.login.UsuarioSesion;
import clases.persistence.jpa.commun.embeddable.UsuarioModifico;
import clases.persistence.jpa.factory.qualifier.MetalesEM;
import ejb.bussines.exception.RDNException;

/**
 * Session Bean implementation class DiamanteEJB
 */
@Stateless
@LocalBean
public class DiamanteEJB {

   

	@Inject @MetalesEM
	private EntityManager metalesEM;
	
	@Resource
	private SessionContext context;
	
	@EJB
	private PropertiesEJB properties;
	
	private static final Logger log = LogManager .getLogger(DiamanteEJB.class);
	
	@Inject
	private UsuarioSesion usuario;
	
	
	
	
    public DiamanteEJB() {
    }
    
    
    public List<DiamantePunto> getPuntos(){
    	
    	 List<DiamantePunto> puntos=null;    	 

     	try {
 			
     		TypedQuery<DiamantePunto> query= metalesEM.createQuery("SELECT punto FROM DiamantePunto punto order by punto.id" ,DiamantePunto.class);
     		puntos=query.getResultList();
     		log.info("cantidad de puntos recuperados "+puntos.size());    		
     		
     	
     		
 		} catch (Exception e) {
 			log.error("Error al recuperar la lista de puntos",e);
 			this.context.setRollbackOnly();
 			
 		}
    	
    	return puntos;
    	
    }

    public List<DiamanteColor> getColores(){
    	
    	List<DiamanteColor> colores=null;
    	try {
 			
     		TypedQuery<DiamanteColor> query= metalesEM.createQuery("SELECT color FROM DiamanteColor  color order by color.id" ,DiamanteColor.class);
     		colores=query.getResultList();
     		log.info("cantidad de colores recuperados "+colores.size());    		
     		
     	
     		
 		} catch (Exception e) {
 			log.error("Error al recuperar la lista de colores",e);
 			this.context.setRollbackOnly();
 			
 		}
    	
    	return colores;
    	
    }
    
    public List<DiamanteLimpieza> getLimpiezas(){
    
    	List<DiamanteLimpieza> limpiezas=null;
    	
    	try {
 			
     		TypedQuery<DiamanteLimpieza> query= metalesEM.createQuery("SELECT limpieza FROM DiamanteLimpieza limpieza  order by limpieza.id" ,DiamanteLimpieza.class);
     		limpiezas=query.getResultList();
     		log.info("cantidad de limpiezas recuperados "+limpiezas.size());    		
     		
     	
     		
 		} catch (Exception e) {
 			log.error("Error al recuperar la lista de limpiezas diamante",e);
 			this.context.setRollbackOnly();
 			
 		}
    	
    	return limpiezas;
    	
    }
    
    
    public PrecioDiamante getPrecioDiamante(Diamante diamante) throws RDNException, Exception{
    	
    	
    	log.info("Recuperando precio de diamante");
    	
    	try{
    		
    		if(diamante==null ){
    			throw new RDNException("No es posible recuperar el precio de un diamante invalido");
    		}
    		
    		if(diamante.getPunto()==null || diamante.getPunto().getId()<=0){
    			throw new RDNException("No es posible recuperar el precio de un diamante punto invalido");
    		}
    		
    		
    		if(diamante.getColor()==null || diamante.getColor().getId()<=0){
    			throw new RDNException("No es posible recuperar el precio de un diamante color invalido");
    		}
    		
    		
    		if(diamante.getLimpieza()==null || diamante.getLimpieza().getId()<=0){
    			throw new RDNException("No es posible recuperar el precio de un diamante limpieza invalido");
    		}
    		
    		log.info("Precio a consultar color, pureza, puntos: "+ diamante.getColor().getId()+" "+diamante.getLimpieza().getId()+" "+diamante.getPunto().getId());
    		
    		StringBuilder sql= new StringBuilder();
    		
    		Diamante diamanteBD=null;
    		
    		// se recupera el ID del diamnate solicitado
    		if(diamante.getId()==0){
    			sql.append("SELECT diamante FROM Diamante  diamante where diamante.color=:paramColor ");
        		sql.append("and diamante.punto=:paramPunto ");
        		sql.append("and diamante.limpieza=:paramLimpieza ");
        		
        		TypedQuery<Diamante> query= metalesEM.createQuery(sql.toString(), Diamante.class);
    			
        		
        		query.setParameter("paramColor", diamante.getColor());
        		query.setParameter("paramPunto", diamante.getPunto());
        		query.setParameter("paramLimpieza", diamante.getLimpieza());
        		
        		diamanteBD= query.getSingleResult();
        		
    		}else{
    			diamanteBD=this.metalesEM.find(Diamante.class, diamante.getId());
    		}
    		
    		log.info("Se encontro el ID diamante ID precio "+diamanteBD.getId()+" "+diamanteBD.getPrecio().getId());
    		
    		diamanteBD.getPrecio().setTipoDiamante(diamanteBD);
    		
    		
    		return diamanteBD.getPrecio();
    		
    	}catch(RDNException e){
    		this.context.setRollbackOnly();
    		throw e;
    		
    	}catch(Exception e){
    		e= new Exception("Error al recuperar los precios de diamante por punto",e);
    		log.error(e,e);
    		this.context.setRollbackOnly();
    		throw e;
    	}
    	
    	
    	
    }
    

    
    
    public void actualizarPrecioDiamante(Diamante diamante) throws RDNException, Exception {
    	
    	
    	
    	try{
    		
    		log.debug("Actualizando precio de diamante");
    		
    		if(diamante==null ){
    			throw new RDNException("No es posible recuperar el precio de un diamante invalido");
    		}
    		
    		
    		if(diamante.getPrecio()==null  || diamante.getPrecio().getId()==0){
    			throw new RDNException("No se puede actualizar el precio de id precio cero");
        		
        		
    		}
    		
    		if(diamante.getPrecio().getPrecio()<=0){
    			throw new RDNException("No se puede poner un precio en cero para diamante");
    		}
    		
    		
    		PrecioDiamante precioBD= this.metalesEM.find(PrecioDiamante.class, diamante.getPrecio().getId());
    		
    		if(precioBD.getPrecio()!= diamante.getPrecio().getPrecio() ){
	    	
    			precioBD.setEstatus(0);
	    		this.metalesEM.persist(precioBD);
	    		
	    		log.info("Precio Anterior actualizado a cero, valor diferente");
	    		// se actualoza el precio el diamante actual
	    		
	    		
	    		PrecioDiamante nuevoPrecio= new PrecioDiamante();
	    		nuevoPrecio.setEstatus(1);
	    		nuevoPrecio.setMoneda(this.properties.getMonedaDolar());
	    		nuevoPrecio.setTipoDiamante(precioBD.getTipoDiamante());
	    		nuevoPrecio.setPrecio(diamante.getPrecio().getPrecio());
	    		nuevoPrecio.setUsuarioModifico(new UsuarioModifico(this.usuario.getNombreUsuario()));
	    		
	    		this.metalesEM.persist(nuevoPrecio);
	    		
	    		
    		}
    		log.info("Precio Diamante insertado");
    		
    		
    		
    	}catch(RDNException e){
    		this.context.setRollbackOnly();
    		throw e;
    		
    	}catch(Exception e){
    		e= new Exception("Error al recuperar los precios de diamante por punto",e);
    		log.error(e,e);
    		this.context.setRollbackOnly();
    		throw e;
    	}
    	
    }
    
    
 
    
}
