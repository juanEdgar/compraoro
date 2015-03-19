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
	private String municipioQuery;
	
	private List<Colonia> colonias;
	private Colonia colonia;
	private String coloniaQuery;
	
	private List<Calle> calles;
	private Calle calle;
	private String calleQuery;
	
	private List<CodigoPostal> codigos;
	private CodigoPostal codigo;
	private String codigoQuery;
	
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
	public List<String> completeMunicipios(String query) {
		try {
			municipios = municipioEJB.obtenerMunicipiosPorEstadoYNombre(getEstado(), query);
			List<String> nombresMunicipio = new ArrayList<String>();
			for (Municipio municipio : municipios) {
				nombresMunicipio.add(municipio.getNombre());
			}
			return nombresMunicipio;
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
		return new ArrayList<String>();
    }
	
//////////////////////////////// Colonia
	public List<String> completeColonias(String query) {
		try {
			colonias = coloniaEJB.obtenerColoniasPorMunicipioYNombre(municipio, query);
			List<String> nombresColonia = new ArrayList<String>();
			for (Colonia colonia : colonias) {
				nombresColonia.add(colonia.getNombre());
			}
			return nombresColonia;
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
		return new ArrayList<String>();
    }
	
////////////////////////////////////Calles
	public List<String> completeCalles(String query) {
		try {
			calles = calleEJB.obtenerCallesPorColoniaYNombre(colonia, query);
			List<String> nombresCalle = new ArrayList<String>();
			for (Calle calle : calles) {
				nombresCalle.add(calle.getNombre());
			}
			return nombresCalle;
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
		return new ArrayList<String>();
    }
	
	///////////////////////// Codigo
	public List<String> completeCodigo(String query) {
		try {
			//codigos = codigoEJB.obtenerCallesPorColoniaYNombre(colonia, query);
			codigos = codigoEJB.obtenerCodigosPorMunicipioYCodigo(municipio, query);
			List<String> nombresCodigo = new ArrayList<String>();
			for (CodigoPostal codigo : codigos) {
				nombresCodigo.add(codigo.getCodigoPostal());
			}
			return nombresCodigo;
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
		return new ArrayList<String>();
    }
	
	public String getMunicipioQuery() {
		return municipioQuery;
	}

	public void setMunicipioQuery(String municipioQuery) {
		this.municipioQuery = municipioQuery;
	}
	
	public String getColoniaQuery() {
		return coloniaQuery;
	}

	public void setColoniaQuery(String coloniaQuery) {
		this.coloniaQuery = coloniaQuery;
	}
	
	public String getCalleQuery() {
		return calleQuery;
	}

	public void setCalleQuery(String calleQuery) {
		this.calleQuery = calleQuery;
	}
	
	public String getCodigoQuery() {
		return codigoQuery;
	}

	public void setCodigoQuery(String calleQuery) {
		this.codigoQuery = codigoQuery;
	}
	
	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

//	public class EstadoConverter implements Converter {
//
//		@Override
//		public Object getAsObject(FacesContext context, UIComponent component, String value) {
//			if (value != null && value.trim().length() > 0) {
//				return estadoEJB.find(Integer.parseInt(value));
//			}
//		    return null;
//		}
//
//		@Override
//		public String getAsString(FacesContext context, UIComponent component, Object obj) {
//			return String.valueOf(((Estado) obj).getId());
//		}
//		
//	}

}
