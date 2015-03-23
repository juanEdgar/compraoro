package clases.ui.administracion;

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
import org.primefaces.event.CellEditEvent;

import clases.business.metales.vo.cotizador.Diamante;
import clases.business.metales.vo.cotizador.DiamanteColor;
import clases.business.metales.vo.cotizador.DiamanteLimpieza;
import clases.business.metales.vo.cotizador.DiamantePunto;
import clases.business.metales.vo.cotizador.PrecioDiamante;
import clases.util.compra.FilaPreciosDiamante;
import ejb.bussines.DiamanteEJB;
import ejb.bussines.exception.RDNException;


@ManagedBean
@ViewScoped
public class PrecioDiamanteView {
	
	private static final Logger log = LogManager .getLogger(PrecioDiamanteView.class);
	
	@EJB
	private DiamanteEJB ejbDiamante;
	
	private List<DiamantePunto> diamantePuntos;
	private List<DiamanteLimpieza> diamanteLimpiezas;
	private List<DiamanteColor> diamanteColores;

	private List<FilaPreciosDiamante> precios;
	
	private DiamantePunto puntoActual;
	
	private int idPuntoSeleccionado=0;
	
	public PrecioDiamanteView() {
	}
	
	@PostConstruct
	private void init(){
		
		try{
		
			this.diamantePuntos = ejbDiamante.getPuntos();
			this.diamanteColores= ejbDiamante.getColores();
			this.diamanteLimpiezas= ejbDiamante.getLimpiezas();
			
			
			
			if(this.diamantePuntos!=null && this.diamantePuntos.size()>0){
				this.puntoActual= this.diamantePuntos.get(0);
				this.idPuntoSeleccionado= this.puntoActual.getId();
			}
			log.debug("Recuperando precios desde init");
			this.llenarFilaPrecios();
			
		}	
			
		catch(RDNException e){
			log.error(e,e);
			this.setMensage(FacesMessage.SEVERITY_WARN, "Cuidado", e.getMessage());
		}
		catch(Exception e){
			log.error(e,e);
			this.setMensage(FacesMessage.SEVERITY_ERROR, "Cuidado", e.getMessage());
			
		}
		
	}
	
	public void eventCambiarPunto(){
		
		try{
			
			log.debug("event cambiar punto");

			this.puntoActual= new DiamantePunto();
			this.puntoActual.setId(this.idPuntoSeleccionado);
			
			int index= this.diamantePuntos.indexOf(this.puntoActual);
			this.puntoActual= this.diamantePuntos.get(index);
			
			log.debug("Punto seleccionado: "+this.puntoActual.getRangoPuntos());
			
			this.llenarFilaPrecios();
			
		}	
			
		catch(RDNException e){
			log.error(e,e);
			this.setMensage(FacesMessage.SEVERITY_WARN, "Cuidado", e.getMessage());
		}
		catch(Exception e){
			log.error(e,e);
			this.setMensage(FacesMessage.SEVERITY_ERROR, "Cuidado", e.getMessage());
			
		}
		
		
		
	}
	
	private void llenarFilaPrecios() throws RDNException, Exception{
		this.precios= new ArrayList<FilaPreciosDiamante>();
		FilaPreciosDiamante fila=null;
		Diamante diamante= null;
		
		log.debug("Filas a iterar :"+this.diamanteColores.size());
		
		for(DiamanteColor color: this.diamanteColores){
			fila= new FilaPreciosDiamante();			
			for(int i=0; i<4; i++){
				
				diamante= new Diamante();
				diamante.setColor(color);
				diamante.setLimpieza(this.diamanteLimpiezas.get(i));
				diamante.setPunto(this.puntoActual);
				diamante.setPrecio( this.ejbDiamante.getPrecioDiamante(diamante));
				diamante.setId(diamante.getPrecio().getTipoDiamante().getId());
				
				fila.getDiamante()[i]=diamante;			
			}
			this.precios.add(fila);
		}
		
		
		
	}
	
	
	public void onCellEdit(CellEditEvent event) {
		
		
		
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
        log.debug("Evento cambiar ");
       
        if(newValue==null){
        	this.setMensage(FacesMessage.SEVERITY_WARN,"cuidado" ,"Debe poner un valor válido");      
        	
        	return;
        }
        
        try{
        	log.debug("Precio a poner "+newValue.toString());
        	Float.parseFloat(newValue.toString());
        	
        	

            if(!newValue.toString().equals(oldValue.toString())){
            	log.debug("El valor cambio, se actualiza");
            	FilaPreciosDiamante filaSeleccionada= this.precios.get(event.getRowIndex());
            	
            	for(int i=0; i<4; i++){
            		this.ejbDiamante.actualizarPrecioDiamante(filaSeleccionada.getDiamante()[i]);
            	}
            	
            	this.llenarFilaPrecios();
            }
        	
        	
        }catch(Exception e){
        	log.error(e,e);
        	this.setMensage(FacesMessage.SEVERITY_WARN,"Cuidado" ,"Debe poner un valor válido");        	
        	return;
        }
        
        
       
        
    }
	
	public String guardarValores(){
		log.debug("guardando valores");
		log.debug("Validando valores");
		
		try{
			
			 
	        for(FilaPreciosDiamante filaF: this.precios){
				log.debug("Color: "+filaF.getPrecioLimpieza1().getColor().getColor());
				log.debug("Color: "+filaF.getPrecioLimpieza1().getPrecio().getPrecio());
				log.debug("Color: "+filaF.getPrecioLimpieza2().getPrecio().getPrecio());
				log.debug("Color: "+filaF.getPrecioLimpieza3().getPrecio().getPrecio());
				log.debug("Color: "+filaF.getPrecioLimpieza4().getPrecio().getPrecio());
				
			}
			
		}catch(Exception e){
			log.error(e,e);
		}
		
		return "cambiarPreciosDiamante";
	}
	
	public int getIdPuntoSeleccionado() {
		return idPuntoSeleccionado;
	}

	public void setIdPuntoSeleccionado(int idPuntoSeleccionado) {
		this.idPuntoSeleccionado = idPuntoSeleccionado;
	}

	public boolean mostrarTabla(){
		return this.puntoActual!=null && this.puntoActual.getId()!=0;
	}
	
	private void setMensage(Severity severity, String titulo, String Mensage){
		FacesContext context= FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(severity,titulo,Mensage));
		
	
	}
	
	public DiamantePunto getPuntoActual() {
		return puntoActual;
	}

	public void setPuntoActual(DiamantePunto puntoActual) {
		this.puntoActual = puntoActual;
	}

	public List<DiamantePunto> getDiamantePuntos() {
		return diamantePuntos;
	}

	public void setDiamantePuntos(List<DiamantePunto> diamantePuntos) {
		this.diamantePuntos = diamantePuntos;
	}

	public List<DiamanteLimpieza> getDiamanteLimpiezas() {
		return diamanteLimpiezas;
	}

	public void setDiamanteLimpiezas(List<DiamanteLimpieza> diamanteLimpiezas) {
		this.diamanteLimpiezas = diamanteLimpiezas;
	}

	public List<DiamanteColor> getDiamanteColores() {
		return diamanteColores;
	}

	public void setDiamanteColores(List<DiamanteColor> diamanteColores) {
		this.diamanteColores = diamanteColores;
	}

	public List<FilaPreciosDiamante> getPrecios() {
		return precios;
	}

	public void setPrecios(List<FilaPreciosDiamante> precios) {
		this.precios = precios;
	}

	

	
	
	
	
	
}
