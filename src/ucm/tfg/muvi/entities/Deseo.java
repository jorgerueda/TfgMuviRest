package ucm.tfg.muvi.entities;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.GenericGenerator;

@Entity
@JsonIgnoreProperties (ignoreUnknown = true)
@Table(name = "DESEOS")
public class Deseo {
	
	//@GeneratedValue (strategy= GenerationType.AUTO)
	//@Generated(value = GenerationTime.INSERT)
	//@GenericGenerator (name="idGenerator",strategy="sequence")
	@Column(columnDefinition= "serial",name = "ID", unique = true, nullable = false)
	private long id;
	
	@EmbeddedId
     private ClaveCompuestaDeseo clave;
	
	
	
	
	public Deseo() {
		
	}
	
	public Deseo(ClaveCompuestaDeseo clave) {
		this.clave = clave	;
	}
	
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public ClaveCompuestaDeseo getClave() {
		return clave;
	}

	public void setClave(ClaveCompuestaDeseo clave) {
		this.clave = clave;
	}
	
	
}