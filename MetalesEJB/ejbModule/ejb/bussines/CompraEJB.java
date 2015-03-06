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

import clases.business.metales.vo.compra.ArticuloCompra;
import clases.business.metales.vo.compra.ArticuloCompraMetal;
import clases.business.metales.vo.compra.BitacoraCompra;
import clases.business.metales.vo.compra.Compra;
import clases.login.QualifierUsuarioSesionTienda;
import clases.login.UsuarioSesion;
import clases.login.UsuarioSesionTienda;
import clases.persistence.jpa.commun.embeddable.UsuarioModifico;
import clases.persistence.jpa.factory.qualifier.MetalesEM;
import clases.vo.catalogo.Estatus;
import clases.vo.cliente.Cliente;
import clases.vo.dinero.Moneda;
import clases.vo.tienda.Tienda;
import ejb.bussines.compra.ClienteEJB;
import ejb.bussines.compra.CotizadorEJB;
import ejb.bussines.exception.RDNException;

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
	
	@EJB
	private PropertiesEJB ejbProperties;
	
	@EJB
	private CotizadorEJB ejbCotizador;
	
	private static final Logger log = LogManager .getLogger(CompraEJB.class);
	
    /**
     * Default constructor. 
     */
    public CompraEJB() {
        // TODO Auto-generated constructor stub
    }
    
    
    public void registrarClienteCompra(Cliente c, List<ArticuloCompra> articulos)  throws RDNException ,Exception{
    	
    	try{
	    	Cliente clienteGuardar=c;
	    	
	    	// Se valida que la informacion este completa
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
	    	
	    	Compra compra= new Compra(this.usuarioSesion.getNombreUsuario());
	    	compra.setArticulos(articulos);
	    	compra.setCliente(clienteGuardar);    	
	    	compra.setTienda( tienda );
	    	compra.setEstatus(e);
	    	
	    	this.registarCompraProductos(compra);
	    	
	    	log.info("Compra por cliente y articulos  registrada correctamente");
    	}catch(Exception e){
    		if(!(e instanceof RDNException)){
    			log.error("Error al registar la compra",e);
    		}
    		this.context.setRollbackOnly();
    		throw e;
    	}
    	
    	
    }
    
    
    
    
    private void registarCompraProductos(Compra compra) throws RDNException ,Exception{

		log.info("Registrando la compra de metal");

		// recuperamos el usuario de la sesion
		UsuarioSesionTienda usuario= (UsuarioSesionTienda)this.usuarioSesion;
			
		if(usuario.getTienda()==null || usuario.getTienda().getId()==0){
			throw new Exception("No se puede registrar la compra, tienda en session invalida");
		}
		
		// verificamos los valores de la compra.
		if(compra==null){
			throw new InvalidParameterException("La compra a registrar es nula");
		}
		if(compra.getArticulos()==null || compra.getArticulos().size()==0){
			throw new RDNException("No se puede registrar la compra de lista de artículos vacia");
		}
		
		if(compra.getCliente()==null || compra.getCliente().getIdPersona()==0){
			throw new  RDNException("No se puede realizar la compra a un cliente inexistente");
		}
		
		
		
		
		
		compra.setTienda(usuario.getTienda());
		compra.setUsuarioModifico( new UsuarioModifico( usuario.getNombreUsuario() ));
		
		
		this.metalesEM.persist(compra);
		
		
		 //se registra la bitacora
		
		this.registarBitacora(compra);
		
		
	
//		List<PrecioMetal> precios= new ArrayList<PrecioMetal>();
//		int index=-1;
		
		// se revisan los articulos
		Moneda m = new Moneda();
		m.setId(this.ejbProperties.getMonedaSistema().getId());
		for(ArticuloCompra articulos: compra.getArticulos()){
			
			if(articulos instanceof ArticuloCompraMetal){
				
				ArticuloCompraMetal articuloM=(ArticuloCompraMetal)articulos;
				articuloM.setMoneda(m);
//				index=precios.indexOf(articuloM.getPrecioMetal() );
//				PrecioMetal precio=null;
//				// se recupra el valor actual del precio de gramo
//				if(index<0){
//					precio=this.metalesEM.find(Metal.class,  articuloM.getPrecioMetal().getMetal().getId()  ).getPrecioGramo();
//					precios.add(precio);
//					
//				}
//				else{
//					precio= precios.get(index);
//				}
//				articuloM.setPrecioMetal(precio);
				this.agreagarArticuloCompraMetal(articuloM,compra,usuario);
				
			}
			
		}
		
    			
	
    		
		   	
    }
    
   
    
	private void registarBitacora(Compra compra) {
		
		BitacoraCompra b= new BitacoraCompra(compra);
		b.setUsuarioModifico(new UsuarioModifico( this.usuarioSesion.getNombreUsuario()));
		this.metalesEM.persist(b);
	}
    
    
    private void agreagarArticuloCompraMetal(ArticuloCompraMetal articulo, final Compra compra, final UsuarioSesionTienda usuario) throws Exception{
    	
    	log.info("Guardando lista de articulos de la compra");
    	
    	if(articulo.getPesoNeto()<=0.0F || articulo.getPesoBruto()<=0.0F ){
			throw new RDNException("Alguno de los artículos a comprar no tiene registrado el peso neto");
		}
		if(articulo.getPrecioMetal()==null  || articulo.getPrecioMetal().getId()==0){
			throw new RDNException("No se puede realizar la compra, alguno de los articulos no tiene el precio base especificado");
		}
		
		if(articulo.getPrecioMetal().getMetal()==null  || articulo.getPrecioMetal().getMetal().getId()==0){
			throw new RDNException("No se puede realizar la compra, alguno de los articulos no tiene el tipo de metal al que pertenece");
		}
		
		
		if(articulo.getPorcentajePureza()==0.0F){
			throw new RDNException("No se puede realizar la compra, existen artículos con pureza cero");
		}
		
		log.info("Validaciones terminadas, se guarda el articulo");
		
				
		// se guarda el usuario modifico con el que se registrara la venta
		articulo.setUsuarioModifico(new UsuarioModifico(usuario.getNombreUsuario()));
		articulo.setCompra(compra);

		// si no tienen el peso fino, se calcula
		articulo.setPesoFino(articulo.getPesoNeto()*articulo.getPorcentajePureza()/100.00F);
		articulo.setMoneda(this.ejbProperties.getMonedaSistema());
		articulo.getPrecioMetal().getMetal().setPorcentajePureza(articulo.getPorcentajePureza());
	
	
		this.metalesEM.persist(articulo);

    	
    }



}
