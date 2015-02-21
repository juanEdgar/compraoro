package clases.ui.compra.cliente;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import clases.iu.compra.cotizacion.CompraView;
import clases.vo.cliente.Cliente;
import ejb.bussines.PersonaEJB;
import ejb.bussines.exception.RDNException;
import ejb.bussines.venta.ClienteEJB;

@ManagedBean
@ViewScoped
public class ClienteView implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7350545623113820114L;
	private final Logger log = LogManager.getLogger(ClienteView.class);

	private List<Cliente> listaClientes;
	private boolean mostrarListaClientes=false;
	private boolean mostarAltaCliente=false;

	
	private int idClienteSeleccionado=0;
	
	@Inject
	private CompraView compra;
	
	@EJB
	private PersonaEJB ejbPersona;
	
	@EJB
	private ClienteEJB ejbCliente;
	
	
	public ClienteView() {
		// TODO Auto-generated constructor stub
	}
	
	
	private boolean validarDatosCorrectos(){
		
		Cliente c= this.compra.getCliente();
		
		if(c!=null){
			 if(c.getNombre()==null ||  c.getNombre().equals("") 
			    || c.getApellidoMaterno()==null || c.getApellidoPaterno().equals("") 	
			    || c.getFechaNacimiento()==null)
			 {
			
				return false;
			 }
		}else{
			return false;
		}
		
		return true;
		
	}
	
	public void validarBusquedaCliente(){
		
			if( ! this.validarDatosCorrectos()){
				
						FacesContext context= FacesContext.getCurrentInstance();
						context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Cuidado", "Debe especificar los parametos obligatorios (marcados con * ) para poder continuar "));
						
						log.debug("Se muestra el mesanje");
			}
		
	}
	
	public void eventBuscarCliente(){
		
		if(!validarDatosCorrectos()){
			return;
		}
	
		log.debug("Buscando cliente por nombre");
		try{
			
			this.mostarAltaCliente=false;
			this.mostrarListaClientes=false;
			this.listaClientes= new ArrayList<>();
			this.compra.getCliente().setIdPersona(0);
			
			
			this.listaClientes= this.ejbCliente.buscarCliente(this.compra.getCliente());
			
			
			if(this.listaClientes!=null && this.listaClientes.size()>0){
				this.mostrarListaClientes=true;
				log.debug("Numero de clientes en coincidencia: "+this.listaClientes.size());
			}else{
				log.debug("No hay coincidencia en busqueda de cliente, se muestar alta");
				this.mostarAltaCliente=true;
			}
			
		}catch(RDNException rdnE){
			
			log.debug("Error RDN en busqueda cliente");
			FacesContext context= FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN ,"Cuidado", rdnE.getMessage() ));
			
		}catch(Exception e){
			log.debug("Error EXP en busqueda cliente",e);
			FacesContext context= FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo buscar al cliente "+e.getMessage()));
		}
	
}
	
	public String eventAltaCliente(){
		
		log.debug("Alta de cliente");
		if(!validarDatosCorrectos()){
			FacesContext context= FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "Datos incompletos para dar de alta el cliente" ));
			return null;
		}
		
		this.mostarAltaCliente=false;
		this.mostrarListaClientes=false;
		
		this.listaClientes=new ArrayList<Cliente>();
		
		return this.compra.navegarResumenCompra();
		
	}
	
	public String eventClienteEncontrado(int idCliente){
		
		try{
			log.debug("Se encontro la coincidencia con el cliente ID: "+idCliente);
			
			int index= this.listaClientes.indexOf(new Cliente(idCliente));
			
			
			this.compra.setCliente( this.listaClientes.get(index)   );
			
			this.mostarAltaCliente=false;
			this.mostrarListaClientes=false;
			
			this.listaClientes=new ArrayList<Cliente>();
		}catch(Exception e){
			log.error("Error al asignar el cliente asignado",e);
			
			FacesContext context= FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "Error al asignar el cliente asignado "+e.getMessage() ));
			return null;
			
		}
			
		return this.compra.navegarResumenCompra();
	
	}
	
	
	public String metodoFechaMaximaNacimiento(){
		
		log.debug("Obteniendo fecha de nacimiento");
		
		SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/yyyy", new Locale("es","MX"));
		
		return sdf.format(this.ejbPersona.getFechaMaximaMayoriaedad());
		
	}


	public List<Cliente> getListaClientes() {
		return listaClientes;
	}


	public void setListaClientes(List<Cliente> listaClientes) {
		this.listaClientes = listaClientes;
	}


	public boolean isMostrarListaClientes() {
		return mostrarListaClientes;
	}


	public void setMostrarListaClientes(boolean mostrarListaClientes) {
		this.mostrarListaClientes = mostrarListaClientes;
	}


	public boolean isMostarAltaCliente() {
		return mostarAltaCliente;
	}


	public void setMostarAltaCliente(boolean mostarAltaCliente) {
		this.mostarAltaCliente = mostarAltaCliente;
	}


	public int getIdClienteSeleccionado() {
		return idClienteSeleccionado;
	}


	public void setIdClienteSeleccionado(int idClienteSeleccionado) {
		this.idClienteSeleccionado = idClienteSeleccionado;
	}
	
	
	

}
