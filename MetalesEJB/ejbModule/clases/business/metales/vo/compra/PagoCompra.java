package clases.business.metales.vo.compra;

import java.util.Date;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import clases.persistence.jpa.commun.embeddable.UsuarioModifico;
import clases.vo.dinero.Moneda;


@Entity
@Table(schema="public", name="PagoCompra")
@SequenceGenerator(schema="pubic", sequenceName="seq_pagoCompa", name="seq_pagoCompra", allocationSize=50)
@Inheritance(strategy=InheritanceType.JOINED)
@DiscriminatorColumn(name="fiidestatustipocompra",
discriminatorType=DiscriminatorType.INTEGER  )
public class PagoCompra {


	@Id
	@GeneratedValue(generator="seq_pagoCompra")
	private int id;
	
	@ManyToOne
	@JoinColumn(name="fiIdCompra")
	private Compra compra;
	
	@Column(name="fdFechaCompra")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fecha;
	
	
	@Column(name="fiIdEstatus")
	private int estatus;
	
	@ManyToOne
	@JoinColumn(name="fiIdMoneda")
	private Moneda moneda; 
	
	private UsuarioModifico usuarioModifico;
	
	
	public PagoCompra() {
		// TODO Auto-generated constructor stub
	}
	
	public PagoCompra(String usuarioModifico) {
		
		this.usuarioModifico= new UsuarioModifico(usuarioModifico);
	}
	
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public int getEstatus() {
		return estatus;
	}

	public void setEstatus(int estatus) {
		this.estatus = estatus;
	}

	public Moneda getMoneda() {
		return moneda;
	}

	public void setMoneda(Moneda moneda) {
		this.moneda = moneda;
	}

	public UsuarioModifico getUsuarioModifico() {
		return usuarioModifico;
	}

	public void setUsuarioModifico(UsuarioModifico usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}

	@Override
	public boolean equals(Object obj) {
		
		if(obj==null){
			return false;
		}
		if(!( obj instanceof PagoCompra )){
			return false;
		}
		
		return  ((PagoCompra)obj).id==this.id;
	}
	
	@Override
	public int hashCode() {
	
		return this.id;
	}
	
	
}
