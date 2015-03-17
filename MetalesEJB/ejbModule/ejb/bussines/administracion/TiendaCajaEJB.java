package ejb.bussines.administracion;

import java.util.ArrayList;
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

import clases.business.metales.vo.compra.PagoCompraEfectivo;
import clases.login.UsuarioSesion;
import clases.persistence.jpa.commun.embeddable.UsuarioModifico;
import clases.persistence.jpa.factory.qualifier.MetalesEM;
import clases.vo.tienda.Tienda;
import clases.vo.tienda.caja.TiendaCajaEfectivo;
import clases.vo.tienda.caja.TiendaCajaEfectivoMovimiento;
import clases.vo.tienda.caja.TiendaCajaEfectivoSaldo;
import ejb.bussines.PropertiesEJB;
import ejb.bussines.exception.RDNException;

/**
 * Session Bean implementation class TiendaCajaEJB
 */
@Stateless
@LocalBean
public class TiendaCajaEJB {

	
	@Inject
	private UsuarioSesion usuarioSesion;
	
	@Inject @MetalesEM
	private EntityManager metalesEM;
	
	@Resource
	private SessionContext context;
	
	private static final Logger log = LogManager .getLogger(TiendaCajaEJB.class);
	
	

	@EJB
	private PropertiesEJB properties;
	
    /**
     * Default constructor. 
     */
    public TiendaCajaEJB() {
        // TODO Auto-generated constructor stub
    }
    
    
    public TiendaCajaEfectivo getCajaPorTienda(Tienda tienda, int caja) throws RDNException, Exception{
        try{
	    	if(caja<=0){
	    		
	    		throw new RDNException("El numero de caja a busacar es incorrecto");
	    	}
	    	
	    	for(TiendaCajaEfectivo tiendai: this.getCajasPorTienda(tienda)){
	    		if(tiendai.getId()==caja){
	    			return tiendai;
	    		}
	    	}
	    	
	    	throw new RDNException("No existe la caja de la tienda a buscar");
        }catch(RDNException e){
    		this.context.setRollbackOnly();
    		throw e;
    	}catch(Exception e){
    		e= new Exception("Error al recuperar la caja de la tienda.",e);
    		log.error(e,e);
    		this.context.setRollbackOnly();
    		throw e;
    	}
    	
    }
    
    public List<TiendaCajaEfectivo> getCajasPorTienda(Tienda tienda) throws RDNException, Exception {
    	
    	try{
    		
    		log.info("Se buscan las cajas por tienda");
    		
    		if(tienda==null || tienda.getId()==0){
    			throw new RDNException("No se pueden recuperar las cajas de una tienda invalida");
    		}
    		
    		Tienda tiendaEncontrada= this.metalesEM.find(Tienda.class, tienda.getId());
    		
    		if(tiendaEncontrada==null){
    			throw new RDNException("No existe la tienda a buscar para obtener sus cajas");
    		}
    		
    		if(tiendaEncontrada.getCajasEfectivo()==null || tiendaEncontrada.getCajasEfectivo().size()==0){
    			throw new RDNException("La sucursal no tiene cajas asignadas");
    		}
    		
    		return tiendaEncontrada.getCajasEfectivo();
    		
    	}catch(RDNException e){
    		this.context.setRollbackOnly();
    		throw e;
    	}catch(Exception e){
    		e= new Exception("Error al recuperar las cajas de la tienda.",e);
    		log.error(e,e);
    		this.context.setRollbackOnly();
    		throw e;
    	}
    	
    	
    }
    
    public  void registarSalidaEnCajaPorCompra(TiendaCajaEfectivo caja, List<TiendaCajaEfectivoMovimiento> movimientos) throws RDNException, Exception{
    	 this.registarSalidaEnCaja(caja, movimientos, 7);
    }
    
    public  void registarDotacionEnCaja(TiendaCajaEfectivo caja, List<TiendaCajaEfectivoMovimiento> movimientos) throws RDNException, Exception{
   	 this.registarSalidaEnCaja(caja, movimientos, 5);
   }
    
    
    private  void registarSalidaEnCaja(TiendaCajaEfectivo caja, List<TiendaCajaEfectivoMovimiento> movimientos, int estatus) throws RDNException, Exception{
    	
    	try{
    		
    		log.info("Se ingresa a la opcion de registrar salidas de caja");
    		
    		if(caja==null ||  caja.getTienda()==null || caja.getId()==0 || caja.getTienda().getId()==0){
    			throw new RDNException("No se puede registrar movimientos a una caja invalida");
    		}
    		
    		if(movimientos==null || movimientos.size()==0){
    			throw new RDNException("La lista de movimientos de caja esta vacia");
    		}
    		
    		log.info("Se proce al registro de los movimientos de caja");
    		
    		UsuarioModifico usuario= new UsuarioModifico(this.usuarioSesion.getNombreUsuario());
    		boolean esDenominacionValida=false;
    		for(TiendaCajaEfectivoMovimiento mov: movimientos){
    			
    			log.info("Se procede a registra el movimiento");
    			
    			esDenominacionValida=false;
    			
    			if(mov.getCantidad()==0){
    				throw new RDNException("La cantidad del movimiento debe ser diferente de cero");
    			}
    			
    			for(float denominacionValida:properties.getDenominacionesValidas()){
    				if(denominacionValida==mov.getDenominacion()){
    					esDenominacionValida=true;
    					break;
    				}
    			}
    			
    			if(!esDenominacionValida){
    				throw new RDNException("El movimiento a ingresar no tiene una denominacion válida");
    			}
    			
    			mov.setUsuarioModifico(usuario);
    			mov.setTiendaCaja(caja);
    			mov.setEstatus(estatus); 
    				
    			this.metalesEM.persist(mov);
    			
    			log.info("Movimiento registrado con exito, se procede a afectar el saldo de la caja");
    			
    			this.ajustarSaldoEnCaja(caja, mov);
    			
    			log.info("Movimieto de caja registrado con éxito");
    			
    		}
    		
    	}catch(RDNException e){
    		this.context.setRollbackOnly();
    		throw e;
    	}catch(Exception e){
    		e= new Exception("Error al registra el movimiento en caja por compra.",e);
    		log.error(e,e);
    		this.context.setRollbackOnly();
    		throw e;
    	}
    	
    }
    
    /*
     * Metodo que reduce el saldo de la caja en por la cantidad del movimiento especificado
     */
    public void ajustarSaldoEnCaja(TiendaCajaEfectivo caja, TiendaCajaEfectivoMovimiento movimiento) throws RDNException, Exception{
    	
    	try{
    		
    		log.info("Se ingresa a la opcion de registrar salidas de caja");
    		
    		if(caja==null ||  caja.getTienda()==null || caja.getId()==0 || caja.getTienda().getId()==0){
    			throw new RDNException("No se puede registrar movimientos a una caja invalida");
    		}
    		
    		if(movimiento==null || movimiento.getCantidad()==0 || movimiento.getDenominacion()==0){
    			throw new RDNException("No se puede afectar el saldo de caja con un movimiento incorrecto");
    		}
    		
    		boolean esDenominacionValida=false;
    		
    		for(float denominacionValida:properties.getDenominacionesValidas()){
				if(denominacionValida==movimiento.getDenominacion()){
					esDenominacionValida=true;
					break;
				}
			}
    		
    		if(!esDenominacionValida){
				throw new RDNException("El movimiento a ingresar no tiene una denominacion válida");
			}
    		
    		log.info("Se proce al registro de los movimientos de caja");
    		
    		UsuarioModifico usuario= new UsuarioModifico(this.usuarioSesion.getNombreUsuario());
    		
    			
        		
        		
        		TypedQuery<TiendaCajaEfectivoSaldo> query=null;
        		
        		try{
        			
        			TiendaCajaEfectivoSaldo saldoActual=null;
        																																		   
        			 query= metalesEM.createQuery("SELECT Saldo FROM TiendaCajaEfectivoSaldo Saldo where Saldo.cajaEfectivo=:caja and Saldo.denominacion=:denominacion" ,TiendaCajaEfectivoSaldo.class);
        	    	 query.setParameter("caja", caja);
        	    	 query.setParameter("denominacion", movimiento.getDenominacion());
        	    	 saldoActual=query.getSingleResult();
        	    	 
        			 int nuevosaldo=0;
        			 
        			 nuevosaldo= saldoActual.getCantidad()+movimiento.getCantidad();
        			 
        			 if(nuevosaldo<0){
        				 throw new RDNException("No se pueda aplicar el moviemito a caja, no hay saldo suficiente");
        			 }
        	    	 
        			 saldoActual.setCantidad(nuevosaldo);
        			 log.info("Se actualizara el saldo en caja");
        			 this.metalesEM.persist(saldoActual);
        			 log.info("Saldo en caja actualizado con exito");
        			 
        		}catch(javax.persistence.NoResultException nre){
        			throw new RDNException("No se encontro el saldo a afectar en la caja.");
        		}
        		
    		
    		
    	}catch(RDNException e){
    		this.context.setRollbackOnly();
    		throw e;
    	}catch(Exception e){
    		e= new Exception("Error al ajustar el saldo de la caja en la tienda.",e);
    		log.error(e,e);
    		this.context.setRollbackOnly();
    		throw e;
    	}
    	
    }
    	
    
    
    
    /*
     * Manda el saldo de la caja a cero
     */
    public void saldarCaja(TiendaCajaEfectivo caja) throws RDNException, Exception{
    	
    	try{
    		
    		log.info("Se ingresa a la opcion de saldar caja");
    		
    		if(caja==null ||  caja.getTienda()==null || caja.getId()==0 || caja.getTienda().getId()==0){
    			throw new RDNException("No se puede registrar movimientos a una caja invalida");
    		}
    		
    		
    		TiendaCajaEfectivo cajaEncontrada= this.getCajaPorTienda( caja.getTienda(), caja.getId());
    		TiendaCajaEfectivoMovimiento movimiento;
    		
    		List<TiendaCajaEfectivoMovimiento> movimientos=new ArrayList<TiendaCajaEfectivoMovimiento>();
    		
    		for(TiendaCajaEfectivoSaldo saldo: cajaEncontrada.getSaldos()){
    			
    			if(saldo.getCantidad()>0){
	    			movimiento= new TiendaCajaEfectivoMovimiento();
	    			movimiento.setDenominacion(saldo.getDenominacion());
	    			movimiento.setCantidad(-1*saldo.getCantidad());
	    			movimiento.setTiendaCaja(cajaEncontrada);
	    			movimientos.add(movimiento);
    			}
    			
    		}
    		
    		if(movimientos.size()>0){
	    		
	    		// ID 6 es po saldar la caja
	    		this.registarSalidaEnCaja(caja, movimientos, 6);
    		}
    		
    		log.info("Se realizo el saldado de la caja");
        		
    		
    		
    	}catch(RDNException e){
    		this.context.setRollbackOnly();
    		throw e;
    	}catch(Exception e){
    		e= new Exception("Error al saldar la caja.",e);
    		log.error(e,e);
    		this.context.setRollbackOnly();
    		throw e;
    	}
    	
    }
    	
    public  List<TiendaCajaEfectivoSaldo> recuperarSaldosPorCaja(TiendaCajaEfectivo caja) throws RDNException, Exception{
    	
	try{
    		
    		log.info("Se ingresa a la opcion de registrar salidas de caja");
    		
    		if(caja==null ||  caja.getTienda()==null || caja.getId()==0 || caja.getTienda().getId()==0){
    			throw new RDNException("No se puede registrar movimientos a una caja invalida");
    		}
    		
    		
    		
    		log.info("Se procede a la consulta de saldos por caja");
    		
        		
        		TypedQuery<TiendaCajaEfectivoSaldo> query=null;
        		
        		try{
        			
        			List<TiendaCajaEfectivoSaldo> saldos=null;
        																																		   
        			 query= metalesEM.createQuery("SELECT Saldo FROM TiendaCajaEfectivoSaldo Saldo where Saldo.cajaEfectivo=:caja order by Saldo.denominacion desc" ,TiendaCajaEfectivoSaldo.class);
        	    	 query.setParameter("caja", caja);
        	    	 saldos=query.getResultList();
        	    
        			 log.info("Saldos en caja recupearados "+saldos.size());
        			 
        			 return saldos;
        			 
        		}catch(javax.persistence.NoResultException nre){
        			throw new RDNException("No se encontros a leer en la caja.");
        		}
        		
    		
    		
    	}catch(RDNException e){
    		this.context.setRollbackOnly();
    		throw e;
    	}catch(Exception e){
    		e= new Exception("Error al ajustar el saldo de la caja en la tienda.",e);
    		log.error(e,e);
    		this.context.setRollbackOnly();
    		throw e;
    	}
    	
    }
    
    
    public List<PagoCompraEfectivo> calcularPagoPorDenominacion(TiendaCajaEfectivo caja,float monto ) throws RDNException,Exception{
    	
    	try{
    		
    		log.info("Se ingresa a la opcion de calcular pago por denominacion");
    		
    		if(caja==null ||  caja.getTienda()==null || caja.getId()==0 || caja.getTienda().getId()==0){
    			throw new RDNException("No se puede registrar movimientos a una caja invalida");
    		}
    		
    		if(monto<=0){
    			throw new RDNException("El monto debe ser mayor de cero");
    		}
    		
    		log.info("Recuperando el saldo de la caja");
    		
    		
    		 List<TiendaCajaEfectivoSaldo> saldos= this.recuperarSaldosPorCaja(caja);
    		 
    		 
    			List<PagoCompraEfectivo> movimientos= new ArrayList<PagoCompraEfectivo>();    			
    			PagoCompraEfectivo movimiento= null;
	    			
	    			int cantidad;
	    			boolean exacto=false;
	    			
	    			for(TiendaCajaEfectivoSaldo denominacion: saldos){
	    				log.info("Denominacion cantidad "+denominacion.getDenominacion()+" "+denominacion.getCantidad());
	    				if( denominacion.getCantidad()>0 &&  monto>=denominacion.getDenominacion()){
	    					
	    					movimiento= new PagoCompraEfectivo();
	    					
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
    		this.context.setRollbackOnly();
    		throw e;
    	}catch(Exception e){
    		e= new Exception("Error al ajustar el saldo de la caja en la tienda.",e);
    		log.error(e,e);
    		this.context.setRollbackOnly();
    		throw e;
    	}
    	
    	
    	
    }

}
