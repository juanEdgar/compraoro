package clases.ui.compra.pago;

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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import clases.business.metales.vo.compra.PagoCompra;
import clases.business.metales.vo.compra.PagoCompraEfectivo;
import clases.business.metales.vo.compra.PagoCompraSPEI;
import clases.login.UsuarioSesion;
import clases.ui.compra.cotizacion.CompraView;
import ejb.bussines.administracion.TiendaCajaEJB;
import ejb.bussines.exception.RDNException;


@ManagedBean
@ViewScoped
public class PagoCompraView {
	
	private final Logger log = LogManager.getLogger(PagoCompraView.class);
	
	@Inject 
	private CompraView compraView;
	
	@EJB
	private TiendaCajaEJB cajaEjb;
	
	@Inject
	private UsuarioSesion usuarioSesion;
	
	private PagoCompraSPEI pagoSpei;
	
	
	private List<PagoCompraEfectivo> movimientosPago;
	
	private boolean mostarPagoEfectivo; 
	
	public PagoCompraView() {
	
	}
	
	@PostConstruct
	private void init(){
		this.compraView.getCompra().setPagos(new ArrayList<PagoCompra>());
		this.pagoSpei= new PagoCompraSPEI();
		
		try{
			
			this.mostarPagoEfectivo=false;
			log.debug("Recuperando saldo de la caja");
			this.movimientosPago=this.cajaEjb.calcularPagoPorDenominacion(usuarioSesion.getCaja(), compraView.getTotalPagoCompra());
			
			if(this.movimientosPago==null || this.movimientosPago.size()==0){
				throw new RDNException("La caja no trajo saldos");
			}
			
			log.debug("Denominaciones de la caja: "+this.movimientosPago.size());
			
			this.mostarPagoEfectivo=true;
			
		}catch(RDNException e){
			log.debug("No se recuperaron los saldos de caja. "+e.getMessage());
			this.mostarPagoEfectivo=false;
			this.setMensage(FacesMessage.SEVERITY_INFO, "Cambio induficiente", "No se puede pagar en efectivo con el saldo en caja actual");
		}catch(Exception e){
			log.error(e,e);
			this.setMensage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage());
		}
		
		
	}
	
	public String eventRegistraPagoEfectivo(){
		
		log.debug("Registrando pago en efectivo");
		try{
			
		
			for(PagoCompra pago: this.movimientosPago){
			
				this.compraView.getCompra().getPagos().add( (PagoCompra)pago );			
			
			}
			
			return this.compraView.eventConfirmarOperacion();
			
		}catch(Exception e){
			this.compraView.getCompra().setPagos(new ArrayList<PagoCompra>());
			this.setMensage(FacesMessage.SEVERITY_ERROR, "Error", "Error con el pago en efectivo. "+e.getMessage() );
			return "";
		}
		
		
	}
	
	
	public String eventRegistraPagoSpei(){
		
		log.debug("Registrando pago SPEI")			
		
		
;
		try{
			
			this.pagoSpei.setMonto(this.compraView.getTotalPagoCompra());
			
			this.compraView.getCompra().getPagos().add( (PagoCompra) pagoSpei);
			
			return this.compraView.eventConfirmarOperacion();
			
		}catch(Exception e){
			this.compraView.getCompra().setPagos(new ArrayList<PagoCompra>());
			this.setMensage(FacesMessage.SEVERITY_ERROR, "Error", "Error con el pago en efectivo. "+e.getMessage() );
			return "";
		}
		
		
	}
	
	
	private void setMensage(Severity severity, String titulo, String Mensage){
		FacesContext context= FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(severity,titulo,Mensage));
		
	}

	public CompraView getCompraView() {
		return compraView;
	}

	public void setCompraView(CompraView compraView) {
		this.compraView = compraView;
	}

	public PagoCompraSPEI getPagoSpei() {
		return pagoSpei;
	}

	public void setPagoSpei(PagoCompraSPEI pagoSpei) {
		this.pagoSpei = pagoSpei;
	}

	public List<PagoCompraEfectivo> getMovimientosPago() {
		return movimientosPago;
	}

	public void setMovimientosPago(
			List<PagoCompraEfectivo> movimientosPago) {
		this.movimientosPago = movimientosPago;
	}

	public boolean isMostarPagoEfectivo() {
		return mostarPagoEfectivo;
	}

	public void setMostarPagoEfectivo(boolean mostarPagoEfectivo) {
		this.mostarPagoEfectivo = mostarPagoEfectivo;
	}
	
	
	
	
}
