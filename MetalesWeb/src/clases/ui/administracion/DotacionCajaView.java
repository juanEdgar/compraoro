package clases.ui.administracion;

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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.event.RowEditEvent;

import clases.vo.tienda.Tienda;
import clases.vo.tienda.caja.TiendaCajaEfectivo;
import clases.vo.tienda.caja.TiendaCajaEfectivoMovimiento;
import clases.vo.tienda.caja.TiendaCajaEfectivoSaldo;
import ejb.bussines.administracion.TiendaCajaEJB;
import ejb.bussines.administracion.TiendaEJB;
import ejb.bussines.exception.RDNException;

@ManagedBean
@ViewScoped
public class DotacionCajaView {

	private final Logger log = LogManager.getLogger(DotacionCajaView.class);
	
	@EJB
	private TiendaCajaEJB cajaEjb;
	
	@EJB
	private TiendaEJB tiendaEjb;
	
	private List<Tienda> tiendasActivas;
	private List<TiendaCajaEfectivo> cajas;
	
	private int idTiendaSeleccionada;
	private int idCajaSeleccionada;

	
	private List<TiendaCajaEfectivoSaldo> saldosCaja;
	

	
	public DotacionCajaView() {
		
	}
	
	@PostConstruct
	private void init(){
		log.debug("iniciando dotaciones de caja");
		
		
		try{
			this.tiendasActivas= this.tiendaEjb.recuperarTindasActivas();
			
		}catch(RDNException e){
			this.setMensage(FacesMessage.SEVERITY_WARN, "cuidado",e.getMessage());
		}catch(Exception e){
			this.setMensage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage());
		}
		
	}
	
	
	private void setMensage(Severity severity, String titulo, String Mensage){
		FacesContext context= FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(severity,titulo,Mensage));
		
	}
	
	
	
	
	public void eventSeleccionarTienda(){
		log.debug("Metodo para obtener las cajas al cambiar el combo de la tienda");
		try{
			this.cajas=null;
			this.saldosCaja=null;
			this.idCajaSeleccionada=0;
			if(this.idTiendaSeleccionada>0){			
				Tienda tiendaSeleccionada= this.obtenerTiendaSeleccionada();				
				if(tiendaSeleccionada==null){
					throw new Exception("Erro al recuperar la tienda seleccionada en el combo");
				}
				this.cajas= this.cajaEjb.getCajasPorTienda(tiendaSeleccionada);
			}
			
			
			
		}catch(RDNException e){
			this.setMensage(FacesMessage.SEVERITY_WARN, "cuidado",e.getMessage());
		}catch(Exception e){
			this.setMensage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage());
		}
		
	}
	
	public void eventEditarFila(RowEditEvent e){
		TiendaCajaEfectivoSaldo saldo= (TiendaCajaEfectivoSaldo)e.getObject();
		
		
	}
	
	public void eventCancelarEditarFila(RowEditEvent e){
		TiendaCajaEfectivoSaldo saldo= (TiendaCajaEfectivoSaldo)e.getObject();
		saldo.setCantidadDotar(0);
		log.debug("fin cancelar canitdad dotar");
		
	}
	
	public String saldarCaja(){
		
		
		try{
			log.debug("Saldando la caja");
			
			if(this.idTiendaSeleccionada==0|| this.idCajaSeleccionada==0){
				throw new RDNException("Debe seleccionar una tienda y caja validos");
			}
			
			Tienda t= new Tienda(this.idTiendaSeleccionada);
			TiendaCajaEfectivo caja= new TiendaCajaEfectivo();
			caja.setId(this.idCajaSeleccionada);
			caja.setTienda(t);			
			
			this.cajaEjb.saldarCaja(caja);
			log.debug("Caja Saldada");
			
		}catch(RDNException e){
			this.setMensage(FacesMessage.SEVERITY_WARN, "cuidado",e.getMessage());
			return "";
		}catch(Exception e){
			log.error(e,e);
			this.setMensage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage());
			return "";
		}
		
		
		log.info("Limpiando formulario");
		
		this.init();
		this.saldosCaja=null;
		this.idCajaSeleccionada=0;
		this.idCajaSeleccionada=0;
		return "dotacionesCaja";
	}
	
	public String guardar(){
		
		log.debug("Se guardan los valores de la dotacion en caja");
		
		try{
			
			if(this.idTiendaSeleccionada==0|| this.idCajaSeleccionada==0){
				throw new RDNException("Debe seleccionar una tienda y caja validos");
			}
			
			Tienda t= new Tienda(this.idTiendaSeleccionada);
			TiendaCajaEfectivo caja= new TiendaCajaEfectivo();
			caja.setId(this.idCajaSeleccionada);
			caja.setTienda(t);
			
			List<TiendaCajaEfectivoMovimiento> movimientos= new ArrayList<TiendaCajaEfectivoMovimiento>();
			TiendaCajaEfectivoMovimiento movimiento;
			// se forman los movimientos
			for(TiendaCajaEfectivoSaldo saldos: this.saldosCaja){
				if(saldos.getCantidadDotar()>0){
					movimiento= new TiendaCajaEfectivoMovimiento();
					movimiento.setCantidad(saldos.getCantidadDotar());
					movimiento.setDenominacion(saldos.getDenominacion());
					movimiento.setEstatus(1);
					movimiento.setTiendaCaja(caja);
					movimientos.add(movimiento);
				}
			}
			
			if(movimientos.size()==0){
				throw new RDNException("No hay registros a guardar");
			}
			
			this.cajaEjb.registarDotacionEnCaja(caja, movimientos);
			log.debug("Dotacion reistrada");
			
		}catch(RDNException e){
			this.setMensage(FacesMessage.SEVERITY_WARN, "cuidado",e.getMessage());
			return "";
		}catch(Exception e){
			log.error(e,e);
			this.setMensage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage());
			return "";
		}
		
		log.info("Limpiando formulario");
		
		this.init();
		this.saldosCaja=null;
		this.idCajaSeleccionada=0;
		this.idCajaSeleccionada=0;
		return "dotacionesCaja";
		
	}
	
	public void eventSeleccionarCaja(){
		
		try{
		
			log.debug("Metodo se selecciono caja en combo");
			
			
			if(this.idTiendaSeleccionada==0 || this.idCajaSeleccionada==0){
				throw new Exception("Erro al recuperar la caja seleccionada en el combo");
			}
			

    		
			Tienda tiendaN= new Tienda(this.idTiendaSeleccionada);
			TiendaCajaEfectivo caja= new TiendaCajaEfectivo();
			caja.setTienda(tiendaN);
			caja.setId(this.idCajaSeleccionada);
			
			log.debug("Consultado saldos "+caja.getTienda().getId()+" caja "+caja.getId());
			
			this.saldosCaja= this.cajaEjb.recuperarSaldosPorCaja(caja);
			
			log.debug("cantidad de saldos ecuperados "+this.saldosCaja.size());
			
		}catch(RDNException e){
			this.setMensage(FacesMessage.SEVERITY_WARN, "cuidado",e.getMessage());
		}catch(Exception e){
			log.error(e,e);
			this.setMensage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage());
		}
		
	}
	

	
	private Tienda obtenerTiendaSeleccionada(){
		if(this.idTiendaSeleccionada>0){
			int index= this.tiendasActivas.indexOf(new Tienda(this.idTiendaSeleccionada));
	
			return this.tiendasActivas.get(index);
		}
		else{
			
			return null;
		}
		
	}
	


	public List<Tienda> getTiendasActivas() {
		return tiendasActivas;
	}

	public void setTiendasActivas(List<Tienda> tiendasActivas) {
		this.tiendasActivas = tiendasActivas;
	}

	public List<TiendaCajaEfectivo> getCajas() {
		return cajas;
	}

	public void setCajas(List<TiendaCajaEfectivo> cajas) {
		this.cajas = cajas;
	}

	public int getIdTiendaSeleccionada() {
		return idTiendaSeleccionada;
	}

	public void setIdTiendaSeleccionada(int idTiendaSeleccionada) {
		this.idTiendaSeleccionada = idTiendaSeleccionada;
	}

	public int getIdCajaSeleccionada() {
		return idCajaSeleccionada;
	}

	public void setIdCajaSeleccionada(int idCajaSeleccionada) {
		this.idCajaSeleccionada = idCajaSeleccionada;
	}

	public List<TiendaCajaEfectivoSaldo> getSaldosCaja() {
		return saldosCaja;
	}

	public void setSaldosCaja(List<TiendaCajaEfectivoSaldo> saldosCaja) {
		this.saldosCaja = saldosCaja;
	}

	public boolean isMostrarTabla() {
		
		if(this.saldosCaja==null || this.saldosCaja.size()==0){
			return false;
		}
		
		return true;
	}

	public float calcularSaldoActual(){
		float saldo=0.0F;
		if(this.saldosCaja!=null && this.saldosCaja.size()>0){
			for(TiendaCajaEfectivoSaldo s: this.saldosCaja){
				saldo+= s.getDenominacion()*s.getCantidad();
			}
			
			return saldo;
		}
		else{
			return 0.0F;
		}
	}
	
	public float calcularSaldoDotar(){
		float saldo=0.0F;
		if(this.saldosCaja!=null && this.saldosCaja.size()>0){
			for(TiendaCajaEfectivoSaldo s: this.saldosCaja){
				saldo+= s.getDenominacion()*s.getCantidadDotar();
			}
			
			return saldo;
		}
		else{
			return 0.0F;
		}
	}
	
	
	
	public String calcularSaldoActualFormateado(){
		DecimalFormat df2 = new DecimalFormat( "#,###,###,##0.00" );			
		return "$ "+df2.format( this.calcularSaldoActual() );
	
		
		
	}
	
	public String calcularSaldoDotarFormateado(){
		DecimalFormat df2 = new DecimalFormat( "#,###,###,##0.00" );		
		return "$ "+df2.format( this.calcularSaldoDotar() );
	}
	
	public String calcularSaldoTotalformateado(){
		DecimalFormat df2 = new DecimalFormat( "#,###,###,##0.00" );		
		return "$ "+df2.format( this.calcularSaldoDotar()+this.calcularSaldoActual() );
	}
	
	
}
