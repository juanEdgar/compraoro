package ejb.bussines.compra;

import java.security.InvalidParameterException;
import java.util.Date;

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

import clases.business.metales.vo.compra.ArticuloCompra;
import clases.business.metales.vo.compra.ArticuloCompraMetal;
import clases.business.metales.vo.compra.BitacoraCompra;
import clases.business.metales.vo.compra.Compra;
import clases.business.metales.vo.compra.PagoCompra;
import clases.business.metales.vo.compra.PagoCompraEfectivo;
import clases.business.metales.vo.compra.PagoCompraSPEI;
import clases.business.metales.vo.compra.Seguribolsa;
import clases.login.UsuarioSesion;
import clases.persistence.jpa.commun.embeddable.UsuarioModifico;
import clases.persistence.jpa.factory.qualifier.MetalesEM;
import clases.vo.catalogo.Estatus;
import clases.vo.cliente.Cliente;
import clases.vo.dinero.Moneda;
import clases.vo.tienda.Tienda;
import ejb.bussines.PropertiesEJB;
import ejb.bussines.exception.RDNException;

/**
 * Session Bean implementation class CompraEJB
 */
@Stateless
@LocalBean
public class CompraEJB {

	@Inject
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
    
    
    
 public void registrarClienteCompra(Cliente c, Compra compra)  throws RDNException ,Exception{
    	
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
	    	
	    	if(compra==null || compra.getBolsas()==null || compra.getBolsas().size()<0){
	    		throw new RDNException("No se puede registrar una venta sin bolsas, la lista esta vacía");
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
	    	
	    	Tienda tienda= this.usuarioSesion.getTienda();
	    	Estatus e= new Estatus ();
	    	e.setId(1);
	    	
	    	compra.setUsuarioModifico(new UsuarioModifico(this.usuarioSesion.getNombreUsuario()));
	    	
	    	compra.setCliente(clienteGuardar);    	
	    	compra.setTienda( tienda );
	    	compra.setEstatus(e);
	    	
	    	compra.setTienda(this.usuarioSesion.getTienda());
			compra.setUsuarioModifico( new UsuarioModifico( this.usuarioSesion.getNombreUsuario() ));
			
			
			this.metalesEM.persist(compra);
			
			log.info("Compra generada ID" + compra.getId());
			
			 //se registra la bitacora
			
			this.registarBitacora(compra);
			
			// se registran los pagos
			log.info("Registrando pago");
			this.registrarPagoCompra(compra);
	    	
			log.info("pago registrado");
			
	    	// se dan de alta las bolsas de seguridad
	    	Seguribolsa bolsaCreada;
	    	for(Seguribolsa bolsa: compra.getBolsas()){
	    		bolsa.setCompra(compra);
	    		bolsaCreada=this.registrarSeguribolsa(bolsa);
	    		// se guarda cada uno de los articulos
	    		this.registarArticulos(bolsaCreada);
	    	}
	    	
	    	
	    	log.info("Compra por cliente y articulos  registrada correctamente");
    	}catch(Exception e){
    		if(!(e instanceof RDNException)){
    			log.error("Error al registar la compra",e);
    		}
    		this.context.setRollbackOnly();
    		throw e;
    	}
    	
    	
    }
    
    
    

    
    
    
    private void registarArticulos(Seguribolsa bolsa) throws RDNException ,Exception{

		log.info("Registrando la compra de metal");

		
			
		if(this.usuarioSesion.getTienda()==null || this.usuarioSesion.getTienda().getId()==0){
			throw new Exception("No se puede registrar la compra, tienda en session invalida");
		}
		
		
		if(bolsa.getArticulosCompra()==null || bolsa.getArticulosCompra().size()==0){
			throw new RDNException("No se puede registrar la compra de lista de artículos vacia");
		}
		
		// se revisan los articulos
		Moneda m = new Moneda();
		m.setId(this.ejbProperties.getMonedaSistema().getId());
		for(ArticuloCompra articulo: bolsa.getArticulosCompra()){
			
			//se coloca la moneda del articlo
			articulo.setMoneda(m);
			
			
			
			if(articulo instanceof ArticuloCompraMetal){				

				this.registrarArticuloCompraMetal((ArticuloCompraMetal)articulo,  bolsa);
				
			}
			
		}
		
    			
	
    		
		   	
    }
    
	
	private Seguribolsa registrarSeguribolsa(Seguribolsa bolsa) throws RDNException, Exception{
		
		log.info("registro de seguribolsa");
		
		if(bolsa==null || bolsa.getCodigo()==null || bolsa.getCodigo().trim().equals("")){
			throw new RDNException("No se puede guardar un codigo de seguribolsa invalido");
		}
		
		bolsa.setUsuarioModifico(new UsuarioModifico(this.usuarioSesion.getNombreUsuario()));
		bolsa.setId(0);
		this.metalesEM.persist(bolsa);
		
		log.info("Seguribolsa generada ID "+bolsa.getId());
		
		return bolsa;
		
		
	}
    
	private void registarBitacora(Compra compra) {
		
		BitacoraCompra b= new BitacoraCompra(compra);
		b.setUsuarioModifico(new UsuarioModifico( this.usuarioSesion.getNombreUsuario()));
		this.metalesEM.persist(b);
	}
    
    
    private void registrarArticuloCompraMetal(ArticuloCompraMetal articulo , Seguribolsa bolsa) throws Exception{
    	
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
		articulo.setUsuarioModifico(new UsuarioModifico(this.usuarioSesion.getNombreUsuario()));
		articulo.setSeguribolsa(bolsa);

		// si no tienen el peso fino, se calcula
		articulo.setPesoFino(articulo.getPesoNeto()*articulo.getPorcentajePureza()/100.00F);
		articulo.setMoneda(this.ejbProperties.getMonedaSistema());
		articulo.getPrecioMetal().getMetal().setPorcentajePureza(articulo.getPorcentajePureza());
		articulo.setId(0);
	
		this.metalesEM.persist(articulo);

    	
    }

    public Seguribolsa buscarSeguribolsaPorCodigo(String codigoSeguridad) throws RDNException, Exception{
    	log.info("buscando bolsa de seguridad : ["+codigoSeguridad+"]");
    	if(codigoSeguridad==null || codigoSeguridad.trim().equals("")){
    		throw new RDNException("Se debe especificar un código de bolsa válido");
    	}
    	
    	try{
    		
    		Seguribolsa bolsa=null;
    		
    		TypedQuery<Seguribolsa> query=null;
    		 
    		try{
    			
    			
    			
    			 query= metalesEM.createQuery("SELECT B FROM Seguribolsa B where B.codigo= :codigo" ,Seguribolsa.class);
    	    	 query.setParameter("codigo", codigoSeguridad);
    	    	 bolsa=query.getSingleResult();
    	    	log.info("Bolsa encontrada");
    			
    		}catch(javax.persistence.NoResultException nre){
    			log.info("Bolsa no existe");
    			return null;
    			
    		}
    		return bolsa;
    		
    	}catch(Exception e){
    		e= new Exception("Error al validar la existencia del codigo de seguribolsa ",e);
    		log.error(e,e);
    		throw e;
    	}
    	
    	
    }
    
    
    
    public void registrarPagoCompra(Compra compra) throws Exception{
    	log.info("Registrando el pago de la compra");
    	try{
    		
    		
    		if(compra==null || compra.getId()==0){
    			throw new RDNException("No se puede registrar el pago de una compra inválida");
    		}
    		
    		if(compra.getPagos()==null || compra.getPagos().size()<=0){
    			throw new RDNException("La lista de pagos esta vacía");
    		}
    		
    		Moneda moneda= this.ejbProperties.getMonedaSistema();
    		UsuarioModifico usuarioM= new UsuarioModifico(this.usuarioSesion.getNombreUsuario());
    		
    		for(PagoCompra pago : compra.getPagos()  ){
    			
    			pago.setCompra(compra);
    			pago.setMoneda(moneda);
    			pago.setUsuarioModifico(usuarioM);
    			pago.setEstatus(1);
    			pago.setFecha(new Date());
    			// TODO registar baja de monto de caja
    			if( pago instanceof PagoCompraEfectivo ){
    				this.metalesEM.persist((PagoCompraEfectivo)pago);
    			}
    			
    			if( pago instanceof PagoCompraSPEI ){
    				this.metalesEM.persist((PagoCompraSPEI)pago);
    			}
    			
    			
    		}
    		
    		log.info("Pago de compra registrado con exito");
    		
    	}catch(RDNException e){
    		this.context.setRollbackOnly();
    		throw e;
    	}
    	catch( Exception   e){
    		
    		e= new Exception("Error al registrar el pago de la compra",e);
    		
    		log.error(e,e);
    		
    		this.context.setRollbackOnly();
    		throw e;
    		
    	}
    }
    


}
