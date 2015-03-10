package clases.ui.administracion;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import clases.business.metales.vo.cotizador.Metal;
import clases.vo.dinero.Moneda;
import ejb.bussines.MetalEJB;
import ejb.bussines.administracion.TipoDeCambioEJB;
import ejb.bussines.compra.CotizadorEJB;
import ejb.bussines.exception.RDNException;

@ManagedBean
@ViewScoped
public class CambiarPrecioView implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7993263219136430433L;

	@EJB
	private MetalEJB ejbMetales;

	@EJB
	private CotizadorEJB ejbCotizador;
	
	@EJB
	private TipoDeCambioEJB ejbTC;
	
	private final Logger log = LogManager.getLogger(CambiarPrecioView.class);
	
	private List<Metal> productoMetales= null;
	private List<Moneda> monedas= null;
	
	public CambiarPrecioView() {
		// TODO Auto-generated constructor stub
	}
	
	@PostConstruct
	private void init(){
		log.debug("Se recupera la lista de metales");
		this.productoMetales= this.ejbMetales.getMetalesComercializables();
		this.monedas= this.ejbTC.getListaMonedas();
	}
	
	public void eventActualizarPrecio(int idMetal){
		// se validan los campos
		log.debug("Se selecciona actualizar precio");
		Metal mSeleccionado= this.getMetalSeleccionado(idMetal);
		if(mSeleccionado!=null){
			if(mSeleccionado.getPrecioGramo().getPrecio()<=0.0F){
				this.setMensage(FacesMessage.SEVERITY_WARN, "Cuidado","Debe especificar un valor mayor que cero");
				return;
			}
			try{
				log.debug("Se actualizan precios "+ mSeleccionado.getPrecioGramo().getMoneda().getId());
				this.ejbMetales.actualizarPrecioMetal(mSeleccionado, mSeleccionado.getPrecioGramo().getPrecio(), mSeleccionado.getPrecioGramo().getMoneda());
				this.setMensage(FacesMessage.SEVERITY_INFO, "Correcto", "Precio actualizado correctamente");
			}catch(Exception e){
				if(!(e instanceof RDNException)){
					log.error(e);
				}
				this.setMensage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage());
			}
		}
	}
	
	private Metal getMetalSeleccionado(int id){
		int index=this.productoMetales.indexOf(new Metal(id));
		if(index>=0){
			return this.productoMetales.get(index);
		}
		this.setMensage(FacesMessage.SEVERITY_ERROR ,"Error","No se pudo recuperar el precio a actualizar");
		return null;
	}
	
	
	private void setMensage(Severity severity, String titulo, String Mensage){
		FacesContext context= FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(severity,titulo,Mensage));
		
	}

	public List<Metal> getProductoMetales() {
		return productoMetales;
	}

	public void setProductoMetales(List<Metal> productoMetales) {
		this.productoMetales = productoMetales;
	}

	public List<Moneda> getMonedas() {
		return monedas;
	}

	public void setMonedas(List<Moneda> monedas) {
		this.monedas = monedas;
	}
	
	
	
}
