package ejb.bussines;

import java.security.InvalidParameterException;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import clases.business.metales.vo.compra.ArticuloCompraMetal;
import clases.business.metales.vo.compra.BitacoraCompraMetal;
import clases.business.metales.vo.compra.CompraMetal;
import clases.login.QualifierUsuarioSesionTienda;
import clases.login.UsuarioSesion;
import clases.login.UsuarioSesionTienda;
import clases.persistence.jpa.commun.embeddable.UsuarioModifico;
import clases.persistence.jpa.factory.qualifier.MetalesEM;
import clases.vo.catalogo.Estatus;
import clases.vo.cliente.Cliente;
import clases.vo.tienda.Tienda;
import ejb.bussines.exception.RDNException;
import ejb.bussines.venta.ClienteEJB;

/**
 * Session Bean implementation class CompraEJB
 */
@Stateless
@LocalBean
public class CompraEJB {

	@Inject @QualifierUsuarioSesionTienda
	private UsuarioSesion usuarioSesion;
	
	@Inject @MetalesEM
	private EntityManager metalesEM;
	
	@Resource
	private SessionContext context;
	
	@EJB
	private ClienteEJB clienteEJB;
	
	private static final Logger log = LogManager .getLogger(CompraEJB.class);
	
    /**
     * Default constructor. 
     */
    public CompraEJB() {
        // TODO Auto-generated constructor stub
    }
    
    
    public void registrarCompra(Cliente c, List<ArticuloCompraMetal> articulos)  throws RDNException ,Exception{
    	
    	
    	Cliente clienteGuardar=c;
    	
    	// Se valida que la informaci�n este completa
    	log.info("registrando compra por cliente y lista de articulos");
    	if(c==null){
    		throw new InvalidParameterException("No se puede registrar la compra con la información del cliente NULL");
    	}
    	
    	if(c.getIdPersona()==0 &&  !this.clienteEJB.validarInformacionAlta(c)) 
    	{
    		throw new RDNException("La información para dar de alta el cliente es incompleta");
    	
    	}
    	
    	if(articulos==null || articulos.size()==0){
    		throw new RDNException("No se puede registrar una venta sin artículos, la lista esta vacía");
    	}
    	
    	// se verifica si se da de alta al cliente
    	
    	if(c.getIdPersona()==0){
    		log.info("Se procede a dar de alta al cliente");
    		
    		UsuarioModifico usrM= new UsuarioModifico(this.usuarioSesion.getNombreUsuario());
    		c.setUsuarioModificoCliente(usrM);
    		
    		clienteGuardar=this.clienteEJB.altaCliente(c);
    		
    		if(clienteGuardar.getIdPersona()==0){
    			throw new Exception("No se pudo crear el cliente en BD. ID=0");
    		}
    		
    		log.debug("Cliente creado correctamente");
    	}
    	
    	// se da de alta la compra con sus articulos
    	
    	Tienda tienda= ((UsuarioSesionTienda)this.usuarioSesion).getTienda();
    	Estatus e= new Estatus ();
    	e.setId(1);
    	
    	CompraMetal compra= new CompraMetal(this.usuarioSesion.getNombreUsuario());
    	compra.setArticulos(articulos);
    	compra.setCliente(clienteGuardar);    	
    	compra.setTienda( tienda );
    	compra.setEstatus(e);
    	
    	this.registarCompra(compra);
    	
    	log.info("Compra por cliente y articulos  registrada correctamente");
    	
    	
    }
    
    public void registarCompra(CompraMetal compraMetal) throws RDNException ,Exception{

		log.info("Registrando la compra de metal");

		// recuperamos el usuario de la sesion
		UsuarioSesionTienda usuario= (UsuarioSesionTienda)this.usuarioSesion;
			
		if(usuario.getTienda()==null || usuario.getTienda().getId()==0){
			throw new Exception("No se puede registrar la compra, tienda en session invalida");
		}
		
		// verificamos los valores de la compra.
		if(compraMetal==null){
			throw new InvalidParameterException("La compra a registrar es nula");
		}
		if(compraMetal.getArticulos()==null || compraMetal.getArticulos().size()==0){
			throw new RDNException("No se puede registrar la compra de lista de artículos vacia");
		}
		
		if(compraMetal.getCliente()==null || compraMetal.getCliente().getIdPersona()==0){
			throw new  RDNException("No se puede realizar la compra a un cliente inexistente");
		}
		
		// se revisan los articulos
		for(ArticuloCompraMetal articulos: compraMetal.getArticulos()){
			if(articulos.getPesoNeto()<=0.0F ){
				throw new RDNException("Alguno de los artículos a comprar no tiene registrado el peso neto");
			}
			if(articulos.getPrecioMetal()==null  || articulos.getPrecioMetal().getId()==0){
				throw new RDNException("No se puede realizar la compra, alguno de los articulos no tiene el precio base especificado");
			}
			
			if(articulos.getPureza()==null || articulos.getPureza().getId()==0){
				throw new RDNException("No se puede realizar la compra, alguno de los articulos no tiene especificada su pureza idPureza");
			}
			
			if(articulos.getPureza().getMetal()==null  || articulos.getPureza().getMetal().getId()==0){
				throw new RDNException("No se puede realizar la compra, alguno de los articulos no tiene especificada su pureza idMetal");
			}
			
			// se guarda el usuario modifico con el que se registrara la venta
			articulos.setUsuarioModifico(new UsuarioModifico(usuario.getNombreUsuario()));
			articulos.setCompraMetal(compraMetal);
			
			
			
			
		}
		
    	try {
    		
    		compraMetal.setTienda(usuario.getTienda());
    		compraMetal.setUsuarioModifico( new UsuarioModifico( usuario.getNombreUsuario() ));
    		
    		System.out.println("Id compra antes de guardar: "+compraMetal.getId());
    		this.metalesEM.persist(compraMetal);
    		System.out.println("Id compra despues de guardar: "+compraMetal.getId());
    		
    		for(ArticuloCompraMetal articulos: compraMetal.getArticulos()){    			
    			this.metalesEM.persist(articulos);
    			
    		}
    		
    		 //se registra la bitacora
    		
    		this.registarBitacora(compraMetal);
    		
		} catch (Exception e) {
			log.error("Error al registrar la compra",e);
			throw e;
		}
    	
    	
    	
    }


	private void registarBitacora(CompraMetal compraMetal) {
		
		BitacoraCompraMetal b= new BitacoraCompraMetal(compraMetal);
		b.setUsuarioModifico(new UsuarioModifico( this.usuarioSesion.getNombreUsuario()));
		this.metalesEM.persist(b);
	}
    
    

}
