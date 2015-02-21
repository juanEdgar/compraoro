package clases.vo.dinero;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(schema="public", name="Moneda")
public class Moneda  implements Serializable{
	

	private static final long serialVersionUID = 646870085991839386L;
	
	
	@Id
	@Column(name="fiIdMoneda")
	private int id;
	@Column(name="fcUnidad")
	private String codigo;
	@Column(name="fcNombre")
	private String nombre;
	
	@Column(name="fcSimbolo")
	private String simbolo;
	
	@OneToMany(mappedBy="monedaBase")	
    private List<TipoDeCambio> tipoCambio;
	
	
	
	public Moneda(){
		
	}
	
	public Moneda(int id){
		this.id=id;
	}
	
	public int hashCode() {
       return this.id;
    }
 
    public boolean equals(Object obj) {
        
    	if(obj==null){
    		return false;
    	}
    	
    	
        if (!(obj instanceof Moneda)){
        	return false;
        }
        
        return  this.id==((Moneda)obj).getId();
    }
        
      
    
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public List<TipoDeCambio> getTipoCambio() {
		return tipoCambio;
	}
	public void setTipoCambio(List<TipoDeCambio> tipoCambio) {
		this.tipoCambio = tipoCambio;
	}

	public String getSimbolo() {
		return simbolo;
	}

	public void setSimbolo(String simbolo) {
		this.simbolo = simbolo;
	}
	
	
	
		
}
