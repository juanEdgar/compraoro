package clases.web.login;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ui.session.SesionObject;

@WebServlet(name = "logoutServlet", urlPatterns = {"/logout"})
public class LogoutServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private static final Logger log = LogManager .getLogger(LogoutServlet.class);
	
	@Inject
	private SesionObject sesion;
	
	protected void doGet(HttpServletRequest request,
		HttpServletResponse response) 
			throws ServletException, IOException {
		
		try{
			log.info("Cerrando sesion");
			
			// Invalidate current HTTP session.
			// Will call LoginModule logout() method
			request.getSession().invalidate();
			sesion=new SesionObject();
			
			
			response.sendRedirect(request.getContextPath() + "/");
			
			log.info("Sesion cerrada");
			
			
		}catch(Exception e){
			log.info("Error al cerrar la sesion "+e.getMessage());
		}
	}

}
