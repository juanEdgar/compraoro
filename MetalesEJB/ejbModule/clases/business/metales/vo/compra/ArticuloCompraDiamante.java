package clases.business.metales.vo.compra;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import clases.business.metales.vo.cotizador.PrecioDiamante;

@Entity
@Table(schema="public",name="ArticuloCompraDiamante")
@DiscriminatorValue("-101")
public class ArticuloCompraDiamante  extends ArticuloCompra  implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 7175681960930291399L;
		
		
		public ArticuloCompraDiamante(){
			
		}
					
		@ManyToOne
		@JoinColumn(name="fiIdPrecioDiamante")
		private PrecioDiamante precioDiamante;
		
		@Column(name="fnQuilates")
		private float quilaes;
		
		public String toString() {
			StringBuilder sb= new StringBuilder();
			
			sb.append("Diamante de ");
			sb.append(this.quilaes);
			sb.append(" quilaes, ");
//			sb.append("color  ");
//			sb.append(this.getPrecioDiamante().getTipoDiamante().getColor().getColor());
//			sb.append(", categoria ");
//			sb.append(this.getPrecioDiamante().getTipoDiamante().getPunto().getRangoPuntos());
//			sb.append(", limpieza ");
//			sb.append(this.getPrecioDiamante().getTipoDiamante().getLimpieza().getLimpieza());
			
			return sb.toString().toUpperCase();
		}

		public PrecioDiamante getPrecioDiamante() {
			return precioDiamante;
		}

		public void setPrecioDiamante(PrecioDiamante precioDiamante) {
			this.precioDiamante = precioDiamante;
		}

		public float getQuilaes() {
			return quilaes;
		}

		public void setQuilaes(float quilaes) {
			this.quilaes = quilaes;
		}
		
		
		
	
	
}
