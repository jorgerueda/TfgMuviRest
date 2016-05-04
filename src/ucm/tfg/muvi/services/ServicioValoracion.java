package ucm.tfg.muvi.services;

import java.io.File;
import java.util.Iterator;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.model.DataModel;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import ucm.tfg.muvi.dao.DAOValoracion;
import ucm.tfg.muvi.entities.Valoracion;
import ucm.tfg.muvi.util.ErrorToJson;

@Path("/valoraciones")
public class ServicioValoracion {
	@javax.ws.rs.core.Context
	ServletContext context;

	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response nueva(String rating) throws JSONException {
		JSONObject json = new JSONObject(rating);
		Iterator<?> val = json.keys();
		Valoracion valoracion = new Valoracion();
		String key;
		while (val.hasNext()) {
			key = (String) val.next();
		    if (key.equals("id_usuario")) {
		    	valoracion.setID_usuario(Long.parseLong(json.getString("id_usuario")));
		    } else if (key.equals("id_pelicula")) {
		    	valoracion.setID_pelicula(Long.parseLong(json.getString("id_pelicula")));
		    } else if (key.equals("valoracion")) {
		    	valoracion.setValoracion(Long.parseLong(json.getString("valoracion")));
		    }
		}
		
		DAOValoracion dao = new DAOValoracion();
		try {
			dao.crear(valoracion);
			File file = new File (context.getRealPath("WEB-INF/classes/ucm/tfg/muvi/services/ratings.csv"));
			DataModel model = new FileDataModel(file);
			model.setPreference(valoracion.getID_usuario(),valoracion.getID_pelicula(), valoracion.getValoracion());
			
		} catch (Exception e) {
			return Response.status(422).entity(new ErrorToJson(e.getMessage())).build();
		}
		return Response.status(201).entity(valoracion).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscar(@QueryParam("usuario") Long usuario, @QueryParam("pelicula") Long pelicula) {
		Valoracion valoracion = new Valoracion();
		valoracion.setID_usuario(usuario);
		valoracion.setID_pelicula(pelicula);
		
		DAOValoracion dao = new DAOValoracion();
		try {
			valoracion = dao.mostrar(valoracion);
		} catch (Exception e) {
			return Response.status(422).entity(new ErrorToJson(e.getMessage())).build();
		}
		return Response.status(200).entity(valoracion).build();
	}
}
