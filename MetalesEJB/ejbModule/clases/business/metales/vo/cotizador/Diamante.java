package clases.business.metales.vo.cotizador;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(schema="public", name="Diamante")
public class Diamante extends Producto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1940420409096201522L;
	
	
	public Diamante(){
		
	}
	
	public Diamante(int id){
		super(id);
		this.id=id;
	}
	
	@Id
	@Column(name="fiIdTipoDiamante")
	private int id;
	
	@ManyToOne
	@JoinColumn(name="fiIdDiamanteColor")
	@Basic(fetch=FetchType.EAGER)
	@OrderBy("id")
	private  DiamanteColor color;
	
	@ManyToOne
	@JoinColumn(name="fiIdDiamantePunto")
	@Basic(fetch=FetchType.EAGER)
	@OrderBy("id")
	private DiamantePunto punto;
	
	@ManyToOne
	@JoinColumn(name="fiIdDiamanteLimpieza")
	@Basic(fetch=FetchType.EAGER)
	@OrderBy("id")
	private DiamanteLimpieza limpieza;
	
	@Column(name="fiIdEstatus")
	private int estatus;
	
	
	@OneToOne(mappedBy="tipoDiamante")	
	private PrecioDiamante precio;
	
	
	@Transient
	private float quilates=0.0F;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public DiamanteColor getColor() {
		return color;
	}

	public void setColor(DiamanteColor color) {
		this.color = color;
	}

	public DiamantePunto getPunto() {
		return punto;
	}

	public void setPunto(DiamantePunto punto) {
		this.punto = punto;
	}

	public DiamanteLimpieza getLimpieza() {
		return limpieza;
	}

	public void setLimpieza(DiamanteLimpieza limpieza) {
		this.limpieza = limpieza;
	}

	public int getEstatus() {
		return estatus;
	}

	public void setEstatus(int estatus) {
		this.estatus = estatus;
	}
	
	
	
	public float getQuilates() {
		return quilates;
	}

	public void setQuilates(float quilates) {
		this.quilates = quilates;
	}

	
	
	
	public PrecioDiamante getPrecio() {
		return precio;
	}

	public void setPrecio(PrecioDiamante precio) {
		this.precio = precio;
	}

	@Override
	public boolean equals(Object obj) {
		
		if(obj==null){
			return false;
		}
		
		if(!(obj instanceof Diamante)){
			return false;
		}
		
		return ((Diamante)obj).getId()==this.getId();
	}
	
	@Override
	public int hashCode() {
		
		return this.id;
	}
	
	
	
}
