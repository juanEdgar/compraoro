package clases.util.compra;

import clases.business.metales.vo.cotizador.Diamante;

public class FilaPreciosDiamante {
	
	
	private Diamante[] diamante= new Diamante[4];
	
	
	public void setPrecioLimpieza1(Diamante d){
		this.diamante[0]=d;
	}
	
	public Diamante getPrecioLimpieza1(){
		return this.diamante[0];
	}
	
	public void setPrecioLimpieza2(Diamante d){
		this.diamante[1]=d;
	}
	
	public Diamante getPrecioLimpieza2(){
		return this.diamante[1];
	}
	
	public void setPrecioLimpieza3(Diamante d){
		this.diamante[2]=d;
	}
	
	public Diamante getPrecioLimpieza3(){
		return this.diamante[2];
	}
	
	public void setPrecioLimpieza4(Diamante d){
		this.diamante[3]=d;
	}
	
	public Diamante getPrecioLimpieza4(){
		return this.diamante[3];
	}

	public Diamante[] getDiamante() {
		return diamante;
	}

	public void setDiamante(Diamante[] diamante) {
		this.diamante = diamante;
	}
	
	

	
	
	
	
}
