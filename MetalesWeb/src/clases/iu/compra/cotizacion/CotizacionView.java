package clases.iu.compra.cotizacion;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import clases.business.metales.vo.cotizador.Metal;
import clases.business.metales.vo.cotizador.PurezaMetal;
import clases.cotizacion.dto.ArticuloCotizar;
import clases.login.QualifierUsuarioSesionTienda;
import clases.login.UsuarioSesion;
import clases.login.UsuarioSesionTienda;
import clases.persistence.jpa.factory.qualifier.MetalesEM;
import clases.vo.tienda.Tienda;
import ejb.bussines.MetalEJB;
import ejb.bussines.venta.CotizadorEJB;


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
	
	@Inject @MetalesEM
	private EntityManager metalesEM;
	
	@QualifierUsuarioSesionTienda
	private UsuarioSesion usuarioSesion;
	
	private List<Metal> productos;
	private List<PurezaMetal> purezas;
	private float pesoNeto;
	private float pesoBruto;
	private float resultadoCotizacion;
	
	private int idMetalSeleccionado=0;
	private int idPurezaSeleccionada=0;
	private String descripcion;
	
	
	public CotizacionView() {
		
	}
	
	

	
	
	@PostConstruct
	private void init(){
		
		// Se inicializa la lista de metales
		this.productos= this.EjbMetal.getMetalesComercializables();
		log.info("Lista de metaes recuperados: "+this.productos!=null? this.productos.size():"NULL");
		
		
	}
	

	
	
	
	public void eventCotizar(){
		
		
		try{
			
			log.debug("Iniciando el evento de cotizador");
			if(this.idMetalSeleccionado!=0 && this.idPurezaSeleccionada!=0 && this.pesoNeto>0.0){
				
				Metal m= new Metal(this.idMetalSeleccionado);
				PurezaMetal p= new PurezaMetal(this.idPurezaSeleccionada, m);
				m.setPurezaMetal(p);
				
				this.resultadoCotizacion=this.EjbCotizador.cotizar(m, this.pesoNeto);
				
				this.pesoBruto=this.pesoNeto;
			}
			else{
				this.resultadoCotizacion=0.0F;
			}
			
			log.debug("Cotizacion: "+this.resultadoCotizacion);
			
		}catch(Exception e){
			log.info("Error al obtener la cotizacion");
		}
		
	}
	
	public void eventProductoChange(){
		
		if(this.idMetalSeleccionado==0){
			this.resultadoCotizacion=0.0F;
			this.idPurezaSeleccionada=0;
			return;
		}
		
		log.debug("Se llena la lista de purezas");
		this.idPurezaSeleccionada=0;				
		
		Metal  m=this.getSeleccionMetal();
		
		log.debug("Se consulta la lista de purezas");
		m.setListaPurezas(this.EjbMetal.getListaPurezas(m));
			
		
		
		this.purezas=  m.getListaPurezas();
		
		
		log.debug("OK, lista de purezas llena");
		
	}
	
	public void eventQuitarArticulo(int numero){
		
		log.debug("Eliminando articulo de la lista: "+numero);
		
		ArticuloCotizar a= new ArticuloCotizar();
		a.setNumeroArticulo(numero);
		
		this.compraView.getArticulos().remove(this.compraView.getArticulos().indexOf(a));
		
		log.debug("Articulo eliminado");
		
	}
	
	public void eventAgregarArticulo(){
		
		log.debug("Agragando articulo");
		
		if(this.resultadoCotizacion<=0.0F || this.pesoBruto<=0.0 ){
			log.debug("No se puede agregar una cotizacion sin datos");
			return;
		}
		
		ArticuloCotizar a = new ArticuloCotizar();
		a.setDescripcion(this.descripcion);
		a.setPesoBruto(this.pesoBruto);
		a.setPesoNeto(this.pesoNeto);		
		a.setCotizacion(this.resultadoCotizacion);
		a.setNumeroArticulo(this.compraView.getNumeroArtculo());
		
		Metal mseleccionado= this.getSeleccionMetal();
		
		Metal m= new Metal(mseleccionado.getId());
		m.setNombre(mseleccionado.getNombre());
		m.setEstatus(1);
		m.setPurezaBase(mseleccionado.getPurezaBase());
		m.setPrecioGramo(mseleccionado.getPrecioGramo());
		
		a.setPureza(this.getSeleccionPureza());
		a.setTipoProducto(m);
		a.getTipoProducto().setPurezaMetal(this.getSeleccionPureza());
		a.setPrecioMetal(mseleccionado.getPrecioGramo());
		
		this.compraView.getArticulos().add(a);
		
		
		this.resultadoCotizacion=0.0F;
		this.idPurezaSeleccionada=0;
		this.pesoBruto=0.0F;
		this.pesoNeto=0.0F;
		this.descripcion=null;
		log.debug("Articulo agregado");
	
		
	}
	
	
	private PurezaMetal getSeleccionPureza(){
	
		PurezaMetal pureza=new PurezaMetal();
		pureza.setId(this.idPurezaSeleccionada);
		
		int index	=  this.getSeleccionMetal().getListaPurezas().indexOf(pureza);
		
		pureza		=  this.getSeleccionMetal().getListaPurezas().get(index);
		
		return   pureza;
	}
	
	private Metal getSeleccionMetal(){
		
		Metal metalSeleccionado= new Metal();
		metalSeleccionado.setId(this.idMetalSeleccionado);
		
		int index=this.productos.indexOf(metalSeleccionado);
		if(index<0){
			log.error("Error al recuperar el metal seleccionado");
		}
		metalSeleccionado=this.productos.get(index);		
		return metalSeleccionado;
	}
	
	
	
	public  void validarCotizacion(){
		// resultado cotizacion se valida en el metodo cotizar(), donde se revisa el resto de los campos
		if(this.resultadoCotizacion<=0.0F || this.pesoBruto<=0.0){
			
			FacesContext context= FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Cuidado", "Debe especificar los parametos obligatorios (marcados con * ) para poder agregar un producto a la lista "));
			
			log.debug("Se muestra el mesanje");
	
		}
	
		return;
	}
	
	public String cotizacionFormateada(){
		
		DecimalFormat df2 = new DecimalFormat( "#,###,###,##0.00" );		
		return "$ "+df2.format( this.resultadoCotizacion );
	}
	
	
	


	public List<Metal> getProductos() {
		return productos;
	}


	public void setProductos(List<Metal> productos) {
		this.productos = productos;
	}


	public List<PurezaMetal> getPurezas() {
		return purezas;
	}


	public void setPurezas(List<PurezaMetal> purezas) {
		this.purezas = purezas;
	}


	public float getPesoNeto() {
		return pesoNeto;
	}


	public void setPesoNeto(float pesoNeto) {
		this.pesoNeto = pesoNeto;
	}


	public float getPesoBruto() {
		return pesoBruto;
	}


	public void setPesoBruto(float pesoBruto) {
		this.pesoBruto = pesoBruto;
	}


	public float getResultadoCotizacion() {
		return resultadoCotizacion;
	}


	public void setResultadoCotizacion(float resultadoCotizacion) {
		this.resultadoCotizacion = resultadoCotizacion;
	}


	public int getIdMetalSeleccionado() {
		return idMetalSeleccionado;
	}


	public void setIdMetalSeleccionado(int idMetalSeleccionado) {
		this.idMetalSeleccionado = idMetalSeleccionado;
	}


	public int getIdPurezaSeleccionada() {
		return idPurezaSeleccionada;
	}


	public void setIdPurezaSeleccionada(int idPurezaSeleccionada) {
		this.idPurezaSeleccionada = idPurezaSeleccionada;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	
	
	
	
}
