package ucm.tfg.muvi.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "PELICULAS")
@NamedQueries({ @NamedQuery(name = "Pelicula.findById", query = "SELECT u FROM Pelicula u WHERE u.id_IMDB = :id") })
public class Pelicula {
	
	@Id
	@Column(name = "ID_IMDB", unique = true, nullable = false)
	private long id_IMDB; // IMDB
	
	@Column(name = "TITULO", nullable = false, length = 50)
	private String titulo; // TheMovieDB
	
	public Pelicula() {
		
	}
	
	public Pelicula(long id_IMDB, String titulo) {
		this.id_IMDB = id_IMDB;
		this.titulo = titulo;
	}
	
	public long getId_IMDB() {
		return this.id_IMDB;
	}
	
	public void setId_IMDB(long id_IMDB) {
		this.id_IMDB = id_IMDB;
	}
	
	public String getTitulo() {
		return this.titulo;
	}
	
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
}
