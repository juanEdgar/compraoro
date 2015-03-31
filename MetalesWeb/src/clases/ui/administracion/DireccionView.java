package clases.ui.administracion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.event.SelectEvent;

import clases.ui.compra.cliente.ClienteView;
import clases.ui.compra.cotizacion.CompraView;
import clases.vo.cliente.Calle;
import clases.vo.cliente.CodigoPostal;
import clases.vo.cliente.Colonia;
import clases.vo.cliente.Direccion;
import clases.vo.cliente.Estado;
import clases.vo.cliente.Municipio;
import ejb.bussines.compra.CalleEJB;
import ejb.bussines.compra.CodigoPostalEJB;
import ejb.bussines.compra.ColoniaEJB;
import ejb.bussines.compra.DireccionEJB;
import ejb.bussines.compra.EstadoEJB;
import ejb.bussines.compra.MunicipioEJB;
import ejb.bussines.exception.RDNException;

@ManagedBean
@ViewScoped
public class DireccionView implements Serializable {
	
	private final Logger log = LogManager.getLogger(DireccionView.class);
	
	@Inject
	private CompraView compra;
	
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

	/**
	 * 
	 */
	private static final long serialVersionUID = -7740269826671663329L;
	
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
	
	//////////////////////////// Estados
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
