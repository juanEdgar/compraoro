package ejb.bussines;

import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import clases.persistence.jpa.factory.qualifier.MetalesEM;
import clases.vo.dinero.Moneda;

/**
 * Session Bean implementation class PropertiesEJB
 */
@Singleton
@LocalBean
public class PropertiesEJB {


	@Inject @MetalesEM
	private EntityManager metalesEM;
	


	private  Moneda MONEDA_SISTEMA;
	private String codigoMonedasistema="MXP";
	
	
	
	
    /**
     * Default constructor. 
     */
    public PropertiesEJB() {
        
    	
    }
    
    public  Moneda getMonedaSistema() throws Exception{
    	
    	if(MONEDA_SISTEMA==null){
    		
    		TypedQuery<Moneda> query=null;
    		
    		try{
    			
    			 query= metalesEM.createQuery("SELECT M FROM Moneda M where M.codigo= :codigo" ,Moneda.class);
    	    	 query.setParameter("codigo", this.codigoMonedasistema);
    	    	
    	    	 this.MONEDA_SISTEMA=query.getSingleResult();
    			
    		}catch(javax.persistence.NoResultException nre){
    			throw new Exception("No existe la moneda para el sistema de codigo"+this.codigoMonedasistema);
    		}
    		
    		return this.MONEDA_SISTEMA;
    	}else{
    		return this.MONEDA_SISTEMA;
    	}
    	
    }
    
    public int getMayoriaDeEdad(){
    	return 18;
    }
    
    
   
    
    public int getFactorRedondeo(){
    	return 10;
    }
    
}
