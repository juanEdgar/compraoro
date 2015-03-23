package clases.business.metales.vo.compra;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import clases.business.metales.vo.cotizador.PrecioMetal;

@Entity
@Table(schema="public",name="ArticuloCompraMetal")
@DiscriminatorValue("-100")
public class ArticuloCompraMetal  extends ArticuloCompra  implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 7175681960930291399L;
		
		
		public ArticuloCompraMetal(){
			
		}
		public ArticuloCompraMetal(String usuarioModifico){
			super(usuarioModifico);
		}
		
		
		
		
		@Column(name="fnPesoNeto")
		private float pesoNeto;
		
		@Column(name="fnPesoBruto")
		private float pesoBruto;
		
		@Column(name="fnPorcentajePureza")
		private float porcentajePureza=0.0F;
		
		@ManyToOne
		@JoinColumn(name="fiidpreciometalgramofino")
		private PrecioMetal precioMetal;
		
		@Column(name="fnPesoFino")
		private float pesoFino;
		
		public float getPesoNeto() {
			return pesoNeto;
		}
		public void setPesoNeto(float pesoNeto) {
			this.pesoNeto = pesoNeto;
		}
		public float getPesoBruto() {
			return pesoBruto;
		}
		public void setPesoBruto(float pesoBruto) {
			this.pesoBruto = pesoBruto;
		}
		public float getPorcentajePureza() {
			return porcentajePureza;
		}
		public void setPorcentajePureza(float porcentajePureza) {
			this.porcentajePureza = porcentajePureza;
		}
		public PrecioMetal getPrecioMetal() {
			return precioMetal;
		}
		public void setPrecioMetal(PrecioMetal precioMetal) {
			this.precioMetal = precioMetal;
		}
		
		
		
		public float getPesoFino() {
			return pesoFino;
		}
		public void setPesoFino(float pesoFino) {
			this.pesoFino = pesoFino;
		}
		@Override
		public String toString() {
			StringBuilder sb= new StringBuilder();
			
			
			sb.append(this.precioMetal.getMetal().getNombre());
			sb.append(" DE ");
			sb.append(this.getPorcentajePureza());
			sb.append("% DE PUREZA CON UN PESO DE  ");
			sb.append(this.getPesoNeto());
			sb.append(" GRAMOS.");
			
			return sb.toString().toUpperCase();
		}
		
	
	
}
