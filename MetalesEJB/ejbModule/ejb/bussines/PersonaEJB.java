package ejb.bussines;

import java.security.InvalidParameterException;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import clases.login.UsuarioSesion;
import clases.persistence.jpa.commun.embeddable.UsuarioModifico;
import clases.persistence.jpa.factory.qualifier.MetalesEM;
import clases.vo.Persona;
import clases.vo.catalogo.Estatus;
import clases.vo.cliente.Cliente;
import ejb.bussines.exception.RDNException;

/**
 * Session Bean implementation class Persona
 */
@Stateless
@LocalBean
public class PersonaEJB {
	
	@Inject @MetalesEM
	private EntityManager metalesEM;
	
	@Inject
	private UsuarioSesion usuarioSesion;
	
	@EJB
	private PropertiesEJB propertiesEJB;
	
	@Resource
	private SessionContext context;
	
	private static final Logger log = LogManager .getLogger(PersonaEJB.class);

    /**
     * Default constructor. 
     */
    public PersonaEJB() {
        
    }
    
    
    public void altaPersona(Persona persona) throws RDNException, Exception{
    	
    	try {
    		log.info("Alta de persona");
        	
        	this.validarInformacionAlta(persona);
    		
        	log.info("Parametros correctos, se procede con el alta");
        	// se procede a dar de alta
        	persona.setEstatusPersona(new Estatus(1));    	

        	persona.setUsuarioModifico(new UsuarioModifico(this.usuarioSesion.getNombreUsuario()));
        	
        	this.metalesEM.persist(persona);
        	log.info("Alta de cliente correcto");
		}
    	catch (Exception e) {
    		if(!(e  instanceof RDNException)){
    			log.error("Error al dar de alta el cliente",e);
    		}
    		this.context.setRollbackOnly();
    		throw  e;
		}
    	
    	
    	
    }
    
    public boolean validarInformacionAlta(Persona persona) throws Exception{
    	
    	// validamos los campos de la persona    	
    	log.info("Se validan los parametros");
    	if(persona==null){
    		throw new InvalidParameterException("El parametro persona es nulo"); 
    	}
    	
    	if( persona.getNombre()==null || persona.getApellidoMaterno()==null 
    	   ||persona.getFechaNacimiento()==null  || persona.getNombre().isEmpty()
    	   || persona.getApellidoPaterno().isEmpty()
    	   ){
    		throw new InvalidParameterException("El campo de nombre, apellido paterno y fecha de nacimiento, son obligarorios");
    	}
    	// se valida el campo obligatoriode tipo de persona, siempre <0
    	
    	if(persona.getIdPersona()!=0){
    		throw new InvalidParameterException("No se puede dar de alta una persona ya existente");
    	}
    	
    	// se valida la fecha de nacimiento
    	if(!this.esMayorDeEdad(persona)){
    		throw new RDNException("El cliente no es mayor de edad");
    	}
    	
    	return true;
    	
    }
    
 private boolean esMayorDeEdad(Persona persona){
    	
	 	if(persona==null  ){
	 		throw new InvalidParameterException("La persona a validar no tiene fecha de nacimiento");
	 	}
	 	
	 	return this.esMayorDeEdad(persona.getFechaNacimiento());
    	
    }
    
  private boolean esMayorDeEdad(Date fechaNacimieto){
    	
    	if(fechaNacimieto==null){
    		throw new InvalidParameterException("La fecha de nacimiento a validar es nula");
    	}
    	
    	
		Calendar calendarFechaNacimiento= Calendar.getInstance();
		calendarFechaNacimiento.setTime(fechaNacimieto);
			
		Calendar calendarioComparar= Calendar.getInstance();
		calendarioComparar.setTime(this.getFechaMaximaMayoriaedad());
		
		if(calendarFechaNacimiento.before(calendarioComparar)){
			return true;
		}else{
			return false;
		}
		
    	
    
    }
  
  public Date  getFechaMaximaMayoriaedad(){
	  	Calendar calendarioComparar= Calendar.getInstance();
		calendarioComparar.set(Calendar.HOUR, 23);
		calendarioComparar.set(Calendar.MINUTE, 59);
		calendarioComparar.add(Calendar.YEAR,-(this.propertiesEJB.getMayoriaDeEdad()));
		return calendarioComparar.getTime();
  }
    
}
