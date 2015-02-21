package ejb.bussines;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import clases.business.metales.vo.compra.ArticuloCompraMetal;
import clases.business.metales.vo.compra.CompraMetal;
import clases.business.metales.vo.cotizador.Metal;
import clases.business.metales.vo.cotizador.PurezaMetal;
import clases.login.QualifierUsuarioSesionTienda;
import clases.login.UsuarioSesion;
import clases.login.UsuarioSesionTienda;
import clases.persistence.jpa.commun.embeddable.UsuarioModifico;
import clases.persistence.jpa.factory.qualifier.MetalesEM;
import clases.vo.cliente.Cliente;
import clases.vo.dinero.Moneda;
import clases.vo.tienda.Tienda;
import ejb.bussines.administracion.TipoDeCambioEJB;
import ejb.bussines.venta.ClienteEJB;
import ejb.bussines.venta.CotizadorEJB;

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
    		
    		a1.setDescripcion("Cadena de 24");
    		a1.setPesoBruto(10.00f);
    		a1.setPesoNeto(10f);
    		a1.setPrecioMetal(m.getPrecioGramo());
    		a1.setPureza(new PurezaMetal(7,m));
    		
    		a2.setDescripcion("Cadena de 14");
    		a2.setPesoBruto(10.00f);
    		a2.setPesoNeto(10f);
    		a2.setPrecioMetal(m.getPrecioGramo());
    		a2.setPureza(new PurezaMetal(18,m));
    		
    		
    		articulos.add(a1);
    		articulos.add(a2);
    		
    		CompraMetal compra= new CompraMetal();
    		
    		compra.setArticulos(articulos);
    		compra.setCliente(c);
    		compra.setUsuarioModifico(new UsuarioModifico(usr.getNombreUsuario()));
    		
    		System.out.println("Se detalla la compra");
    		
    		for(ArticuloCompraMetal a: compra.getArticulos()){
    			
    			m.setPurezaMetal(a.getPureza());
    			System.out.println(String.format("Precio %s = %f",a.getDescripcion(),this.cotizador.cotizar(m, a.getPesoNeto())));
    			
    			
    		}
    		
    		
    		
    		this.compraEJB.registarCompra(compra);
    		
    		
    		
    		
    		
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	
    	
    }

}
