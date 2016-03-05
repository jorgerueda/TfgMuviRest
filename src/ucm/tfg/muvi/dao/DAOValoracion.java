package ucm.tfg.muvi.dao;

import java.util.ArrayList;

import org.bson.Document;

import com.mongodb.client.MongoDatabase;

import ucm.tfg.muvi.entities.Valoracion;
import ucm.tfg.muvi.util.MongoClientUtil;

public class DAOValoracion {
	
	public void crear(Valoracion valoracion) throws Exception {
		MongoDatabase db = MongoClientUtil.getMongoDatabase();
		ArrayList<Document> busqueda = db.getCollection("prueba").find(
				new Document("origen", "muvi").append("id_usuario", valoracion.getID_usuario())
				.append("id_pelicula", valoracion.getID_pelicula())).into(new ArrayList<Document>());
		if (busqueda.isEmpty()) {
			db.getCollection("prueba").insertOne(
					new Document()
						.append("origen", "muvi")
						.append("id_usuario", valoracion.getID_usuario())
						.append("id_pelicula", valoracion.getID_pelicula())
						.append("valoracion", valoracion.getValoracion())
					);
		} else {
			throw new Exception("ese usuario ya ha valorado esa pel�cula");
		}
	}
}
