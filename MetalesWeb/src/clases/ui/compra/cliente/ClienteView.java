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
import org.primefaces.event.SelectEvent;

import clases.ui.administracion.DireccionView;
import clases.ui.compra.cotizacion.CompraView;
import clases.vo.cliente.Calle;
import clases.vo.cliente.Cliente;
import clases.vo.cliente.CodigoPostal;
import clases.vo.cliente.Colonia;
import clases.vo.cliente.Direccion;
import clases.vo.cliente.Estado;
import clases.vo.cliente.Municipio;
import ejb.bussines.PersonaEJB;
import ejb.bussines.compra.CalleEJB;
import ejb.bussines.compra.ClienteEJB;
import ejb.bussines.compra.CodigoPostalEJB;
import ejb.bussines.compra.ColoniaEJB;
import ejb.bussines.compra.DireccionEJB;
import ejb.bussines.compra.EstadoEJB;
import ejb.bussines.compra.MunicipioEJB;
import ejb.bussines.exception.RDNException;

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
	
	private Estado estado;
	private Municipio municipio;
	private Colonia colonia;
	private Calle calle;
	private CodigoPostal codigo;
	private Direccion direccion;
	
	private String municipioString;
	private String coloniaString;
	private String calleString;
	private String codigoString;
	
	private String numeroExt;
	private String numeroInt;

	
	private int idClienteSeleccionado=0;
	
	@Inject
	private CompraView compra;
	
	@EJB
	private PersonaEJB ejbPersona;
	
	@EJB
	private ClienteEJB ejbCliente;
	
//	@Inject
//	private DireccionView direccionView;
	
	@EJB
	private EstadoEJB estadoEJB;
	
	@EJB
	private MunicipioEJB municipioEJB;
	
	@EJB
	private ColoniaEJB coloniaEJB;
	
	@EJB
	private CalleEJB calleEJB;
	
	@EJB
	private CodigoPostalEJB codigoEJB;
	
	@EJB
	private DireccionEJB direccionEJB;
	
	
	public ClienteView() {
		// TODO Auto-generated constructor stub
	}
	
	
	private boolean validarDatosCorrectos() {
		
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
		if (!validarDatosCorrectos()) {
			FacesContext context= FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "Datos incompletos para dar de alta el cliente" ));
			return null;
		}
		if (!createDireccion()) {
			FacesContext context= FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Dirección Incorrecta"));
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
	
	////////////  Direccion
	
	public List<Estado> completeEstados(String query) {
		estado = null;
		municipio = null;
		colonia = null;
		calle = null;
		codigo = null;
		try {
			return estadoEJB.buscarEstadosPorNombre(query);
		} catch(RDNException rdnE) {
			log.debug("Error RDN en busqueda estados");
			FacesContext context= FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN ,"Cuidado", rdnE.getMessage() ));
		} catch(Exception e) {
			log.debug("Error EXP en busqueda estados",e);
			FacesContext context= FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo buscar al cliente "+e.getMessage()));
		}
		return new ArrayList<Estado>();
    }
	
	public void handleEstadoSeleccionado(SelectEvent event) {
		estado = (Estado)event.getObject();
	}
	
////////////////////////////Municipios
	public List<Municipio> completeMunicipios(String query) {
		municipioString = query;
		municipio = null;
		colonia = null;
		calle = null;
		codigo = null;
		if (estado == null) {
			return new ArrayList<Municipio>();
		}
		try {
			return municipioEJB.obtenerMunicipiosPorEstadoYNombre(getEstado(), query);
		} catch(RDNException rdnE) {
			
			log.debug("Error RDN en busqueda municipios");
			FacesContext context= FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN ,"Cuidado", rdnE.getMessage() ));
		} catch(Exception e) {
			log.debug("Error EXP en busqueda municipios",e);
			FacesContext context= FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo buscar municipios "+e.getMessage()));
		}
		return new ArrayList<Municipio>();
    }
	
	public void handleMunicipioSeleccionado(SelectEvent event) {
		municipio = (Municipio)event.getObject();
		log.debug("Test handleEstadoSeleccionado");
	}
	
//////////////////////////////// Colonia
	public List<Colonia> completeColonias(String query) {
		coloniaString = query;
		colonia = null;
		calle = null;
		if (municipio == null) {
			return new ArrayList<Colonia>();
		}
		try {
			return coloniaEJB.obtenerColoniasPorMunicipioYNombre(getMunicipio(), query);
		} catch(RDNException rdnE){
			
			log.debug("Error RDN en busqueda colonias");
			FacesContext context= FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN ,"Cuidado", rdnE.getMessage() ));
		}catch(Exception e){
			log.debug("Error EXP en busqueda colonias",e);
			FacesContext context= FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo buscar colonias "+e.getMessage()));
		}
		return new ArrayList<Colonia>();
    }
	
	public void handleColoniaSeleccionada(SelectEvent event) {
		colonia = (Colonia)event.getObject();
	}
	
////////////////////////////////////Calles
	public List<Calle> completeCalles(String query) {
		calleString = query;
		calle = null;
		if (colonia == null) {
			return new ArrayList<Calle>();
		}
		try {
			return calleEJB.obtenerCallesPorColoniaYNombre(getColonia(), query);
		} catch(RDNException rdnE){
			
			log.debug("Error RDN en busqueda calles");
			FacesContext context= FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN ,"Cuidado", rdnE.getMessage() ));
		}catch(Exception e){
			log.debug("Error EXP en busqueda colonias",e);
			FacesContext context= FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo buscar calles "+e.getMessage()));
		}
		return new ArrayList<Calle>();
    }
	
	public void handleCalleSeleccionada(SelectEvent event) {
		calle = (Calle)event.getObject();
	}
	
	///////////////////////// Codigo
	public List<CodigoPostal> completeCodigos(String query) {
		codigoString = query;
		codigo = null;
		try {
			if (municipio == null) {
				return codigoEJB.obtenerCodigosPorCodigo(query);
			} else {
				return codigoEJB.obtenerCodigosPorMunicipioYCodigo(getMunicipio(), query);
			}
		} catch(RDNException rdnE){
			
			log.debug("Error RDN en busqueda calles");
			FacesContext context= FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN ,"Cuidado", rdnE.getMessage() ));
		}catch(Exception e){
			log.debug("Error EXP en busqueda colonias",e);
			FacesContext context= FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo buscar calles "+e.getMessage()));
		}
		return new ArrayList<CodigoPostal	>();
    }
	
	public boolean createDireccion() {
		FacesContext context= FacesContext.getCurrentInstance();
		if (estado == null) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Selecciona un Estado"));
			return false;
		}
		if (municipioString == null || municipioString.trim().length() < 1) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Escribe un Municipio"));
			return false;
		}
		if (coloniaString == null || coloniaString.trim().length() < 1) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Escribe una Colonia"));
			return false;
		}
		if (calleString == null || calleString.trim().length() < 1) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Escribe una Calle"));
			return false;
		}
		if (codigoString == null || codigoString.trim().length() < 1) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Escribe un Código Postal"));
			return false;
		}
		try {
			if (municipio == null) {
				Municipio m = new Municipio();
				m.setEstado(estado);
				m.setNombre(municipioString.trim().toUpperCase());
				municipio = municipioEJB.altaMunicipio(m);
			}
			if (colonia == null) {
				Colonia c = new Colonia();
				c.setMunicipio(municipio);
				c.setNombre(coloniaString.trim().toUpperCase());
				colonia = coloniaEJB.altaColonia(c);
			}
			if (calle == null) {
				Calle c = new Calle();
				c.setColonia(colonia);
				c.setNombre(calleString.trim().toUpperCase());
				calle = calleEJB.altaCalle(c);
			}
			if (codigo == null) {
				CodigoPostal c = new CodigoPostal();
				c.setCodigoPostal(codigoString);
				c.setMunicipio(municipio);
				codigo = codigoEJB.altaCodigo(c);
			}
			if (direccion == null) {
				Direccion d = new Direccion();
				d.setColonia(colonia);
				d.setCalle(calle);
				d.setCodigo(codigo);
				d.setNumeroExterior(numeroExt);
				d.setNumeroInterior(numeroInt);
				direccion = direccionEJB.altaDireccion(d);
				this.compra.setDireccion(direccion);
			}
			return true;
			
		} catch (RDNException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public void handleCodigoSeleccionado(SelectEvent event) {
		codigo = (CodigoPostal) event.getObject();
	}
	
	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public Municipio getMunicipio() {
		return municipio;
	}

	public void setMunicipio(Municipio municipio) {
		this.municipio = municipio;
	}

	public Colonia getColonia() {
		return colonia;
	}

	public void setColonia(Colonia colonia) {
		this.colonia = colonia;
	}

	public Calle getCalle() {
		return calle;
	}

	public void setCalle(Calle calle) {
		this.calle = calle;
	}

	public CodigoPostal getCodigo() {
		return codigo;
	}

	public void setCodigo(CodigoPostal codigo) {
		this.codigo = codigo;
	}

	public String getNumeroExt() {
		return numeroExt;
	}

	public void setNumeroExt(String numeroExt) {
		this.numeroExt = numeroExt;
	}

	public String getNumeroInt() {
		return numeroInt;
	}

	public void setNumeroInt(String numeroInt) {
		this.numeroInt = numeroInt;
	}

	public Direccion getDireccion() {
		return direccion;
	}

	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}
	

}
