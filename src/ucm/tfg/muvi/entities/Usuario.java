package ucm.tfg.muvi.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "USUARIOS", uniqueConstraints = {
		@UniqueConstraint(columnNames = "ALIAS"),
		@UniqueConstraint(columnNames = "EMAIL")
})
@NamedQueries({ @NamedQuery(name = "Usuario.findByAlias", query = "SELECT u FROM Usuario u WHERE u.alias = :alias"), 
	@NamedQuery(name = "Usuario.findByEmail", query = "SELECT u FROM Usuario u WHERE u.email = :email") })
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private long id;
	
	@Column(name = "ALIAS", unique = true, nullable = false, length = 15)
	private String alias;
	
	@Column(name = "PASSWORD", nullable = false, length = 15)
	private String password;
	
	@Column(name = "NOMBRE", nullable = false, length = 60)
	private String nombre;
	
	@Column(name = "EMAIL", unique = true, nullable = false, length = 20)
	private String email;
	
	public Usuario() {
		
	}
	
	public Usuario(String alias, String password, String nombre,/* String apellidos,*/ String email) {
		this.alias = alias;
		this.password = password;
		this.nombre = nombre;
		this.email = email;
	}
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getAlias() {
		return this.alias;
	}
	
	public void setAlias(String alias) {
		this.alias = alias;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getNombre() {
		return this.nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
}
