package ucm.tfg.muvi.entities;

import java.util.ArrayList;

public class PeliculaFromAPI {
	
	private long id;
	private String titulo;
	private String sinopsis;
	private ArrayList<String> generos;
	private double votacion;
	private String trailer;
	
	public PeliculaFromAPI() {
		
	}
	
	public PeliculaFromAPI(long id, String titulo, String sinopsis, ArrayList<String> generos, double votacion, String trailer) {
		this.id = id;
		this.titulo = titulo;
		this.sinopsis = sinopsis;
		this.generos = generos;
		this.votacion = votacion;
		this.trailer = trailer;
	}
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
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
