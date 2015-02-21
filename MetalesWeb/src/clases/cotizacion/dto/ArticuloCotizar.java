package clases.cotizacion.dto;

import java.text.DecimalFormat;

import javax.persistence.Transient;

import clases.business.metales.vo.compra.ArticuloCompraMetal;
import clases.business.metales.vo.cotizador.Metal;

public class ArticuloCotizar extends ArticuloCompraMetal {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3977220286279209779L;
	@Transient
	private float cotizacion;
	@Transient
	private int numeroArticulo;
	
	@Transient
	private Metal tipoProducto;
	
	
	
	public float getCotizacion() {
		return cotizacion;
	}
	public void setCotizacion(float cotizacion) {
		this.cotizacion = cotizacion;
	}
	public int getNumeroArticulo() {
		return numeroArticulo;
	}
	public void setNumeroArticulo(int numeroArticulo) {
		this.numeroArticulo = numeroArticulo;
	}
	public Metal getTipoProducto() {
		return tipoProducto;
	}
	public void setTipoProducto(Metal tipoProducto) {
		this.tipoProducto = tipoProducto;
	}
	
	public String cotizacionFormateada(){
		
		DecimalFormat df2 = new DecimalFormat( "#,###,###,##0.00" );		
		return "$ "+df2.format( cotizacion );
	}
	
	
	@Override
	public String toString() {
		StringBuilder sb= new StringBuilder();
		
		
		sb.append(this.tipoProducto.getNombre());
		sb.append(" DE ");
		sb.append(this.getPureza().getNombre());
		sb.append(" CON UN PESO DE  ");
		sb.append(this.getPesoNeto());
		sb.append(" GRAMOS.");
		
		return sb.toString().toLowerCase();
	}
	
	
	@Override
	public boolean equals(Object obj) {
		
	
		
		if(obj==null){
			return false;
		}
		if(!(obj instanceof ArticuloCotizar)){
			return false;
		}
		
		
		return this.numeroArticulo==((ArticuloCotizar)obj).getNumeroArticulo();
		
	}
	
	@Override
	public int hashCode() {
		
		return this.numeroArticulo;
	}
	
	

}
