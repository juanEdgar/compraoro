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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import clases.persistence.jpa.commun.embeddable.UsuarioModifico;
import clases.vo.dinero.Moneda;

@Entity
@Table(schema="public", name="ArticuloCompra")
@SequenceGenerator(schema="public", name="seq_ArticuloCompra",  sequenceName="seq_ArticuloCompra" , allocationSize=50)
@Inheritance(strategy=InheritanceType.JOINED)
@DiscriminatorColumn(name="fiIdTipoArticulo",
                     discriminatorType=DiscriminatorType.INTEGER)
public    class ArticuloCompra implements Serializable {
	
	
	private static final long serialVersionUID = -5933541001999578641L;

	@Transient
	private static final Logger log = LogManager .getLogger(ArticuloCompra.class);
	
		@Id @GeneratedValue(generator="seq_ArticuloCompra")
		@Column(name="fiIdArticuloCompra")
		private  int id;
		
		
		
		
		@Column(name="fiIdEstatus")
		private int estatus;
		
		@Column(name="fcDescripcion")
		private String descripcion;
		
		@Column(name="fnValorMonetario")
		private float valor=0.0F;
		
		@ManyToOne
		@JoinColumn(name="fiIdMoneda")
		private Moneda moneda;
		
		@ManyToOne
		@JoinColumn(name="fiIdSeguribolsa")
		private Seguribolsa seguribolsa;
		
		
		
		
		
		private UsuarioModifico usuarioModifico;
		
		
		public ArticuloCompra() {
			// TODO Auto-generated constructor stub
		}
		
		public ArticuloCompra(int id) {
			this.id=id;
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
			
		
		public String llaveBolsaArticulo(){
			String llave=this.seguribolsa.getId()+"-"+this.id;
			log.debug("Llave bolsa articulo "+llave);
			return llave;
		}
	

		
		public Moneda getMoneda() {
			return moneda;
		}

		public void setMoneda(Moneda moneda) {
			this.moneda = moneda;
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
		
		

		public Seguribolsa getSeguribolsa() {
			return seguribolsa;
		}

		public void setSeguribolsa(Seguribolsa seguribolsa) {
			this.seguribolsa = seguribolsa;
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
