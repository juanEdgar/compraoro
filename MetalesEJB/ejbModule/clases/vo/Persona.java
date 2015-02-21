package clases.vo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import clases.persistence.jpa.commun.embeddable.UsuarioModifico;
import clases.vo.catalogo.Estatus;


@Entity
@Table(schema="public",name="Persona")
@SequenceGenerator(name="seq_persona", schema="public", sequenceName="seq_persona",allocationSize=50 )
@Inheritance(strategy=InheritanceType.JOINED)
@DiscriminatorColumn(name="fiidestatustipopersona",
                     discriminatorType=DiscriminatorType.INTEGER)
public class Persona {
	
	
	public Persona(int id){
		this();
		this.idPersona=id;
	}
	
	public Persona() {
	
		this.estatusPersona= new Estatus(1);
	}
	
	public Persona(String usuarioModifico){
		this();
		this.usuarioModifico= new UsuarioModifico(usuarioModifico);
	}
	
	
	@Id @GeneratedValue(generator="seq_persona")
	@Column(name="fiIdPersona")
	private int idPersona=0;
	
	@Column(name="fcNombre")
	private String nombre;
	
	@Column(name="fcSegundoNombre")
	private String segundoNombre;
	
	@Column(name="fcApellidoPaterno")
	private String apellidoPaterno;
	
	@Column(name="fcApellidoMaterno")
	private String apellidoMaterno;
	
	@Column(name="fdFechaNacimiento")
	@Temporal(TemporalType.DATE)
	private Date fechaNacimiento;
	
	@Column(name="fcRFC")
	private String RFC;
	

	@OneToOne
	@JoinColumn(name="fiIdEstatus")
	protected Estatus estatusPersona;
	
	private UsuarioModifico usuarioModifico;

	public int getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(int idPersona) {
		this.idPersona = idPersona;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre==null?nombre:nombre.toUpperCase().trim();
	}

	public String getSegundoNombre() {
		return segundoNombre;
	}

	public void setSegundoNombre(String segundoNombre) {
		this.segundoNombre = segundoNombre==null?segundoNombre:segundoNombre.toUpperCase().trim();
	}

	public String getApellidoPaterno() {
		return apellidoPaterno;
	}

	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno==null?apellidoPaterno: apellidoPaterno.toUpperCase().trim();
	}

	public String getApellidoMaterno() {
		return apellidoMaterno;
	}

	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno==null?apellidoMaterno:apellidoMaterno.toUpperCase().trim();
	}

	
	
	
	public String getFechaNacimientoFormateada( ) throws ParseException {
			
		if(this.fechaNacimiento==null){
			return null;
		}
		
		 return new SimpleDateFormat("dd/MM/yyyy", new Locale("es","MX")).format(this.fechaNacimiento);
	
	}


	public void setFechaNacimientoFormateada(String fechaNacimiento) throws ParseException {
			
			System.out.println("Fecha : "+ fechaNacimiento);
		
			this.fechaNacimiento = new SimpleDateFormat("dd/MM/yyyy", new Locale("es","MX")).parse(fechaNacimiento);
		
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getRFC() {
		return RFC;
	}

	public void setRFC(String RFC) {
		RFC = RFC==null?RFC:RFC.toUpperCase();
	}



	public Estatus getEstatusPersona() {
		return estatusPersona;
	}

	public void setEstatusPersona(Estatus estatusPersona) {
		this.estatusPersona = estatusPersona;
	}

	

	public void setUsuarioModifico(UsuarioModifico usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if(obj==null){
			return false;
		}
		
		if(!(obj instanceof Persona)){
			return false;
		}
		
		Persona p =(Persona)obj;
		
		return p.getIdPersona()==this.getIdPersona();
	}
	
	@Override
	public int hashCode() {		
		return Integer.valueOf(this.getIdPersona()).hashCode();
	}

	
	
	
}
