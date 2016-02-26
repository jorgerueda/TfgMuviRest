package ucm.tfg.muvi.entities;

import java.util.ArrayList;

public class PeliculaFromAPI {
	
	private long id_IMDB;
	private long id_TheMovieDB;
	private String titulo;
	private String sinopsis;
	private ArrayList<String> generos;
	private double votacion;
	private String trailer;
	
	public PeliculaFromAPI() {
		
	}
	
	public PeliculaFromAPI(long id_IMDB, long id_TheMovieDB, String titulo, String sinopsis, ArrayList<String> generos, double votacion, String trailer) {
		this.id_IMDB = id_IMDB;
		this.id_TheMovieDB = id_TheMovieDB;
		this.titulo = titulo;
		this.sinopsis = sinopsis;
		this.generos = generos;
		this.votacion = votacion;
		this.trailer = trailer;
	}
	
	public long getId_IMDB() {
		return this.id_IMDB;
	}
	
	public void setId_IMDB(long id_IMDB) {
		this.id_IMDB = id_IMDB;
	}
	
	public long getId_TheMovieDB() {
		return this.id_TheMovieDB;
	}
	
	public void setId_TheMovieDB(long id_TheMovieDB) {
		this.id_TheMovieDB = id_TheMovieDB;
	}
	
	public String getTitulo() {
		return this.titulo;
	}
	
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	public String getSinopsis() {
		return this.sinopsis;
	}
	
	public void setSinopsis(String sinopsis) {
		this.sinopsis = sinopsis;
	}
	
	public ArrayList<String> getGeneros() {
		return this.generos;
	}
	
	public void setGeneros(ArrayList<String> generos) {
		this.generos = generos;
	}
	
	public double getVotacion() {
		return this.votacion;
	}
	
	public void setVotacion(double votacion) {
		this.votacion = votacion;
	}
	
	public String getTrailer() {
		return this.trailer;
	}
	
	public void setTrailer(String trailer) {
		this.trailer = trailer;
	}
}
