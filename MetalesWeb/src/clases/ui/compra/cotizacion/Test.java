package clases.ui.compra.cotizacion;

import java.io.IOException;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import clases.login.UsuarioSesion;
import clases.vo.tienda.Tienda;
import ejb.bussines.TestEjb;
import ejb.bussines.administracion.TipoDeCambioEJB;
import ejb.bussines.compra.CotizadorEJB;

/**
 * Servlet implementation class Test
 */
@WebServlet( urlPatterns="/Test")
public class Test extends HttpServlet {
	
	@EJB
	CotizadorEJB cotizador;
	
	@EJB
	TestEjb test;
	
	
	@EJB
	TipoDeCambioEJB tcEjb;
	
	@Inject
	private UsuarioSesion user;

	
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Test() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			
		test.Test();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
