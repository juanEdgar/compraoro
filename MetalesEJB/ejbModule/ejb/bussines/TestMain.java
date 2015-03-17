package ejb.bussines;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import clases.business.metales.vo.cotizador.Metal;
import clases.vo.tienda.caja.TiendaCajaEfectivo;
import clases.vo.tienda.caja.TiendaCajaEfectivoMovimiento;
import clases.vo.tienda.caja.TiendaCajaEfectivoSaldo;

import com.sun.xml.internal.ws.resources.ManagementMessages;

import ejb.bussines.exception.RDNException;

public class TestMain {

	static float  denominaciones[]= new float[]{1000F,500F,200F,100F,20F,10F};
	
	public static void main(String[] args) {
			
		
		try {
			
			
			List<TiendaCajaEfectivoMovimiento> movimientos= calcularPagoPorDenominacion(610F);
			
			for(TiendaCajaEfectivoMovimiento m: movimientos){
				System.out.println("Denominacion cantidad "+m.getDenominacion()+" "+m.getCantidad());
			}
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	

			
		public static List<TiendaCajaEfectivoMovimiento> calcularPagoPorDenominacion(float monto ) throws RDNException,Exception{
	    	
	    	try{
	    		
	    		
	    		

	    		if(monto<=0){
	    			throw new RDNException("El monto debe ser mayor de cero");
	    		}
	    		
	    		
	    		
	    		
	    		 List<TiendaCajaEfectivoSaldo> saldos= new ArrayList<TiendaCajaEfectivoSaldo>();
	    		 
	    		 TiendaCajaEfectivoSaldo s= new TiendaCajaEfectivoSaldo();
	    		 s.setCantidad(1);
	    		 s.setDenominacion(1000);
	    		 saldos.add(s);
	    		 
	    		 s= new TiendaCajaEfectivoSaldo();
	    		 s.setCantidad(1);
	    		 s.setDenominacion(500);
	    		 saldos.add(s);
	    		 
	    		 s= new TiendaCajaEfectivoSaldo();
	    		 s.setCantidad(2);
	    		 s.setDenominacion(200);
	    		 saldos.add(s);
	    		 
	    		 s= new TiendaCajaEfectivoSaldo();
	    		 s.setCantidad(0);
	    		 s.setDenominacion(100);
	    		 saldos.add(s);
	    		 
	    		 s= new TiendaCajaEfectivoSaldo();
	    		 s.setCantidad(1);
	    		 s.setDenominacion(50);
	    		 saldos.add(s);
	    		 
	    		 s= new TiendaCajaEfectivoSaldo();
	    		 s.setCantidad(2);
	    		 s.setDenominacion(20);
	    		 saldos.add(s);
	    		 
	    		 s= new TiendaCajaEfectivoSaldo();
	    		 s.setCantidad(2);
	    		 s.setDenominacion(10);
	    		 saldos.add(s);
	    		 
	 
	    		 
	    		List<TiendaCajaEfectivoMovimiento> movimientos= new ArrayList<>();    			
	    		TiendaCajaEfectivoMovimiento movimiento= null;
	    			
	    			int cantidad;
	    			boolean exacto=false;
	    			
	    			for(TiendaCajaEfectivoSaldo denominacion: saldos){
	    				
	    				if( denominacion.getCantidad()>0 &&  monto>=denominacion.getDenominacion()){
	    					
	    					movimiento= new TiendaCajaEfectivoMovimiento();
	    					
	    					cantidad=(int)Math.floor(monto/denominacion.getDenominacion());
	    					
	    					if(cantidad>denominacion.getCantidad()){
	    						
	    						cantidad=denominacion.getCantidad();  
	    						
	    					}
	    					
	    					movimiento.setCantidad(cantidad);
							movimiento.setDenominacion(denominacion.getDenominacion());
							
	    					movimientos.add(movimiento);
	    					   					
	    					
	    					if(monto%denominacion.getDenominacion()==0 && (monto-cantidad*denominacion.getDenominacion()) ==0){
	    					   exacto=true;
	    					   break;	
	    					}
	    					
	    					
	    					
	    					monto-=cantidad*denominacion.getDenominacion();				
	    				}		
	    			}
	    			
	    			// si el monto no fue exacto, no se tiene el cambio suficiente para cubrir el pago
	    			if(!exacto){
	    				throw new RDNException("No es posible dar un monto exacto");
	    			}
	    			
	    			return movimientos;	  		 
	    		
	    		
	    	}catch(RDNException e){
	    		throw e;
	    	}catch(Exception e){
	    		e= new Exception("Error al ajustar el saldo de la caja en la tienda.",e);
	    		throw e;
	    	}
	    	
	    	
	    	
	    }
	
}
