package ucm.tfg.muvi.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import ucm.tfg.muvi.dao.DAOPelicula;
import ucm.tfg.muvi.entities.Pelicula;
import ucm.tfg.muvi.entities.PeliculaFromAPI;
import ucm.tfg.muvi.util.ErrorToJson;

@Path("/peliculas")
public class ServicioPelicula {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
    public Response buscarInfo(@QueryParam("titulo") String title) throws IOException, JSONException {
		title = title.replace(' ', '+');
		String url = "http://api.themoviedb.org/3/search/movie?query=" + title + "&api_key=1e8e4b46f1a22adcbbf8dc3633e12465";
		JSONObject json = getJsonOfResponse(url, "GET");
		
		PeliculaFromAPI pelicula = new PeliculaFromAPI();
		Iterator<?> movies = json.keys();
		boolean fin = false;
		String key;
		while (movies.hasNext() && !fin) {
		    key = (String) movies.next();
		    if (key.equals("results")) {
		    	JSONArray values = json.getJSONArray(key);
		    	if (values.length() == 0) {
		    		return Response.status(422).entity(new ErrorToJson("pelicula no encontrada")).build();
		    	}
		    	JSONObject info = values.getJSONObject(0);
		    	String sinopsis = info.getString("overview");
		    	String id = info.getString("id");
		    	String titulo = info.getString("original_title");
		    	pelicula.setSinopsis(sinopsis);
		    	pelicula.setId_TheMovieDB(Long.parseLong(id)); 
		    	pelicula.setTitulo(titulo);
		    	fin = true;
		    }
		}
		
		url = "http://api.themoviedb.org/3/movie/" + pelicula.getId_TheMovieDB() + "?api_key=1e8e4b46f1a22adcbbf8dc3633e12465";
		json = getJsonOfResponse(url, "GET");
		
		Iterator<?> movie = json.keys();
		fin = false;
		String votacion = json.getString("vote_average");
		pelicula.setVotacion(Double.parseDouble(votacion));
		while (movie.hasNext() && !fin) {
			key = (String) movie.next();
			if (key.equals("genres")) {
				JSONArray genres = json.getJSONArray("genres");
				ArrayList<String> generos = new ArrayList<String>(genres.length());
				for (int i = 0; i < genres.length(); i++) {
					generos.add(genres.getJSONObject(i).getString("name"));
				}
				pelicula.setGeneros(generos);
			} else if (key.equals("imdb_id")) {
			    String id_con_tt =  json.getString("imdb_id");
			    String[] id_sin_tt = id_con_tt.split("t");
			    if(!id_con_tt.equals("null")){
			    pelicula.setId_IMDB(Long.parseLong(id_sin_tt[2]));}
		    	fin = true;
		    }
		}
		
		url = "http://api.themoviedb.org/3/movie/" + pelicula.getId_TheMovieDB() + "/videos?api_key=1e8e4b46f1a22adcbbf8dc3633e12465";
		json = getJsonOfResponse(url, "GET");
		
		Iterator<?> video = json.keys();
		fin = false;
		String ruta = "https://www.youtube.com/watch?v=";
		while (video.hasNext() && !fin) {
			key = (String) video.next();
		    if (key.equals("results")) {
		    	JSONArray values = json.getJSONArray(key);
		    	JSONObject info = values.getJSONObject(0);
			    ruta = ruta + info.getString("key");
			    pelicula.setTrailer(ruta);
		    	fin = true;
		    }
		}
		
		//url = "http://www.omdbapi.com/?t=" + title;
		//json = getJsonOfResponse(url, "GET");
		
		//movie = json.keys();
		//fin = false;
		Pelicula peli = new Pelicula();
		peli.setTitulo(pelicula.getTitulo());
		peli.setId_IMDB(pelicula.getId_IMDB());
		
	/*	while (movie.hasNext() && !fin) {
			key = (String) movie.next();
		    if (key.equals("imdbID")) {
			    String id_con_tt =  json.getString("imdbID");
			    String[] id_sin_tt = id_con_tt.split("t");
			    peli.setId_IMDB(Long.parseLong(id_sin_tt[2]));
			    pelicula.setId_IMDB(Long.parseLong(id_sin_tt[2]));
		    	fin = true;
		    }
		}*/
		
		DAOPelicula dao = new DAOPelicula();
		dao.crear(peli);
		
		return Response.status(200).entity(pelicula).build();
    }
	
	private JSONObject getJsonOfResponse(String url, String metodo) throws IOException, JSONException {
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod(metodo);

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		String respuesta = response.toString();
		JSONObject json = new JSONObject(respuesta);
		return json;
	}
	
	@Path("/list")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
    public Response buscarPeliculas(@QueryParam("titulo") String title) throws IOException, JSONException {
		ArrayList<PeliculaFromAPI> peliculas = new ArrayList<PeliculaFromAPI>();

		title = title.replace(' ', '+');
		String url = "http://api.themoviedb.org/3/search/movie?query=" + title + "&api_key=1e8e4b46f1a22adcbbf8dc3633e12465";
		JSONObject json = getJsonOfResponse(url, "GET");
		
		PeliculaFromAPI pelicula;
		Iterator<?> movies = json.keys();
		boolean fin = false;
		String key;
		while (movies.hasNext() && !fin) {
		    key = (String) movies.next();
		    if (key.equals("results")) {
		    	JSONArray values = json.getJSONArray(key);
		    	if (values.length() == 0) {
		    		return Response.status(422).entity(new ErrorToJson("pelicula no encontrada")).build();
		    	}
		    	
		    	for (int t= 0; t< values.length();t++){
		    	pelicula = new PeliculaFromAPI();
		    	JSONObject info = values.getJSONObject(t);
		    	String sinopsis = info.getString("overview");
		    	String id = info.getString("id");
		    	String titulo = info.getString("original_title");
		    	pelicula.setSinopsis(sinopsis);
		    	pelicula.setId_TheMovieDB(Long.parseLong(id)); 
		    	pelicula.setTitulo(titulo);
		    	fin = true;
		
		url = "http://api.themoviedb.org/3/movie/" + pelicula.getId_TheMovieDB() + "?api_key=1e8e4b46f1a22adcbbf8dc3633e12465";
		json = getJsonOfResponse(url, "GET");
		
		Iterator<?> movie = json.keys();
		fin = false;
		String votacion = json.getString("vote_average");
		pelicula.setVotacion(Double.parseDouble(votacion));
		while (movie.hasNext() && !fin) {
			key = (String) movie.next();
			if (key.equals("genres")) {
				JSONArray genres = json.getJSONArray("genres");
				ArrayList<String> generos = new ArrayList<String>(genres.length());
				for (int i = 0; i < genres.length(); i++) {
					generos.add(genres.getJSONObject(i).getString("name"));
				}
				pelicula.setGeneros(generos);
			} else if (key.equals("imdb_id")) {
			    String id_con_tt =  json.getString("imdb_id");
			    String[] id_sin_tt = id_con_tt.split("t");
			    if(!id_con_tt.equals("null")){
			    pelicula.setId_IMDB(Long.parseLong(id_sin_tt[2]));}
		    	fin = true;
		    }
		}
		
		url = "http://api.themoviedb.org/3/movie/" + pelicula.getId_TheMovieDB() + "/videos?api_key=1e8e4b46f1a22adcbbf8dc3633e12465";
		json = getJsonOfResponse(url, "GET");
		
		Iterator<?> video = json.keys();
		fin = false;
		String ruta = "https://www.youtube.com/watch?v=";
		while (video.hasNext() && !fin) {
			key = (String) video.next();
		    if (key.equals("results")) {
		    	JSONArray values2 = json.getJSONArray(key);
		    	if (values2.length()!=0){
		    	JSONObject info2 = values2.getJSONObject(0);
			    ruta = ruta + info2.getString("key");
			    pelicula.setTrailer(ruta);
		    	fin = true;}
		    	else{ fin =true;}
		    }
		}
		
		Pelicula peli = new Pelicula();
		peli.setTitulo(pelicula.getTitulo());
		peli.setId_IMDB(pelicula.getId_IMDB());
		DAOPelicula dao = new DAOPelicula();
		dao.crear(peli);
		peliculas.add(pelicula);
		    	} //for
		    }//if
				}//while
				
		return Response.status(200).entity(peliculas).build();
    }
}
