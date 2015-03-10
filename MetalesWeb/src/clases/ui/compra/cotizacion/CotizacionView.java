package clases.ui.compra.cotizacion;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import clases.business.metales.vo.compra.ArticuloCompra;
import clases.business.metales.vo.compra.ArticuloCompraMetal;
import clases.business.metales.vo.compra.Seguribolsa;
import clases.business.metales.vo.cotizador.Diamante;
import clases.business.metales.vo.cotizador.Metal;
import clases.business.metales.vo.cotizador.Producto;
import clases.login.UsuarioSesion;
import clases.persistence.jpa.factory.qualifier.MetalesEM;
import ejb.bussines.MetalEJB;
import ejb.bussines.compra.CompraEJB;
import ejb.bussines.compra.CotizadorEJB;
import ejb.bussines.exception.RDNException;


@ManagedBean
@ViewScoped
public class CotizacionView  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1100486514401503873L;




	private final Logger log = LogManager.getLogger(CotizacionView.class);
	
	
	
	
	@Inject
	private CompraView compraView;
	
	@EJB
	private CotizadorEJB EjbCotizador;
	
	@EJB
	private MetalEJB EjbMetal;
	
	@EJB
	private CompraEJB EjbCompra;
	
	@Inject @MetalesEM
	private EntityManager metalesEM;
	
	private List<Producto> productos;		
	private int idProductoSeleccionado=0;

	private boolean isMetalSeleccionado=false;
	private ArticuloCompraMetal articuloMetalCotizacion;
	private String seguribolsa="";
	
	
	public CotizacionView() {
		
	}
	
	

	
	
	@PostConstruct
	private void init(){
		
		// Se inicializa la lista de metales
		this.productos= new ArrayList<>(); 
		
		// se carga la lista de metales comercializables
		this.productos.addAll(this.EjbMetal.getMetalesComercializables());
		Diamante diamante= new Diamante(500);
		diamante.setNombre("DIAMANTES");
		this.productos.add(diamante);
		
		log.info("Lista de productos recuperados: "+this.productos!=null? this.productos.size():"NULL");
		
		
		
	}
	

	
	
	
	public void eventCotizar(){
		
		
		try{
			
			log.debug("Iniciando el evento de cotizador");
			
			
			
			Object coincidencia= this.getProductoSeleccionado();
			
			if( coincidencia  instanceof Metal) {
				this.cotizarMetal();
				
			}		
			
		}catch(Exception e){
			log.info("Error al obtener la cotizacion");
		}
			
		
	}
	
	
	private void cotizarMetal(){
		try{
			
			
			if(this.articuloMetalCotizacion.getPesoBruto()>0.0 && this.articuloMetalCotizacion.getPesoNeto()==0.0F){
				this.articuloMetalCotizacion.setPesoNeto(this.articuloMetalCotizacion.getPesoBruto());
			
			}
			
			if(this.idProductoSeleccionado!=0 && this.articuloMetalCotizacion.getPesoNeto()>0.0F && this.articuloMetalCotizacion.getPorcentajePureza()>0.0F){
				
				Metal m= new Metal(this.idProductoSeleccionado);
				
				m.setPorcentajePureza(this.articuloMetalCotizacion.getPorcentajePureza());
				
				this.articuloMetalCotizacion.setValor(
													this.EjbCotizador.cotizarRedondeada(m, this.articuloMetalCotizacion.getPesoNeto())
													);
				
				this.articuloMetalCotizacion.setPesoFino( this.articuloMetalCotizacion.getPesoNeto()*this.articuloMetalCotizacion.getPorcentajePureza()/100F);
				
			}
			else{
				this.articuloMetalCotizacion.setValor(0.0F);
			}
			
			log.debug("Cotizacion: "+this.articuloMetalCotizacion.getValor());
		}catch(RDNException e){
			setMensage(FacesMessage.SEVERITY_WARN, "Cuidado", e.getMessage());
		}catch (Exception e) {
			log.info("Error al mandar a llamar la cotizacion", e);
			setMensage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage());
		}
	}
	
	public void eventProductoSeleccionado(){
		

		log.debug("Se selecciono un producto");
		
		this.isMetalSeleccionado=false;
		
		if(this.idProductoSeleccionado!=0){
		
				this.isMetalSeleccionado=false;
				Producto  producto=this.getProductoSeleccionado();
				
				if(producto instanceof Metal){
					log.debug("Se selecciono un Metal");
					this.eventMetalSeleccionado();
				}
		}
		
		
		
	}
	
	
	private void eventMetalSeleccionado(){
		this.isMetalSeleccionado= true;
		if(this.articuloMetalCotizacion==null){
			this.articuloMetalCotizacion= new ArticuloCompraMetal();
		}
		this.cotizarMetal();
		
	}
	
	public void eventQuitarArticulo(String llave){
		
		log.debug("Eliminando articulo de la lista: "+llave);
		
		
		String llaves[]=llave.split("-");
		
		if(llaves.length!=2){
			this.setMensage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo obtener la llave de borrado");
		}
		
		try{
			this.compraView.quitarArticulo(Integer.valueOf(llaves[0]), Integer.valueOf(llaves[1]));
		
		}catch(Exception e){
			log.error("Error al borrar el articulo de la lista",e);
			this.setMensage(FacesMessage.SEVERITY_ERROR, "Error", "No se ṕodo obtener la llave de borrado. "+e.getMessage());
		}
		log.debug("Articulo eliminado");
		
	}
	
	public void eventAgregarArticulo(){
		
		log.debug("Agragando articulo bolsa "+this.getSeguribolsa());
		
		
		if( this.seguribolsa==null || this.seguribolsa.trim().equals("") ){
			setMensage(FacesMessage.SEVERITY_WARN, "Cuidado", "Debe agregar el código de bolsa de seguridad");
			return;
		}
		
		try {
			log.debug("buscando bolsa");
			Seguribolsa s=this.EjbCompra.buscarSeguribolsaPorCodigo(this.seguribolsa);
			if(s!=null ){
				log.debug("la bolsa existe  ");
				throw new RDNException("La seguribolsa que intenta ingresar ya existe, cambiar código");
			}
		} catch (RDNException e) {
			setMensage(FacesMessage.SEVERITY_WARN, "Cuidado", e.getMessage());
			return;
		}catch( Exception e ){
			log.error(e);
			setMensage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage());
			return;
		}
		
		if(!isMetalSeleccionado){
			setMensage(FacesMessage.SEVERITY_WARN, "Cuidado", "Debe seleccionar un producto válido");
			return;
		}
		
		
		
		if(isMetalSeleccionado){
			if(!this.validarCotizacionMetal()){
				log.debug("No se puede agragar producto, faltan campos");
				return;
			}
			log.debug("Agregando producto");
			this.agregarArticuloMetal();
			
		}
		
		
		// se resetea el valor del combo
		this.idProductoSeleccionado=0;
	
		
	}
	
	
	private void agregarArticuloMetal(){
		
		log.debug("Agregando articulo metal");		
		
		
		Metal mseleccionado= (Metal) this.getProductoSeleccionado();
		
		Metal m= new Metal(mseleccionado.getId());
		m.setNombre(mseleccionado.getNombre());
		
		this.articuloMetalCotizacion.setPrecioMetal(mseleccionado.getPrecioGramo());
		this.articuloMetalCotizacion.getPrecioMetal().setMetal(m);
		
		log.debug("Se agrega producto metal ID precio ID "+this.articuloMetalCotizacion.getPrecioMetal().getId()+" "+this.articuloMetalCotizacion.getPrecioMetal().getMetal().getId());
		
		try {
			this.compraView.agregarArticulo(this.articuloMetalCotizacion, this.seguribolsa, mseleccionado.getNombre());
		} catch (RDNException e) {
			this.setMensage(FacesMessage.SEVERITY_ERROR, "Cuidado", e.getMessage());
			return;
		}finally{
			this.articuloMetalCotizacion= new ArticuloCompraMetal();
			this.isMetalSeleccionado=false;
		}
		 
		

		log.debug("Articulo agregado");
		
		
	}
	
	private Producto getProductoSeleccionado(){
		Producto productoSeleccionado= new Producto() ;
		productoSeleccionado.setId(this.idProductoSeleccionado);
		
		int index=this.productos.indexOf(productoSeleccionado);
		if(index<0){
			log.error("Error al recuperar el produco seleccionado");
			this.setMensage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo obtener el ID delproducto seleccionado");
			return null;
		}
		productoSeleccionado=this.productos.get(index);		
		return productoSeleccionado;
	}
	
	
	
	private  boolean validarCotizacionMetal(){
		
		// resultado cotizacion se valida en el metodo cotizar(), donde se revisa el resto de los campos
		if(	this.articuloMetalCotizacion.getValor()<=0.0F || this.articuloMetalCotizacion.getPesoBruto()<=0.0||
			this.articuloMetalCotizacion.getPesoNeto()<=0 || this.articuloMetalCotizacion.getDescripcion()==null
			|| this.articuloMetalCotizacion.getDescripcion().trim().equals("")|| this.articuloMetalCotizacion.getPorcentajePureza()<=0.0F
		){
			
			
			this.setMensage(FacesMessage.SEVERITY_WARN, "Cuidado", "Debe llenar todos los campos obligatorios");
			return false;
	
		}
	
		return true;
	}
	
	public String cotizacionFormateada(){
		
		DecimalFormat df2 = new DecimalFormat( "#,###,###,##0.00" );	
		
		if(isMetalSeleccionado){
			return "$ "+df2.format( this.articuloMetalCotizacion.getValor() );
		}
		
		return "$ "+df2.format( 0.0F );
	}

	private void setMensage(Severity severity, String titulo, String Mensage){
		FacesContext context= FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(severity,titulo,Mensage));
		
	}



	public List<Producto> getProductos() {
		return productos;
	}





	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}









	public int getIdProductoSeleccionado() {
		return idProductoSeleccionado;
	}





	public void setIdProductoSeleccionado(int idProductoSeleccionado) {
		this.idProductoSeleccionado = idProductoSeleccionado;
	}








	

	public boolean isMetalSeleccionado() {
		return isMetalSeleccionado;
	}





	public void setMetalSeleccionado(boolean isMetalSeleccionado) {
		this.isMetalSeleccionado = isMetalSeleccionado;
	}







	public ArticuloCompraMetal getArticuloMetalCotizacion() {
		return articuloMetalCotizacion;
	}





	public void setArticuloMetalCotizacion(
			ArticuloCompraMetal articuloMetalCotizacion) {
		this.articuloMetalCotizacion = articuloMetalCotizacion;
	}





	public String getSeguribolsa() {
		return seguribolsa;
	}





	public void setSeguribolsa(String seguribolsa) {
		this.seguribolsa = seguribolsa.toUpperCase().trim();
	}



	
  

	
	

	
	
	
	
}
