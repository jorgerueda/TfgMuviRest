package ucm.tfg.muvi.services;

import java.io.File;
import java.io.FileReader;

import javax.ws.rs.GET;
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

import au.com.bytecode.opencsv.CSVReader;
import ucm.tfg.muvi.util.ErrorToJson;
import ucm.tfg.muvi.util.EstimacionToJson;

@Path("/recomendador")
public class ServicioRecomendador {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response estimar(@QueryParam("usuario") String usuario, @QueryParam("pelicula") String pelicula ) {
		long userID = Long.parseLong(usuario);
		long filmID = Long.parseLong(pelicula);
		float valor = 0;
		EstimacionToJson estimacion = new EstimacionToJson();
		try {
			DataModel model = new FileDataModel(new File("C:/WorkspaceMaven/MuviAppREST/dataset/ratings.csv"));
			UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
			UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.1, similarity, model);
			UserBasedRecommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);
			valor = recommender.estimatePreference(userID, filmID);
			if (Float.isNaN(valor)) {
				float media = calcularMedia(filmID);
				estimacion.setEstimacion(media);
			} else {
				estimacion.setEstimacion(valor);
			}
		} catch (Exception e) {
			return Response.status(422).entity(new ErrorToJson(e.getMessage())).build();
		}
		return Response.status(200).entity(estimacion).build();
	}
	
	private float calcularMedia(Long pelicula) {
		int cont = 0;
		float suma = 0;
		CSVReader csvReader;
		try {
			File fichero = new File("C:/WorkspaceMaven/MuviAppREST/dataset/ratings.csv");
		    FileReader freader = new FileReader(fichero);    
			csvReader = new CSVReader(freader);
			String[] nextLine;
			while ((nextLine = csvReader.readNext()) != null) {
				if (Float.parseFloat(nextLine[1]) == pelicula) {
					cont++;
					suma += Float.parseFloat(nextLine[2]);
				} 
			}
			csvReader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		float media = suma / cont;
		return media;
	}
}
