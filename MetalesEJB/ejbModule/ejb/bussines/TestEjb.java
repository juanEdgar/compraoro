package ejb.bussines;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import clases.business.metales.vo.compra.ArticuloCompraMetal;
import clases.business.metales.vo.cotizador.Metal;
import clases.login.QualifierUsuarioSesionTienda;
import clases.login.UsuarioSesion;
import clases.login.UsuarioSesionTienda;
import clases.persistence.jpa.factory.qualifier.MetalesEM;
import clases.vo.cliente.Cliente;
import clases.vo.dinero.Moneda;
import ejb.bussines.administracion.TipoDeCambioEJB;
import ejb.bussines.compra.ClienteEJB;
import ejb.bussines.compra.CotizadorEJB;

/**
 * Session Bean implementation class TestEjb
 */
@Stateless
@LocalBean
public class TestEjb {

	
	@Inject @QualifierUsuarioSesionTienda
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
	
    /**
     * Default constructor. 
     */
    public TestEjb() {
       
    	
    	
    }
    
    public void Test(){
System.out.println("Test EJB");
    	
    	try {
	
    		System.out.println("iniciando");
    		
    		
    		Moneda peso= new Moneda(1);
    		Moneda dolar= new Moneda(2);
    		
    		System.out.println(this.tc.getTCPorMoneda(peso, dolar).getValor());
    		System.out.println(this.tc.getTCPorMoneda(dolar, peso).getValor());
    		
    		this.tc.actualizarTipoDeCambio(peso, dolar, 10);
    		
    		
    		
    		System.out.println(this.tc.getTCPorMoneda(peso, dolar).getValor());
    		System.out.println(this.tc.getTCPorMoneda(dolar, peso).getValor());
    		
    		
    		this.tc.actualizarTipoDeCambio(dolar, peso, 10);
    		
    		System.out.println(this.tc.getTCPorMoneda(peso, dolar).getValor());
    		System.out.println(this.tc.getTCPorMoneda(dolar, peso).getValor());
    		
    		
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    }
    
    
    private void venta(){

    	try {
	
    		System.out.println("iniciando");
    		
    		
    		
    	
//    		
    		UsuarioSesionTienda usr= (UsuarioSesionTienda)this.userSesion;
    		
    		Cliente c= this.metalesEM.find(Cliente.class, -41);
    		Metal m= this.metalesEM.find(Metal.class, 1);
    		
    		
    		List<ArticuloCompraMetal> articulos= new ArrayList<ArticuloCompraMetal>();
    		
    		ArticuloCompraMetal a1,a2;
    		
    		a1= new ArticuloCompraMetal();
    		a2= new ArticuloCompraMetal();
    		

    		
    		
    		
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	
    	
    }

}
