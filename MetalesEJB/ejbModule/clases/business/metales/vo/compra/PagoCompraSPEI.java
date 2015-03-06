package clases.business.metales.vo.compra;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(schema="public", name="PagoSPEI")
@DiscriminatorValue("-201")
public class PagoCompraSPEI extends PagoCompra{


	@Column(name="fcReferencia")
	private String referencia;
	
	@Column(name="fcBancoOrigen")
	private String bancoOrigen;
	
	@Column(name="fcBancoDestino")
	private String bancoDestino;
	
	@Column(name="fcCuenta")
	private String cuentaDestino;
	
	@Column(name="fcTipoCuenta")
	private String tipoCuenta;
	
	@Column(name="fcTitular")
	private String titular;
	
	@Column(name="fcCodigoRastreo")
	private String codigoRastreo;
	
	public String getReferencia() {
		return referencia;
	}
	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}
	public String getBancoOrigen() {
		return bancoOrigen;
	}
	public void setBancoOrigen(String bancoOrigen) {
		this.bancoOrigen = bancoOrigen;
	}
	public String getBancoDestino() {
		return bancoDestino;
	}
	public void setBancoDestino(String bancoDestino) {
		this.bancoDestino = bancoDestino;
	}
	public String getCuentaDestino() {
		return cuentaDestino;
	}
	public void setCuentaDestino(String cuentaDestino) {
		this.cuentaDestino = cuentaDestino;
	}
	public String getTipoCuenta() {
		return tipoCuenta;
	}
	public void setTipoCuenta(String tipoCuenta) {
		this.tipoCuenta = tipoCuenta;
	}
	public String getTitular() {
		return titular;
	}
	public void setTitular(String titular) {
		this.titular = titular;
	}
	public String getCodigoRastreo() {
		return codigoRastreo;
	}
	public void setCodigoRastreo(String codigoRastreo) {
		this.codigoRastreo = codigoRastreo;
	}
	
	
}
