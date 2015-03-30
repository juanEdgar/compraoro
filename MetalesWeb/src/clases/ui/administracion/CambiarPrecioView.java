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
	
	private Metal metalEditar= null;
	private boolean mostarTabla=false;
	
	
	public CambiarPrecioView() {
		// TODO Auto-generated constructor stub
	}
	
	@PostConstruct
	private void init(){
		log.debug("Se recupera la lista de metales");
		this.productoMetales= this.ejbMetales.getMetalesComercializables();
		this.monedas= this.ejbTC.getListaMonedas();
		this.mostarTabla= false;
	}
	
	public void eventActualizarPrecio(int idMetal){
		// se validan los campos
		log.debug("Se selecciona actualizar precio id Metal "+idMetal);
		this.metalEditar= this.getMetalSeleccionado(idMetal);
		this.mostarTabla=true;

	}
	
	
	public void guardar(){
		if(metalEditar!=null){
			if(metalEditar.getPrecioGramo().getPrecio()<=0.0F){
				this.setMensage(FacesMessage.SEVERITY_WARN, "Cuidado","Debe especificar un valor mayor que cero");
				return;
			}
			if(metalEditar.getPrecioGramo().getAforo()<40F || metalEditar.getPrecioGramo().getAforo()>100F){
				this.setMensage(FacesMessage.SEVERITY_WARN, "Cuidado","Debe especificar un aforo entre 40 y 100%");
				return;
			}
			try{
				log.debug("Se guarda el valor del precio de metal");
				this.ejbMetales.actualizarPrecioMetal(metalEditar, metalEditar.getPrecioGramo().getPrecio(), metalEditar.getPrecioGramo().getMoneda());
				this.setMensage(FacesMessage.SEVERITY_INFO, "Correcto", "Precio actualizado correctamente");
				this.init();
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

	public Metal getMetalEditar() {
		return metalEditar;
	}

	public void setMetalEditar(Metal metalEditar) {
		this.metalEditar = metalEditar;
	}

	public boolean isMostarTabla() {
		return mostarTabla;
	}

	public void setMostarTabla(boolean mostarTabla) {
		this.mostarTabla = mostarTabla;
	}
	
	
	
}
