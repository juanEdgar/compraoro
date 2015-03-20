package clases.ui.administracion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.event.SelectEvent;

import clases.ui.compra.cliente.ClienteView;
import clases.vo.cliente.Calle;
import clases.vo.cliente.CodigoPostal;
import clases.vo.cliente.Colonia;
import clases.vo.cliente.Estado;
import clases.vo.cliente.Municipio;
import ejb.bussines.compra.CalleEJB;
import ejb.bussines.compra.CodigoPostalEJB;
import ejb.bussines.compra.ColoniaEJB;
import ejb.bussines.compra.EstadoEJB;
import ejb.bussines.compra.MunicipioEJB;
import ejb.bussines.exception.RDNException;

@ManagedBean
@ViewScoped
public class DireccionView implements Serializable {
	
	private final Logger log = LogManager.getLogger(DireccionView.class);
	
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

	/**
	 * 
	 */
	private static final long serialVersionUID = -7740269826671663329L;
	
	private List<Estado> estados;
	private Estado estado;
	
	private List<Municipio> municipios;
	private Municipio municipio;
	
	private List<Colonia> colonias;
	private Colonia colonia;
	
	private List<Calle> calles;
	private Calle calle;
	
	private List<CodigoPostal> codigos;
	private CodigoPostal codigo;
	
	private String numeroExt;
	private String numeroInt;
	
	//////////////////////////// Estados
	public List<Estado> completeEstados(String query) {
		try {
			estados = estadoEJB.buscarEstadosPorNombre(query);
			return estados;
//			List<String> nombresEstado = new ArrayList<String>();
//			for (Estado estado : estados) {
//				nombresEstado.add(estado.getNombre());
//			}
//			return nombresEstado;
		} catch(RDNException rdnE){
			
			log.debug("Error RDN en busqueda estados");
			FacesContext context= FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN ,"Cuidado", rdnE.getMessage() ));
//			
		}catch(Exception e){
			log.debug("Error EXP en busqueda estados",e);
			FacesContext context= FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo buscar al cliente "+e.getMessage()));
		}
		return new ArrayList<Estado>();
    }
	
	public void handleEstadoSeleccionado(SelectEvent event) {
		log.debug("Test handleEstadoSeleccionado");
	}
	
////////////////////////////Municipios
	public List<Municipio> completeMunicipios(String query) {
		try {
			municipios = municipioEJB.obtenerMunicipiosPorEstadoYNombre(getEstado(), query);
			return municipios;
		} catch(RDNException rdnE){
			
			log.debug("Error RDN en busqueda municipios");
			FacesContext context= FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN ,"Cuidado", rdnE.getMessage() ));
//			
		}catch(Exception e){
			log.debug("Error EXP en busqueda municipios",e);
			FacesContext context= FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo buscar municipios "+e.getMessage()));
		}
		return new ArrayList<Municipio>();
    }
	
	public void handleMunicipioSeleccionado(SelectEvent event) {
		log.debug("Test handleEstadoSeleccionado");
	}
	
//////////////////////////////// Colonia
	public List<Colonia> completeColonias(String query) {
		try {
			colonias = coloniaEJB.obtenerColoniasPorMunicipioYNombre(getMunicipio(), query);
			return colonias;
		} catch(RDNException rdnE){
			
			log.debug("Error RDN en busqueda colonias");
			FacesContext context= FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN ,"Cuidado", rdnE.getMessage() ));
//			
		}catch(Exception e){
			log.debug("Error EXP en busqueda colonias",e);
			FacesContext context= FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo buscar colonias "+e.getMessage()));
		}
		return new ArrayList<Colonia>();
    }
	
	public void handleColoniaSeleccionada(SelectEvent event) {
		log.debug("Test handleEstadoSeleccionado");
	}
	
////////////////////////////////////Calles
	public List<Calle> completeCalles(String query) {
		try {
			calles = calleEJB.obtenerCallesPorColoniaYNombre(getColonia(), query);
			return calles;
		} catch(RDNException rdnE){
			
			log.debug("Error RDN en busqueda calles");
			FacesContext context= FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN ,"Cuidado", rdnE.getMessage() ));
//			
		}catch(Exception e){
			log.debug("Error EXP en busqueda colonias",e);
			FacesContext context= FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo buscar calles "+e.getMessage()));
		}
		return new ArrayList<Calle>();
    }
	
	public void handleCalleSeleccionada(SelectEvent event) {
		log.debug("Test handleCalleSeleccionado");
	}
	
	///////////////////////// Codigo
	public List<CodigoPostal> completeCodigos(String query) {
		try {
			//codigos = codigoEJB.obtenerCallesPorColoniaYNombre(colonia, query);
			codigos = codigoEJB.obtenerCodigosPorMunicipioYCodigo(getMunicipio(), query);
			return codigos;
		} catch(RDNException rdnE){
			
			log.debug("Error RDN en busqueda calles");
			FacesContext context= FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN ,"Cuidado", rdnE.getMessage() ));
//			
		}catch(Exception e){
			log.debug("Error EXP en busqueda colonias",e);
			FacesContext context= FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo buscar calles "+e.getMessage()));
		}
		return new ArrayList<CodigoPostal	>();
    }
	
	public void handleCodigoSeleccionado(SelectEvent event) {
		log.debug("Test handleCodigoSeleccionado");
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
}
