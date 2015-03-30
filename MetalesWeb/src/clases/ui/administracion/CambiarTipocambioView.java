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
import clases.vo.dinero.TipoDeCambio;
import ejb.bussines.MetalEJB;
import ejb.bussines.administracion.TipoDeCambioEJB;
import ejb.bussines.compra.CotizadorEJB;
import ejb.bussines.exception.RDNException;

@ManagedBean
@ViewScoped
public class CambiarTipocambioView implements Serializable{


	private static final long serialVersionUID = 8036395925296796691L;

	
	@EJB
	private TipoDeCambioEJB ejbTC;
	
	private final Logger log = LogManager.getLogger(CambiarTipocambioView.class);
	
	private List<TipoDeCambio> listaTC= null;
	private List<Moneda> monedas= null;
	
	public CambiarTipocambioView() {
		// TODO Auto-generated constructor stub
	}
	
	@PostConstruct
	private void init(){
		log.debug("Se recupera la lista de tipos de cambio");
		this.listaTC= this.ejbTC.getTablaTC();
		this.monedas= this.ejbTC.getListaMonedas();
	}
	
	public void eventActualizarPrecio(int idTC){
		// se validan los campos
		log.debug("Se selecciona actualizar TC");
		TipoDeCambio tcSeleccionado= this.getTcSeleccionado(idTC);
		if(tcSeleccionado!=null){
			if(tcSeleccionado.getValor()<=0.0F){
				this.setMensage(FacesMessage.SEVERITY_WARN, "Cuidado","Debe especificar un valor mayor que cero");
				return;
			}
			try{
				this.ejbTC.actualizarTipoDeCambio(tcSeleccionado.getMonedaBase(), tcSeleccionado.getMonedaCambio(), tcSeleccionado.getValor());
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
	
	private TipoDeCambio getTcSeleccionado(int id){
		int index=this.listaTC.indexOf(new TipoDeCambio(id));
		if(index>=0){
			return this.listaTC.get(index);
		}
		this.setMensage(FacesMessage.SEVERITY_ERROR ,"Error","No se pudo recuperar el precio a actualizar");
		return null;
	}
	
	
	private void setMensage(Severity severity, String titulo, String Mensage){
		FacesContext context= FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(severity,titulo,Mensage));
		
	}

	public List<TipoDeCambio> getListaTC() {
		return listaTC;
	}

	public void setListaTC(List<TipoDeCambio> listaTC) {
		this.listaTC = listaTC;
	}

	public List<Moneda> getMonedas() {
		return monedas;
	}

	public void setMonedas(List<Moneda> monedas) {
		this.monedas = monedas;
	}
	
	
	
}
