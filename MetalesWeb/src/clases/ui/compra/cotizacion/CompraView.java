package clases.ui.compra.cotizacion;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.Default;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import clases.business.metales.vo.compra.ArticuloCompra;
import clases.business.metales.vo.compra.Compra;
import clases.business.metales.vo.compra.Seguribolsa;
import clases.vo.cliente.Cliente;
import clases.vo.cliente.Direccion;
import ejb.bussines.compra.CompraEJB;
import ejb.bussines.exception.RDNException;

@Named
@ConversationScoped
@Default
public class CompraView implements Serializable{
		
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = 2732432874569849246L;
	
	private final Logger log = LogManager.getLogger(CompraView.class);
	
	private int numeroArticulo=0;
	
	@Inject
	 private Conversation conversation;
	
	//private List<ArticuloCompra> articulos;
	private Compra compra;
	private Cliente cliente;
	private Direccion direccion;
	
	@EJB
	private CompraEJB ejbCompra;
	
	
	
	public CompraView() {
		
	}
	
	public void quitarArticulo(int idBolsa,int idArticulo) throws Exception{
		
		
		if(this.compra.getBolsas()==null||this.compra.getBolsas().size()<=0){
			throw new Exception("No hay bolsas en la lista");
		}
		
		int indexBolsa= this.compra.getBolsas().indexOf(new Seguribolsa(idBolsa));
		
		if(indexBolsa<0){
			throw new  Exception("La bolsa no existe");
		}
		
		
		Seguribolsa bolsa= this.compra.getBolsas().get(indexBolsa);
		
		if(bolsa.getArticulosCompra()==null|| bolsa.getArticulosCompra().size()<=0){
			throw new Exception("No hay articulos en la bolsa");
		}
		
		int indexArticulo= bolsa.getArticulosCompra().indexOf(new ArticuloCompra(idArticulo));
		
		if(indexArticulo<0){
			throw new  Exception("El articulo no existe");
		}
		
		bolsa.getArticulosCompra().remove(indexArticulo);
		
		if(bolsa.getArticulosCompra().size()==0){
			this.compra.getBolsas().remove(indexBolsa);
		}
			
	}
	
	public void agregarArticulo(ArticuloCompra articulo, String codigoBolsa, String tipo) throws RDNException{
		
		log.debug("%%%%%Agregando articulo a bolsa  codigoBolsa tipo " + codigoBolsa+" "+tipo);
		
		Seguribolsa bolsa=null;
		boolean bolsaNueva=false;
		codigoBolsa=codigoBolsa.toUpperCase().trim();
		tipo=tipo.trim().toUpperCase();
		if(this.compra.getBolsas()==null){
			log.debug("Primer bolsa, se crea lista");
			this.compra.setBolsas(new ArrayList<Seguribolsa>());
				
		}else{		
			Seguribolsa bolsaTmp;
			// se valida la bolsa
			log.debug("Se busca la bolsa");
			for(int i=0; i<this.compra.getBolsas().size();i++){
				bolsaTmp=this.compra.getBolsas().get(i);
				if(bolsaTmp.getCodigo().equals(codigoBolsa)){
					log.debug("Bolsa encontrada");
					bolsa=bolsaTmp;
					break;
				}
			}
			
		}
		
		if(bolsa==null){
			log.debug("La bolsa no existe, se crea"); 
			bolsa=new Seguribolsa(this.getNumeroArticulo());
			bolsa.setCodigo(codigoBolsa);
			bolsa.setTipo(tipo);
			bolsaNueva=true;
			
		}
		
		if(!bolsa.getTipo().equals(tipo)){
			throw new RDNException("La bolsa no corresponde al tipo de producto seleccionado");
		}
		
		if(bolsa.getArticulosCompra()==null){
			log.debug("se crea lista de articulos");
			bolsa.setArticulosCompra(new ArrayList<ArticuloCompra>());
		}
		articulo.setId(this.getNumeroArticulo());
		articulo.setSeguribolsa(bolsa);
		
		 bolsa.getArticulosCompra().add(articulo);
		 if(bolsaNueva){
			 this.compra.getBolsas().add(bolsa);
		 }
		
	}
	
	public String getTotalValuacionArticulos(){
		
		DecimalFormat df2 = new DecimalFormat( "#,###,###,##0.00" );	
		return "$ "+df2.format(this.getTotalPagoCompra());
		
	}
	
	
	public float getTotalPagoCompra(){
		float total=0.0F;
		
		
		for(Seguribolsa bolsa: compra.getBolsas()){
		
			for(ArticuloCompra a : bolsa.getArticulosCompra()){
				total+=a.getValor();
			}
			
		}
		return total;	
	}
	
	@PostConstruct
	private void init(){
		
		this.compra= new Compra();
		this.cliente= new Cliente();
		this.setDireccion(new Direccion());
	}
	
	public boolean mostrarTabla(){
		
		
		if(this.compra.getArticulos().size()>0){
			
			return true;
		}
		return false;
	}

	public String getTipoCliente(){
		return this.cliente.getIdPersona()==0?"NUEVO":"CONOCIDO";
	}
	
	public String eventConfirmarOperacion(){
		
		log.debug("Inicia el guardado de los datos");
		
		try {
			
			log.debug("Lista de bolsas a mandar: "+this.compra.getBolsas().size());
			
			// limpiamos los ID

			this.cliente.setDireccion(this.direccion);
			this.ejbCompra.registrarClienteCompra(this.cliente,this.compra);
			
			log.debug("OK, operacion realizada con éxito");
			
			this.endConversation();			
			return this.navegacionCotizacion();
		} catch(RDNException rdnE){
			
			log.error("Error RDN en alta  cliente",rdnE);
			FacesContext context= FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN ,"Cuidado", rdnE.getMessage() ));
			return null;
		}catch(Exception e){
			log.error("Error EXP en alta cliente",e);
			FacesContext context= FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo dar de alta al cliente"+e.getMessage()));
			return null;
		}
	}

	public String navegacionCliente(){
		log.debug("navega cliente");
		return "busquedaCliente?faces-redirect=true";
	}
	
	public String navegacionPago(){
		log.debug("navega pago");
		return "registroPago?faces-redirect=true";
	}
	
	public String navegacionCotizacion(){
		log.debug("regresando a cotizacion");
		return "cotizacion?faces-redirect=true";
	}
	
	public String navegarResumenCompra() {		
		return "resumen?faces-redirect=true";
	}
	
	public String navegarResumenImpresionCompra() {		
		return "resumenImpresion?faces-redirect=true";
	}
	
	public void initConversation(){
	    if (!FacesContext.getCurrentInstance().isPostback() 
	      && conversation.isTransient()) {
	    
	      conversation.begin();
	    }
	  }
	
	 public String endConversation(){
		    if(!conversation.isTransient()){
		      conversation.end();
		    }
		    return "cotizacion?faces-redirect=true";
		  }

	public Conversation getConversation() {
		return conversation;
	}

	public void setConversation(Conversation conversation) {
		this.conversation = conversation;
	}

	public Compra getCompra() {
		return compra;
	}

	public void setCompra(Compra compra) {
		this.compra = compra;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public int getNumeroArticulo(){
		return ++this.numeroArticulo;
	}

	public Direccion getDireccion() {
		return direccion;
	}

	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}





	 
}
