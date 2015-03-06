package clases.business.metales.vo.compra;

import java.io.Serializable;
import java.text.DecimalFormat;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import clases.persistence.jpa.commun.embeddable.UsuarioModifico;
import clases.vo.dinero.Moneda;

@Entity
@Table(schema="public", name="ArticuloCompra")
@SequenceGenerator(schema="public", name="seq_ArticuloCompra",  sequenceName="seq_ArticuloCompra" , allocationSize=50)
@Inheritance(strategy=InheritanceType.JOINED)
@DiscriminatorColumn(name="fiIdTipoArticulo",
                     discriminatorType=DiscriminatorType.INTEGER)
public    class ArticuloCompra implements Serializable {
	
		/**
	 * 
	 */
	private static final long serialVersionUID = -5933541001999578641L;

		@Id @GeneratedValue(generator="seq_ArticuloCompra")
		@Column(name="fiIdArticuloCompra")
		private  int id;
		
		
		@ManyToOne
		@JoinColumn(name="fiIdCompra")
		private Compra compra;
		
		@Column(name="fiIdEstatus")
		private int estatus;
		
		@Column(name="fcDescripcion")
		private String descripcion;
		
		@Column(name="fnValorMonetario")
		private float valor=0.0F;
		
		@ManyToOne
		@JoinColumn(name="fiIdMoneda")
		private Moneda moneda;
		
		@Transient
		private float numeroArticulo;
		
		
		
		private UsuarioModifico usuarioModifico;
		
		
		public ArticuloCompra() {
			// TODO Auto-generated constructor stub
		}
		
		public ArticuloCompra(String usuarioModifico) {
			
			this.usuarioModifico= new UsuarioModifico(usuarioModifico);
		}
		
		
		
		public String getValorFormateado(){
			
			DecimalFormat df2 = new DecimalFormat( "#,###,###,##0.00" );					
			return "$ "+df2.format(this.valor);
		}
		
		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public Compra getCompra() {
			return compra;
		}

		public void setCompra(Compra compra) {
			this.compra = compra;
		}

		public int getEstatus() {
			return estatus;
		}

		public void setEstatus(int estatus) {
			this.estatus = estatus;
		}

		public UsuarioModifico getUsuarioModifico() {
			return usuarioModifico;
		}

		public void setUsuarioModifico(UsuarioModifico usuarioModifico) {
			this.usuarioModifico = usuarioModifico;
		}
			
		
		
	

		
		public Moneda getMoneda() {
			return moneda;
		}

		public void setMoneda(Moneda moneda) {
			this.moneda = moneda;
		}

		public float getNumeroArticulo() {
			return numeroArticulo;
		}

		public void setNumeroArticulo(float numeroArticulo) {
			this.numeroArticulo = numeroArticulo;
		}

		public float getValor() {
			return valor;
		}

		public void setValor(float valor) {
			this.valor = valor;
		}
		
		

		public String getDescripcion() {
			return descripcion;
		}

		public void setDescripcion(String descripcion) {
			this.descripcion = descripcion;
		}

		@Override
		public boolean equals(Object obj) {
		
			if(obj==null){
				return false;
			}
			
			if( !( obj instanceof ArticuloCompra ) ){
				return false;
			}
			
			return ((ArticuloCompra)obj).getId()==this.getId();
			
			
		
		}
		
		@Override
		public int hashCode() {		
			return this.getId();
		}
		
}
