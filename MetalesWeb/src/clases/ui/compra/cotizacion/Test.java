package clases.ui.compra.cotizacion;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import clases.business.metales.vo.cotizador.Metal;
import clases.business.metales.vo.cotizador.PurezaMetal;
import clases.login.QualifierUsuarioSesionTienda;
import clases.login.UsuarioSesion;
import clases.login.UsuarioSesionTienda;
import clases.vo.cliente.Calle;
import clases.vo.cliente.CodigoPostal;
import clases.vo.cliente.Colonia;
import clases.vo.cliente.Direccion;
import clases.vo.cliente.Estado;
import clases.vo.cliente.Municipio;
import clases.vo.tienda.Tienda;
import ejb.bussines.TestEjb;
import ejb.bussines.administracion.TipoDeCambioEJB;
import ejb.bussines.compra.CalleEJB;
import ejb.bussines.compra.CodigoPostalEJB;
import ejb.bussines.compra.ColoniaEJB;
import ejb.bussines.compra.CotizadorEJB;
import ejb.bussines.compra.DireccionEJB;
import ejb.bussines.compra.EstadoEJB;
import ejb.bussines.compra.MunicipioEJB;

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
	
	@Inject @QualifierUsuarioSesionTienda
	private UsuarioSesion user;
	
	@EJB
	private EstadoEJB estadoEJB;
	
	@EJB
	private MunicipioEJB municipioEJB;
	
	@EJB
	private ColoniaEJB coloniaEJB;
	
	@EJB
	private CalleEJB calleEJB;
	
	@EJB
	private CodigoPostalEJB cpEJB;
	
	@EJB
	private DireccionEJB direccionEJB;

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

			//// VERIFICAR MUNICIPIO ////
			/* String estadoName = request.getParameter("estado");
			String municipioName = request.getParameter("municipio");
			Estado estado = estadoEJB.buscarEstadosPorNombre(estadoName).get(0);

			Municipio m = new Municipio();
			m.setNombre(municipioName);
			m.setEstado(estado);		
			municipioEJB.altaMunicipio(m);
			System.out.println(m.toString()); */
			
			//// verificar colonia ////
			String estadoName = request.getParameter("estado");
			String municipioName = request.getParameter("municipio");
			String coloniaName = request.getParameter("colonia");
			String calleName = request.getParameter("calle");
			String cpString = request.getParameter("cp");
			String ext = request.getParameter("ext");
			
			Estado estado = estadoEJB.buscarEstadosPorNombre(estadoName).get(0);
			List <Municipio> municipios = municipioEJB.obtenerMunicipiosPorEstadoYNombre(estado, municipioName);
			List<Colonia> colonias = coloniaEJB.obtenerColoniasPorMunicipioYNombre(municipios.get(0), coloniaName);
			List<Calle> calles = calleEJB.obtenerCallePorColonia(colonias.get(0));
			List<CodigoPostal> codigos = cpEJB.obtenerCodigosPorMunicipioYCodigo(municipios.get(0), cpString);
			
			Direccion dir = new Direccion();
			dir.setCodigo(codigos.get(0));
			dir.setCalle(calles.get(0));
			dir.setColonia(colonias.get(0));
			dir.setNumeroExterior(ext);
			
			direccionEJB.altaDireccion(dir);
			
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
