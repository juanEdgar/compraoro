package ejb.bussines.compra;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import clases.login.UsuarioSesion;
import clases.persistence.jpa.commun.embeddable.UsuarioModifico;
import clases.persistence.jpa.factory.qualifier.MetalesEM;
import clases.vo.cliente.Cliente;
import ejb.bussines.PersonaEJB;
import ejb.bussines.exception.RDNException;

/**
 * Session Bean implementation class ClienteEJB
 */
@Stateless
@LocalBean
public class ClienteEJB extends PersonaEJB{
	
	@Inject @MetalesEM
	private EntityManager metalesEM;
	
	@Inject
	private UsuarioSesion usuarioSesion;
	
	@Resource
	private SessionContext context;
	
	@EJB
	private PersonaEJB personaEJB;
	
	private static final Logger log = LogManager .getLogger(ClienteEJB.class);

    /**
     * Default constructor. 
     */
    public ClienteEJB() {
        
    }
    
    public Cliente altaCliente(Cliente c) throws RDNException, Exception{
    	
    	
    	try{
    		
    		log.info("Alta de cliente");
    		
    		
    		
    		
    		if(usuarioSesion.getTienda()== null || usuarioSesion.getTienda().getId()<=0){
    			throw new Exception("La tienda en sesion es invalida, no se puede dar de alta al cliente");
    		}
    		
    		log.info("Tienda de sesion correcta, ID: "+usuarioSesion.getTienda().getId());
    		
    		log.info("Se manda a validar la informacion de la persona");
    		
    		this.validarInformacionAlta(c);
    		
    		log.info("Se asigna el cliente a la tienda en sesion");
    		
    		c.setTienda((this.usuarioSesion).getTienda());
    		
    		if(c.getUsuarioModificoCliente()==null){
    			c.setUsuarioModificoCliente(new UsuarioModifico(usuarioSesion.getNombreUsuario()));
    		}
    		
    		log.info("Regisrando la informacion de cliente");
    		
    		this.metalesEM.persist(c);
    		
    		log.info("alta de cliente exitosa");
    		
    		// se regresa el cliente creado
    		
    		return c;
    		
    	}catch(Exception e){
    		if(!(e  instanceof RDNException)){
    			log.error("Error al dar de alta el cliente",e);
    		}
    		this.context.setRollbackOnly();
    		throw e;
    		
    	}
    	
    }
    
    
    public List<Cliente> buscarCliente(Cliente c) throws RDNException, Exception{
    	if(c.getIdPersona()==0){
    		return this.buscarClientePorNombre(c);
    	}else{
    		Cliente cDB=null;
    		cDB= this.buscarClientePorID(c);
    		
    		if(cDB!=null){
    			List<Cliente> clientes= new ArrayList<Cliente>();
    			clientes.add(cDB);
    			return clientes;
    		}else{
    			return null;
    		}
    	}
    }
    

    
    public Cliente buscarClientePorID(Cliente c) throws Exception{
    	
    	
    	
    	log.info("Buscando cliente por ID");
    	
    	// se valida el cliente a buscar
		if(c==null || c.getIdPersona()==0){
			throw new RDNException("Para buscar un cliente por ID es necesario que sea difenrente de cero");
		}
    	
    	try {
			
    		log.info("Recuperando el cliente");
    		return this.metalesEM.find(Cliente.class, c.getIdPersona());
    		
		} catch (Exception e) {
				log.error("Error al buscar el cliente",e);
				throw e;
		}
    	
    	
    }

    
    
    public List<Cliente> buscarClientePorNombre(Cliente c) throws RDNException, Exception{
    	
    	
    	List<Cliente> listaClientes=null;
    	
    	
    	// se valida el cliente a buscar
		if(c==null || c.getFechaNacimiento()==null || c.getApellidoPaterno()==null || c.getNombre()==null
		    || c.getApellidoPaterno().trim().equals("") || c.getNombre().trim().equals("")	
		  ){
			throw new RDNException("Para buscar un cliente es necesaria la fecha de nacimiento, apellido paterno y primer nombre");
		}
    	
    	try {
			
    		log.info("Recuperando la lista de clientes");
    		
    		boolean tieneMaterno=false,
    				tieneSegundoNombre=false;
    		
    		
    		
    		TypedQuery<Cliente> query=null;
    		boolean sinResultados= false;
    		
    		try{
    			

        		StringBuilder consulta= new StringBuilder("SELECT C FROM Cliente C where c.fechaNacimiento= :fecha and c.apellidoPaterno=:paterno and c.nombre= :nombre");
        		
        		if(c.getApellidoMaterno()!=null && !c.getApellidoMaterno().trim().equals("")){
        			consulta.append(" and C.apellidoMaterno = :materno ");
        			tieneMaterno=true;
        		}
        		
        		if(c.getSegundoNombre()!=null && !c.getSegundoNombre().trim().equals("")){
        			consulta.append(" and C.segundoNombre = :segundoNombre");
        			tieneSegundoNombre=true;
        		}
    			
    			 query= metalesEM.createQuery( consulta.toString() ,Cliente.class);
    	    	 query.setParameter("fecha", c.getFechaNacimiento());
    	    	 query.setParameter("paterno",c.getApellidoPaterno());
    	    	 query.setParameter("nombre", c.getNombre());
    	    	 
    	    	 if(tieneMaterno){
    	    		 query.setParameter("materno",c.getApellidoMaterno());
    	    	 }
    	    	 if(tieneSegundoNombre){
    	    		 query.setParameter("segundoNombre", c.getSegundoNombre() );
    	    	 }
    	    	 
    	    	 listaClientes=query.getResultList();
    	    	     	    	 
    	    	 
    		}catch(javax.persistence.NoResultException nre){
    			sinResultados=true;
    		}
    		
    		if(listaClientes==null || sinResultados || listaClientes.size()==0){
    			return null;
    		}
    		
    		log.info("Lista de clientes recuperados: "+listaClientes.size());
    		return listaClientes;
    		
    		
		} catch (Exception e) {
				log.error("Error al buscar el cliente",e);
				throw e;
		}
    	
    	
    }
    

}
