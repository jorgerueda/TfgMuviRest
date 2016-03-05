package ucm.tfg.muvi.entities;

public class Valoracion {
	
	private long id_usuario;
	
	private long id_pelicula;
	
	private long valoracion;
	
	public Valoracion() {}
	
	public Valoracion(long id_usuario, long id_pelicula, long valoracion) {
		this.id_usuario = id_pelicula;
		this.id_pelicula = id_pelicula;
		this.valoracion = valoracion;
	}
	
	public long getID_usuario() {
		return this.id_usuario;
	}
	
	public void setID_usuario(long id_usuario) {
		this.id_usuario = id_usuario;
	}
	
	public long getID_pelicula() {
		return this.id_pelicula;
	}
	
	public void setID_pelicula(long id_pelicula) {
		this.id_pelicula = id_pelicula;
	}

	public long getValoracion() {
		return this.valoracion;
	}
	
	public void setValoracion(long valoracion) {
		this.valoracion = valoracion;
	}
}
