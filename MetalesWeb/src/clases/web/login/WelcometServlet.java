package clases.web.login;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import clases.login.UsuarioSesion;
import clases.vo.tienda.caja.TiendaCajaEfectivo;

@WebServlet(name = "welcomeServlet", urlPatterns = {"/index"})
public class WelcometServlet extends HttpServlet {

	
	@Inject
	private UsuarioSesion usuario;
	
	
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request,
		HttpServletResponse response) 
			throws ServletException, IOException {
		
		if(request.isUserInRole("admin")){
			response.sendRedirect(request.getContextPath() + "/ui/administracion/welcome.faces");
			usuario.setNombreUsuario(request.getUserPrincipal().getName());
			usuario.getTienda().setId(0);
			usuario.getTienda().setNombre("Central");
			
		}
		
		if(request.isUserInRole("cajero")){
			response.sendRedirect(request.getContextPath() + "/ui/cajero/cotizacion.faces");

			usuario.setNombreUsuario(request.getUserPrincipal().getName());			
			usuario.getTienda().setId(1);
			usuario.getTienda().setNombre("Tienda Centro");
			TiendaCajaEfectivo caja= new TiendaCajaEfectivo();
			caja.setTienda(usuario.getTienda());
			caja.setId(1);
			usuario.setCaja(caja);
			
			
			
		}
		
		
		
		
	}

}
