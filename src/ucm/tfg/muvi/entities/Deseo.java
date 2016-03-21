package ucm.tfg.muvi.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "DESEOS", uniqueConstraints = {
		@UniqueConstraint(columnNames = "ID_USUARIO"),
		@UniqueConstraint(columnNames = "ID_PELICULA")
})
public class Deseo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private long id;
	
	@Column(name = "ID_USUARIO", unique = true, nullable = false, length = 15)
	private int id_usuario;
	
	@Column(name = "ID_PELICULA", nullable = false, length = 15)
	private int id_pelicula;
	
	
	public Deseo() {
		
	}
	
	public Deseo(int id_usuario, int id_pelicula) {
		this.id_pelicula = id_pelicula;
		this.id_usuario = id_usuario;
	
	}
	
	public int getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(int id_usuario) {
		this.id_usuario = id_usuario;
	}

	public int getId_pelicula() {
		return id_pelicula;
	}

	public void setId_pelicula(int id_pelicula) {
		this.id_pelicula = id_pelicula;
	}

	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	
}