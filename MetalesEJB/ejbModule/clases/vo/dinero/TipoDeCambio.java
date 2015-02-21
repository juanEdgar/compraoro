package clases.vo.dinero;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import clases.login.UsuarioSesion;
import clases.persistence.jpa.commun.embeddable.UsuarioModifico;

@Entity
@Table(schema="public", name="v_TipoCambio")
@SequenceGenerator(name="seqtc", schema="public",sequenceName="seq_tc", initialValue=-900000,allocationSize=1)
public class TipoDeCambio implements Serializable{
	
		
	private static final long serialVersionUID = -8636040500545312195L;
	
	@Transient
	private boolean invertido=false;
	
	
	@Id @GeneratedValue(generator="seqtc")
	@Column(name="fiidtipodecambio")	
	private int id;
		
	
	@ManyToOne
	@JoinColumn(name="fiIdMoneda")
	private Moneda monedaBase;
	
	
	@ManyToOne
	@JoinColumn(name="fiIdMonedaCambio")
	private Moneda monedaCambio;
	
	@Column(name="fdTipoDeCambio")
	private float valor;
	
	@Column(name="fiidestatus")
	private int estatus;
	
	
	private UsuarioModifico usuarioModifico;
	
	public TipoDeCambio(){
		this.invertido=false;
	}
	
	public TipoDeCambio(String usuarioModifico){
		this.usuarioModifico= new UsuarioModifico(usuarioModifico);
	}
		
	public int hashCode() {
        if(this.monedaBase==null  || this.monedaCambio==null){
        	return 0;
        }
        else{
        	return (this.monedaBase.getNombre().hashCode())+this.monedaCambio.getNombre().hashCode();
        }
    }
 
    public boolean equals(Object obj) {
        
    	if(obj==null){
    		return false;
    	}
    	
    	
        if (!(obj instanceof TipoDeCambio)){
        	return false;
        }
        
        TipoDeCambio comparar=(TipoDeCambio)obj;
        
        return  this.monedaBase.getId()==comparar.monedaBase.getId()&&this.monedaCambio.getId()==comparar.getMonedaCambio().getId();
        
      
    }
  
   
    
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Moneda getMonedaBase() {
		
		if(this.invertido){
			return this.monedaCambio;
		}
		
		return monedaBase;
	}
	public void setMonedaBase(Moneda monedaBase) {
		
		
		if(this.invertido){
			this.monedaCambio= monedaBase;
		}else{
			this.monedaBase = monedaBase;
		}
	}
	public Moneda getMonedaCambio() {
		if(this.invertido){
			return this.monedaBase;
		}
		return monedaCambio;
	}
	public void setMonedaCambio(Moneda monedaCambio) {
		if(this.invertido){
			this.monedaBase= monedaCambio;
		}else{
			this.monedaCambio = monedaCambio;
		}
	}
	public float getValor() {
		
		if(invertido){
			return 1.0F/valor;
		}
		
		return valor;
	}
	public void setValor(float valor) {
		if(this.invertido){
			valor=1.0F/valor;
		}
		this.valor = valor;
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

	public boolean isInvertido() {
		return invertido;
	}

	public void setInvertido(boolean invertido) {
		this.invertido = invertido;
	}
	
	
	

}
