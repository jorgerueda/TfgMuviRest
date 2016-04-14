package ucm.tfg.muvi.services;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.mongodb.util.JSON;

import ucm.tfg.muvi.dao.DAODeseo;
import ucm.tfg.muvi.dao.DAOPelicula;
import ucm.tfg.muvi.entities.ClaveCompuestaDeseo;
import ucm.tfg.muvi.entities.Deseo;
import ucm.tfg.muvi.util.ErrorToJson;

@Path("/deseos")
@JsonIgnoreProperties (ignoreUnknown = true)
public class ServicioDeseos {
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
    public Response addDeseo (String deseo) throws IOException, JSONException {
		JSONObject json = new JSONObject(deseo);
		Iterator<?> val = json.keys();
		ClaveCompuestaDeseo clave = new ClaveCompuestaDeseo();
		String key;
		while (val.hasNext()) {
			key = (String) val.next();
		    if (key.equals("id_usuario")) {
		    	clave.setId_usuario(Integer.parseInt(json.getString("id_usuario")));
		    } else if (key.equals("id_pelicula")) {
		    	clave.setId_pelicula(Integer.parseInt(json.getString("id_pelicula")));
		    } 
		}
		
		DAODeseo dao = new DAODeseo();
		Deseo des = new Deseo (clave);
		Deseo d = dao.crear(des);
		JSONObject jsonRespuesta = new JSONObject();
		jsonRespuesta.put("id", d.getId());
		jsonRespuesta.put("id_usuario", d.getClave().getId_usuario());
		jsonRespuesta.put("id_pelicula", d.getClave().getId_pelicula());
		
		return Response.status(201).entity(jsonRespuesta).build();
		
    }
	

	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public Response remove(@QueryParam("usuario") int usuario, @QueryParam("pelicula") int pelicula) {
		DAODeseo dao = new DAODeseo();
		ClaveCompuestaDeseo cd = new ClaveCompuestaDeseo();
		cd.setId_pelicula(pelicula);
		cd.setId_usuario(usuario);
		Deseo d = new Deseo(cd);
	    try {
			dao.eliminar(d);
		} catch (Exception e) {
			return Response.status(422).entity(new ErrorToJson(e.getMessage())).build();
		}
	    
	    return Response.status(204).build();
	    
	    
	    
	  }
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response consultar(@QueryParam("usuario") int usuario, @QueryParam("pelicula") int pelicula) {
		DAODeseo dao = new DAODeseo();
		ClaveCompuestaDeseo cd = new ClaveCompuestaDeseo();
		cd.setId_pelicula(pelicula);
		cd.setId_usuario(usuario);
		Deseo d = new Deseo(cd);
		boolean result=dao.consultarDeseo(d);
	    String str= "{resultado:"+"'"+result+"'"+"}";
	    return Response.status(201).entity(JSON.parse(str)).build();
	    
	    
	    
	  }
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{usuario}")
	public Response listarDeseos(@PathParam("usuario") int usuario) {
		DAODeseo dao = new DAODeseo();
		DAOPelicula daoPeli = new DAOPelicula();
		ClaveCompuestaDeseo cd = new ClaveCompuestaDeseo();
		JSONArray result = new JSONArray();
		cd.setId_usuario(usuario);
		Deseo d = new Deseo(cd);
		List<Deseo> deseos;
		try {
			deseos = dao.listarDeseos(d);
			for (Deseo des: deseos){
				
				JSONObject jsonRespuesta = new JSONObject();
				jsonRespuesta.put("id_pelicula", des.getClave().getId_pelicula());
				jsonRespuesta.put("titulo", daoPeli.buscarPorId(des.getClave().getId_pelicula()).getTitulo());
				result.put(jsonRespuesta);
				
			}
			
			
		} catch (Exception e) {
			return Response.status(409).entity(new ErrorToJson(e.getMessage())).build();
		}
	  
	    
		return Response.status(201).entity(result).build();
	    
	  }
	

}

