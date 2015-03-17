package ejb.bussines;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import clases.login.UsuarioSesion;
import clases.persistence.jpa.commun.embeddable.UsuarioModifico;
import clases.persistence.jpa.factory.qualifier.MetalesEM;
import clases.vo.tienda.Tienda;
import clases.vo.tienda.caja.TiendaCajaEfectivo;
import clases.vo.tienda.caja.TiendaCajaEfectivoMovimiento;
import clases.vo.tienda.caja.TiendaCajaEfectivoSaldo;
import ejb.bussines.administracion.TiendaCajaEJB;
import ejb.bussines.administracion.TiendaEJB;
import ejb.bussines.administracion.TipoDeCambioEJB;
import ejb.bussines.compra.ClienteEJB;
import ejb.bussines.compra.CompraEJB;
import ejb.bussines.compra.CotizadorEJB;

/**
 * Session Bean implementation class TestEjb
 */
@Stateless
@LocalBean
public class TestEjb {

	
	@Inject
	private UsuarioSesion userSesion;
	
	@Inject @MetalesEM
	private EntityManager metalesEM;
	
	@EJB
	ClienteEJB cliente;
	
	@EJB
	CompraEJB compraEJB;
	
	@EJB
	CotizadorEJB cotizador;
	
	@EJB
	TipoDeCambioEJB tc;
	
	
	@EJB
	TiendaEJB tienda;
	
	@EJB
	TiendaCajaEJB caja;
	
    /**
     * Default constructor. 
     */
    public TestEjb() {
       
    	
    	
    }
    
    public void Test(){
System.out.println("Test EJB");
    	
    	try {
	
    		System.out.println("iniciando");
    		
    		List<TiendaCajaEfectivoSaldo> saldos=null;
    		
    		Tienda tienda= new Tienda(1);
    		TiendaCajaEfectivo caja= new TiendaCajaEfectivo();
    		caja.setId(1);
    		caja.setTienda(tienda);
			
    		saldos= this.caja.recuperarSaldosPorCaja(caja);
    		
    		for(TiendaCajaEfectivoSaldo s: saldos){
    			System.out.println("saldos "+s.getCantidad()+" den "+s.getDenominacion());
    		}
    		
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    }
    
    
    private void venta(){

    	try {
	
    		System.out.println("iniciando");
    		
    		
//    		
//    	
////    		
//    		UsuarioSesionTienda usr= (UsuarioSesionTienda)this.userSesion;
//    		
//    		Cliente c= this.metalesEM.find(Cliente.class, -41);
//    		Metal m= this.metalesEM.find(Metal.class, 1);
//    		
//    		
//    		List<ArticuloCompraMetal> articulos= new ArrayList<ArticuloCompraMetal>();
//    		
//    		ArticuloCompraMetal a1,a2;
//    		
//    		a1= new ArticuloCompraMetal();
//    		a2= new ArticuloCompraMetal();
//    		

    		
    		
    		
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	
    	
    }

}
