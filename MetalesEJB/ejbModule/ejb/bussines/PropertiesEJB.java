package ejb.bussines;

import javax.ejb.EJB;
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
	private  Moneda MONEDA_DOLAR;
	private String codigoMonedasistema="MXP";
	private String codigoDolar="USD";
	
	private float [] denominacionesValidas= new float[]{10F,20F,50F,100F,200F,500F,1000F};
	
	
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
    		
    		
    	}
    	
    	return this.MONEDA_SISTEMA;
    	
    	
    }
    
 public  Moneda getMonedaDolar() throws Exception{
    	
    	if(MONEDA_DOLAR==null){
    		
    		TypedQuery<Moneda> query=null;
    		
    		try{
    			
    			 query= metalesEM.createQuery("SELECT M FROM Moneda M where M.codigo= :codigo" ,Moneda.class);
    	    	 query.setParameter("codigo", this.codigoDolar);
    	    	
    	    	 this.MONEDA_DOLAR=query.getSingleResult();
    			
    		}catch(javax.persistence.NoResultException nre){
    			throw new Exception("No existe la moneda para el sistema de codigo"+this.codigoMonedasistema);
    		}
    	}
    		
    		return this.MONEDA_DOLAR;
    	
    	
    	
    }
    
    public int getMayoriaDeEdad(){
    	return 18;
    }
    
    
   
    
    public int getFactorRedondeo(){
    	return 10;
    }

	public float[] getDenominacionesValidas() {
		return denominacionesValidas;
	}

	
    
    
    
}
