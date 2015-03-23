package ejb.bussines.compra;

import java.security.InvalidParameterException;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import clases.business.metales.vo.cotizador.Diamante;
import clases.business.metales.vo.cotizador.Metal;
import clases.business.metales.vo.cotizador.PrecioDiamante;
import clases.business.metales.vo.cotizador.Producto;
import clases.persistence.jpa.factory.qualifier.MetalesEM;
import ejb.bussines.DiamanteEJB;
import ejb.bussines.PropertiesEJB;
import ejb.bussines.administracion.TipoDeCambioEJB;
import ejb.bussines.exception.RDNException;


@Stateless
@LocalBean
public class CotizadorEJB {
	
	@Inject @MetalesEM
	private EntityManager metalesEM;
	
	@Resource
	private SessionContext context;
	
	@EJB
	private PropertiesEJB properties;
	
	@EJB
	private TipoDeCambioEJB tipoDeCambioEJB;
	
	@EJB
	private DiamanteEJB ejbDiamante;
	
	private static final Logger log = LogManager .getLogger(CotizadorEJB.class);
    
    public CotizadorEJB() {
    }
    
    
    
    
    
    public float cotizarRedondeada(final Producto producto, float pesoGramos) throws RDNException, Exception{
    	
    try{
    	log.info("Cotizacion redondeada a decenas");
    	
    	float cotizacion=0.0F;
    	
    	if(producto instanceof Metal){    	
    		 cotizacion=this.cotizar((Metal)producto, pesoGramos);
    	}else if(producto instanceof Diamante){
    		cotizacion= this.cotizarDiamante((Diamante)producto);
    	}else{
    		throw new RDNException("Producto invalido");
    	}
    	
    	
    	
    	log.info("Cotizacion sin redondeo:"+cotizacion);
    	cotizacion/=properties.getFactorRedondeo();
    	cotizacion=(float)Math.floor(cotizacion);
    	cotizacion*=properties.getFactorRedondeo();
    	log.info("Cotizacion con redondeo:"+cotizacion);
    	return cotizacion;
    }catch(Exception e){
    	log.error(e);
    	this.context.setRollbackOnly();
    	throw e;
    }
    
    }

    
    public float cotizarDiamante(Diamante diamante) throws RDNException, Exception{

    	try{
    		
    		
    		log.info("Inicia el metodo de cotizacion diamante");
    		
        	if(diamante==null ){
        		throw new RDNException("No se puede cotizar un diamante nulo");
        	}
        	
        	if(diamante.getQuilates()==0.0F){
        		return 0.0F;
        	}
        	
        	if(diamante.getColor()==null || diamante.getColor().getId()==0 ){
        		throw new RDNException("No se puede cotizar un diamante sin color");
        	}
        	
        	if(diamante.getLimpieza()==null || diamante.getLimpieza().getId()==0 ){
        		throw new RDNException("No se puede cotizar un diamante sin limpieza");
        	}
        	
        	if(diamante.getPunto()==null || diamante.getPunto().getId()==0 ){
        		throw new RDNException("No se puede cotizar un diamante sin caterogia");
        	}
        	
        
        	// se consulta en la BD el precio
        	
        	PrecioDiamante precio=this.ejbDiamante.getPrecioDiamante(diamante);
        	
        	log.info("Precio del diamante: "+precio.getPrecio());
    		
    		float valorTC;
    		
    		// se busca la conversion de tipo de cambio
    		if(! (precio.getMoneda().equals(properties.getMonedaSistema())) ){
    			log.info("La moneda del precio de venta del diamante, no es la misma del sistema, se obtiene el TC");
    			valorTC= this.tipoDeCambioEJB.getTCPorMoneda(precio.getMoneda(), properties.getMonedaSistema()).getValor();
    			log.info(String.format("Tipo de cambio recuperado %f", valorTC));    			
    			
    		}else{
    			log.info("La moneda del sistema es la misma del precio de venta");
    			// si las monedas son iguales, no se convierte por ticpo de cambio;
    			valorTC=1;
    		}
    		
    		
    		// se obtiene el valor de la cotizacion , peso del metal por precio de gramo del metal por tipo de cambio
    		return valorTC*diamante.getQuilates()*precio.getPrecio();
    		
    		
    		
    	}catch(javax.persistence.NoResultException nre){
    		this.context.setRollbackOnly();
    		throw new RDNException("No existe precio para metal requerido");
    	}
    }
    
    
    public float cotizar(final Metal metalAcotizar, float pesoGramos) throws RDNException, Exception{
    	
    	
    	
    	try{
    		
    		
    		log.info("Inicia el metodo de cotizacion");
        	
        	if(metalAcotizar==null || metalAcotizar.getPorcentajePureza()<=0.0F ){
        		throw new InvalidParameterException("No se pude contizar un metal NULL");
        	}
        	if(pesoGramos<0.0F){
        		throw new InvalidParameterException("No se puede cotizar un peso menor a cero");
        	}
        	
        	if(pesoGramos==0.0F){
        		return 0.0F;
        	}
        	
        	
        	Metal metalBD=null;
    		
    		metalBD= this.metalesEM.find(Metal.class, metalAcotizar.getId());
    		
    		if(metalBD==null){
    			throw new RDNException("No existe precio metal requerido");
    		}
    		
    		
    		float valorTC;
    		
    		// se busca la conversion de tipo de cambio
    		if(! (metalBD.getPrecioGramo().getMoneda().equals(properties.getMonedaSistema())) ){
    			log.info("La moneda del precio de venta del metal, no es la misma del sistema, se obtiene el TC");
    			valorTC= this.tipoDeCambioEJB.getTCPorMoneda(metalBD.getPrecioGramo().getMoneda(), properties.getMonedaSistema()).getValor();
    			log.info(String.format("Tipo de cambio recuperado %f", valorTC));    			
    			
    		}else{
    			log.info("La moneda del sistema es la misma del precio de venta");
    			// si las monedas son iguales, no se convierte por ticpo de cambio;
    			valorTC=1;
    		}
    		
    		float cantidadMetalFino=pesoGramos*metalAcotizar.getPorcentajePureza()/100.0F;    	
    		
    		log.info(String.format("Precio por gramo del %s : %f",metalBD.getNombre(),metalBD.getPrecioGramo().getPrecio()));
    		
    		// se obtiene el valor de la cotizacion , peso del metal por precio de gramo del metal por tipo de cambio
    		return  (valorTC* metalBD.getPrecioGramo().getPrecio() )* // esto da el precio de gramo de metal fino por el tipo de cambio a aplicar
    				cantidadMetalFino;
    		
    		
    		
    	}catch(javax.persistence.NoResultException nre){
    		this.context.setRollbackOnly();
    		throw new RDNException("No existe precio para metal requerido");
    	}
    	
    	
    	
    }
    
  
    
    
    
  

}
