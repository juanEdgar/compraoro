package clases.business.metales.vo.compra;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(schema="public", name="PagoEfectivo")
@DiscriminatorValue("-200")
public class PagoCompraEfectivo  extends PagoCompra{
		
		@Column(name="fnDenominacion")
		private float denominacion;
		
		@Column(name="fiCantidad")
		private int cantidad;

		public float getDenominacion() {
			return denominacion;
		}

		public void setDenominacion(float denominacion) {
			this.denominacion = denominacion;
		}

		public int getCantidad() {
			return cantidad;
		}

		public void setCantidad(int cantidad) {
			this.cantidad = cantidad;
		}
		
		
		
		
	
}
