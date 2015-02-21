package ejb.bussines.venta;

import java.security.InvalidParameterException;
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
import clases.business.metales.vo.cotizador.PurezaMetal;
import clases.persistence.jpa.factory.qualifier.MetalesEM;
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
	

	
	private static final Logger log = LogManager .getLogger(CotizadorEJB.class);
    
    public CotizadorEJB() {
    }
    
    
    
    
    
    public float cotizar(final Metal metalAcotizar, float pesoGramos) throws RDNException, Exception{
    	
    	log.info("Inicia el metodo de cotizacion");
    	
    	if(metalAcotizar==null || metalAcotizar.getPurezaMetal()==null || metalAcotizar.getPurezaMetal().getId()==0){
    		throw new InvalidParameterException("No se pude contizar un metal NULL");
    	}
    	if(pesoGramos<0.0F){
    		throw new InvalidParameterException("No se puede cotizar un peso menor a cero");
    	}
    	
    	if(pesoGramos==0.0F){
    		return 0.0F;
    	}
    	
    	
    	Metal metalBD=null;
    	
    	try{
    	
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
    		
    		try{
    		
    		// se intenta recuperar el valor de la pureza de la bd
    		metalBD.setPurezaMetal(metalBD.getListaPurezas().get(
    																metalBD.getListaPurezas().indexOf(metalAcotizar.getPurezaMetal())  
    															));
    		}catch(IndexOutOfBoundsException iob){
    			throw new Exception("No existe la pureza del metal a cotizar ID "+metalAcotizar.getId());
    		}
    		
    	
    		
    		// se obtiene el valor de la cotizacion , peso del metal por precio de gramo del metal por tipo de cambio
    		return  (valorTC* metalBD.getPrecioGramo().getPrecio() )* // esto da el precio de gramo de metal fino por el tipo de cambio a aplicar
    				(pesoGramos*metalBD.getCantidadMetalFino()); // esto da la cantidad de metal fino total
    		
    		
    	}catch(javax.persistence.NoResultException nre){
    		throw new RDNException("No existe precio para metal requerido");
    	}
    	
    	
    	
    }
    
    
    
  

}
