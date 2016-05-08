package ucm.tfg.muvi.services;

import java.io.File;
import java.io.FileWriter;
import java.util.Iterator;
import java.util.logging.*;

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
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.csvreader.CsvWriter;

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
			File file = new File (context.getRealPath("/WEB-INF/classes/ucm/tfg/muvi/services/ratings.csv"));
			//String csv = context.getRealPath("/WEB-INF/classes/ucm/tfg/muvi/services/ratings.csv");
			
			
			CsvWriter csvOutput = new CsvWriter(new FileWriter(file, true), ',');
			csvOutput.endRecord();   
			csvOutput.write(String.valueOf(valoracion.getID_usuario()));
			csvOutput.write(String.valueOf(valoracion.getID_pelicula()));
			csvOutput.write(String.valueOf((float)valoracion.getValoracion()));
			csvOutput.endRecord();   
			csvOutput.close();
			
			/*CSVWriter writer = new CSVWriter(new FileWriter(file,true),',');
			 
			String [] value ={ String.valueOf(valoracion.getID_usuario()),String.valueOf(valoracion.getID_pelicula()),String.valueOf(valoracion.getValoracion())};
			 
			writer.writeNext(value);
			 
			writer.close();
			 */
			
			
			
			//DataModel model = new FileDataModel(file);
			//UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
			//UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.1, similarity, model);
			//UserBasedRecommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);
			//recommender.setPreference(valoracion.getID_usuario(),valoracion.getID_pelicula(), (float)valoracion.getValoracion());
		} catch (Exception e) {
			Logger.getLogger(ServicioValoracion.class.getName()).log(Level.WARNING, e.getMessage(),e);
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
			Logger.getLogger(ServicioValoracion.class.getName()).log(Level.WARNING, e.getMessage(),e);
			return Response.status(422).entity(new ErrorToJson(e.getMessage())).build();
		}
		return Response.status(200).entity(valoracion).build();
	}
}
