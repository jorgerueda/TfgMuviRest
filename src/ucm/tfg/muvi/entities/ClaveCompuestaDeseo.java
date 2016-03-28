package ucm.tfg.muvi.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ClaveCompuestaDeseo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "ID_USUARIO", nullable = false, length = 15)
	private int id_usuario;
	
	@Column(name = "ID_PELICULA", nullable = false, length = 15)
	private int id_pelicula;

	
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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id_usuario;

		result = prime * result + id_pelicula;
		return result;
	}
 
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClaveCompuestaDeseo other = (ClaveCompuestaDeseo) obj;
		if (id_usuario != other.id_usuario)
			return false;
		if (id_pelicula != other.id_pelicula)
			return false;
		return true;
	}

}
