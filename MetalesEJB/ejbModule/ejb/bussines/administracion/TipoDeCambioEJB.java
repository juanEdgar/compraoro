package ejb.bussines.administracion;

import java.security.InvalidParameterException;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import clases.login.UsuarioSesion;
import clases.persistence.jpa.commun.embeddable.UsuarioModifico;
import clases.persistence.jpa.factory.qualifier.MetalesEM;
import clases.vo.dinero.Moneda;
import clases.vo.dinero.TipoDeCambio;
import ejb.bussines.compra.CotizadorEJB;
import ejb.bussines.exception.RDNException;

/**
 * Session Bean implementation class TipoDeCambioEJB
 */
@Stateless
@LocalBean
public class TipoDeCambioEJB {
	
	
	@Inject @MetalesEM
	private EntityManager metalesEM;
	
	@Resource
	private SessionContext context;
	
	private static final Logger log = LogManager .getLogger(CotizadorEJB.class);
	
	@Inject 
	private UsuarioSesion usuarioSesion;
	
    /**
     * Default constructor. 
     */
    public TipoDeCambioEJB() {
        // TODO Auto-generated constructor stub
    }
    
    
    public void cancelarTipoDeCambio(TipoDeCambio tc) throws RDNException, Exception{
    	
    	log.info("Cancelando un tipo de cambio");
    	if(tc==null || tc.getId()==0){
    		throw new InvalidParameterException("No se puede mandar un tipo de cambio vacio para actualizarlo");
    	}
    	
    	try{
    	
    		// se recupera el TC
    		tc= this.metalesEM.find(TipoDeCambio.class, tc.getId());
    		
    		log.info("Se encontro el TC a actualiar, se actualiza");
    		tc.setEstatus(0);
    		tc.setUsuarioModifico(new UsuarioModifico(this.usuarioSesion.getNombreUsuario()));
    		 this.metalesEM.persist(tc);
    		 log.info("Tipo de cambio cancelado");
    		
    	} catch(NoResultException nre){
    		throw new RDNException("No se encontro el tipo de cambio a actualizar");
    	}catch(Exception e){
    		log.error("Error al actualizar el tipo de cambio ID " +tc.getId(),e);
    		throw new Exception("Error al actualizar el tipo de cambio"+e.getMessage());
    	}
    	
    	
    }
    
    public void nuevoTC(Moneda monedaBase, Moneda monedaCambio, float tipoDeCambio) throws RDNException, Exception{
    	
    	if(monedaBase==null || monedaCambio==null || monedaBase.getId()==0 || monedaCambio.getId()==0){
    		throw new InvalidParameterException("La moneda base y cambio deben ser validas");
    	}
    	if(tipoDeCambio<=0){
    		throw new InvalidParameterException("El valor del tipo de cambio deber ser mayor que cero");
    	}
    	
    	try {
			
    		TipoDeCambio tc = new TipoDeCambio(this.usuarioSesion.getNombreUsuario());
    		
    		tc.setMonedaBase(monedaBase);
    		tc.setMonedaCambio(monedaCambio);
    		tc.setValor(tipoDeCambio);
    		tc.setEstatus(1);
    		this.metalesEM.persist(tc);
    		log.info("Tipo de cambio guardado");
    		
    		
		} catch (Exception e) {
			log.error("Error al dar de alta el tipo de cambio",e);
			throw new Exception("Error al dar de alta el tipo de cambio "+e.getMessage());
		}
    	
    }
    
    public void actualizarTipoDeCambio(Moneda monedaBase, Moneda monedaCambio, float nuevoTipoDeCambio) throws RDNException,Exception{
    	
    	try{
    		
    		log.info("Actualizando el tipo de cambio");
    		
    		
    		if(nuevoTipoDeCambio<=0.0F){
    			throw new InvalidParameterException("el nuevo tipo de cambio deber ser mayor que cero");
    		}
    		
    		
    		
    		TipoDeCambio tc= this.getTCPorMoneda(monedaBase, monedaCambio);
    		
    		if(tc==null){
    			throw new RDNException("No se puede actualiazar un tipo de cambio que no existe");
    		}
    		
    		// se cancela el tipo de cambio actual
    		tc.setEstatus(0);
    		tc.setUsuarioModifico(new UsuarioModifico(this.usuarioSesion.getNombreUsuario()));
    		this.metalesEM.persist(tc);
    		
    		TipoDeCambio tcn= new TipoDeCambio();
    		tcn.setInvertido(tc.isInvertido());;
    		tcn.setMonedaBase(tc.getMonedaBase());
    		tcn.setMonedaCambio(tc.getMonedaCambio());
    		tcn.setEstatus(1);
    		tcn.setUsuarioModifico(new UsuarioModifico(  this.usuarioSesion.getNombreUsuario()));
    		
    		
    		tcn.setValor(nuevoTipoDeCambio);
    	
    		
    		this.metalesEM.persist(tcn);
    		log.info("Tipo de cambio actualizado");
    		
    	}catch(Exception e){
    		log.error("No se pudo actualizar el tipo de cambio");
    		throw new Exception("Error al actualizar el tipo de cambio."+e.getMessage());
    	}
    	
    	
    }
    
    
    public float convertir(float valorMonedaBase, Moneda monedaBase, Moneda monedaCambio) throws Exception {
    	
    	String params=String.format("Convertir  de la moneda base %s a la %s por %f", monedaBase.getCodigo(), monedaCambio.getCodigo(), valorMonedaBase);
    	
    	log.info( params);
    	
    	
    	if(monedaBase==null || monedaBase.getId()==0 || monedaCambio==null || monedaCambio.getId()==0){
    		throw new InvalidParameterException("Parameros de entrada incorrectos en la conversión por tipo de cambio");
    	}
    	
    	if(valorMonedaBase==0.0F){
    		return 0.0F;
    	}
    	
    	
    	TipoDeCambio tc= this.getTCPorMoneda(monedaBase, monedaCambio);
		if(tc==null){
			throw new RDNException("No se recupero ningun tipo de cambio asociado a las monedas");
		}
    	
    	try{
    		
    		return valorMonedaBase*tc.getValor();
    	}
    	catch(Exception e){    		
    		String err="Error al convertir monedas por tipo de cambio "+params;
    		log.error(err,e);
    		throw new Exception(err);
    	}
    	
    }
    
    public TipoDeCambio getTCPorMoneda(Moneda monedaBase, Moneda monedaCambio){
    	
    	log.info("Entrando al tipo de cambio por moneda");
    	TipoDeCambio tc=null;
    	
    	if(monedaBase==null || monedaBase.getId()==0){
    		throw new InvalidParameterException("La moneda base no debe ser nula para leer el tipo de cambio");
    	}
    	
    	if(monedaCambio==null || monedaCambio.getId()==0){
    		throw new InvalidParameterException("La moneda cambio no debe ser nula para leer el tipo de cambio");
    	}
    	
    	try {
    		
    		
    		TypedQuery<TipoDeCambio> query=null;
    		boolean sinResultados= false;
    		try{
    			
    			
    			
    			 query= metalesEM.createQuery("SELECT TC FROM TipoDeCambio TC where TC.monedaBase= :base and TC.monedaCambio = :cambio and TC.estatus=1" ,TipoDeCambio.class);
    	    	 query.setParameter("base", monedaBase);
    	    	 query.setParameter("cambio", monedaCambio);
    	    	 tc=query.getSingleResult();
    	    	 tc.setInvertido(false);
    			
    		}catch(javax.persistence.NoResultException nre){
    			sinResultados=true;
    		}
    		
    		
    		
    		if(tc==null||sinResultados){
    			// se intenta recuperar el tipo de cambio por el orden inverso de las monedas
    			query= metalesEM.createQuery("SELECT TC FROM TipoDeCambio TC where TC.monedaBase= :cambio and TC.monedaCambio = :base and TC.estatus=1" ,TipoDeCambio.class);
        		query.setParameter("base", monedaBase);
        		query.setParameter("cambio", monedaCambio);
        		tc=query.getSingleResult();
        		
        		if(tc!=null){
        			// si hay coincidencia, se divide el tipo de cambio para dar el valor correcto por cambiar de orden las monedas
        			
        			tc.setInvertido(true);
        		
        		}
        		
    		}
    		
    		if(tc==null){
    			log.info("No se pudo recuperar el tipo de cambio");
    		}else{
    		
    			log.info("OK, se recupero tipo de cambio: "+tc.getValor());
    		}
		} 
    	catch(javax.persistence.NoResultException nre){
    		log.info("No se recupero ningun tipo de cambio asociado");
    		return null;
    	}
    	catch (Exception e) {
			this.context.setRollbackOnly();
			log.error("Error al obtener el tipo de cambio por moneda",e);
			return null;
		}
    	
    	return tc;
    	
    	
    }
    
    public List<TipoDeCambio> getTablaTC(){
    	log.info("Recuperando la tabla de todos los tipos de cambio");
    	
    	List<TipoDeCambio> tcList=null;
    	
    	try {
    		
    		TypedQuery<TipoDeCambio> query= metalesEM.createQuery("SELECT TC FROM TipoDeCambio TC where  TC.estatus=1 order by TC.monedaBase.id,TC.monedaCambio.id" ,TipoDeCambio.class);
    		tcList= query.getResultList();
    		
    		
    		log.info("OK, tabla de tipos de cambio recuperadas size: "+tcList.size());
		} catch (Exception e) {
			this.context.setRollbackOnly();
			log.error("Error al obtener la tabla completa de tipode de cambio",e);
			return null;
		}
    	
    	return tcList;
    }
    
    
    public List<TipoDeCambio> getTablaTC(Moneda moneda){
    	log.info("Recuperando la tabla de todos los tipos de cambio por moneda");
    	
    	if(moneda==null){
    		log.info("moneda nula, se manda toda la tabla de TC");
    		return this.getTablaTC();
    	}
    	
    	List<TipoDeCambio> tcList=null;
    	
    	try {
    		   		
    		tcList=this.metalesEM.find(Moneda.class ,moneda.getId() ).getTipoCambio();
    		
    		log.info("OK, tabla de tipos de cambio recuperadas size "+tcList.size());
		} catch (Exception e) {
			this.context.setRollbackOnly();
			log.error("Error al obtener la tabla completa de tipode de cambio  por moneda",e);
			return null;
		}
    	
    	return tcList;
    }
    
    public List<Moneda> getListaMonedas(){
    	log.info("Recuperando la lista de monedas");
    	
    	List<Moneda> mList=null;
    	
    	try {
    		
    		TypedQuery<Moneda> query= metalesEM.createQuery("SELECT M FROM Moneda M" ,Moneda.class);
    		mList= query.getResultList();
    		
    		
    		log.info("OK, tabla de monedas size: "+mList.size());
		} catch (Exception e) {
			this.context.setRollbackOnly();
			log.error("Error al obtener la tabla completa de tipode de cambio",e);
			return null;
		}
    	
    	return mList;
    }

}
