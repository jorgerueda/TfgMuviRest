package ucm.tfg.muvi.dao;

import java.util.ArrayList;

import org.bson.Document;

import com.mongodb.client.MongoDatabase;

import ucm.tfg.muvi.entities.Beacon;
import ucm.tfg.muvi.util.MongoClientUtil;

public class DAOBeacon {
	
	public void crear(Beacon beacon) throws Exception {
		MongoDatabase db = MongoClientUtil.getMongoDatabase();
		ArrayList<Document> busqueda = db.getCollection("beacons").find(
				new Document("id_usuario", beacon.getID_usuario())
				.append("id_beacon", beacon.getID_beacon())).into(new ArrayList<Document>());
		if (busqueda.isEmpty()) {
			db.getCollection("beacons").insertOne(
					new Document()
						.append("id_usuario", beacon.getID_usuario())
						.append("id_beacon", beacon.getID_beacon())
						.append("name", beacon.getName())
					);
		} else {
			throw new Exception("ese usuario ya existe en el beacon");
		}
	}

	public ArrayList<Document> listar(String id_beacon) throws Exception {
		MongoDatabase db = MongoClientUtil.getMongoDatabase();
		ArrayList<Document> busqueda = db.getCollection("beacons").find(
				new Document("id_beacon", id_beacon)).into(new ArrayList<Document>());
		if (busqueda.isEmpty()) {
			throw new Exception("ese beacon no tiene usuarios");
		} else {
			return busqueda;
		}
	}
	
	public void eliminar (Beacon beacon) throws Exception {
		MongoDatabase db = MongoClientUtil.getMongoDatabase();
		Long count = db.getCollection("beacons").deleteOne(
				new Document("id_usuario", beacon.getID_usuario())
				.append("id_beacon", beacon.getID_beacon())).getDeletedCount();
		if (count == 0) {
			throw new Exception("ese usuario no existe en ese beacon");
		} 
	}
}