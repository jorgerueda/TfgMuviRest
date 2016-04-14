package ucm.tfg.muvi.dao;

import java.util.ArrayList;
import java.util.Collection;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.CommandResult;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;

import ucm.tfg.muvi.entities.Gps;
import ucm.tfg.muvi.entities.Gps.Loc;
import ucm.tfg.muvi.util.MongoClientUtil;

public class DAOGps {
	
	public void crear(Gps gps) throws Exception {
		MongoDatabase db = MongoClientUtil.getMongoDatabase();
		
			db.getCollection("gps").replaceOne(new Document("id_usuario", gps.getId_usuario()),
					new Document()
						.append("id_usuario", gps.getId_usuario())
						.append("name", gps.getName())
						.append("loc", new Document()
								.append("lat", gps.getLoc().getLatitud())
								.append("lon",gps.getLoc().getLongitud()))
					,new UpdateOptions().upsert(true));
		
	}

	public String listar(Loc coor, long id_usuario, double distancia) throws Exception {
		MongoDatabase db = MongoClientUtil.getMongoDatabase();
		/*db.getCollection("gps").createIndex(new BasicDBObject("loc", "2d"));
		BasicDBObject filter = new BasicDBObject("$near", new double[] {coor.getLatitud(), coor.getLongitud() });
		filter.put("$maxDistance", distancia);
		BasicDBObject query = new BasicDBObject("loc", filter);
		ArrayList<Document> busqueda = db.getCollection("gps").find(query).into(new ArrayList<Document>());*/
		//
		db.getCollection("gps").createIndex(new BasicDBObject("loc", "2d"));
		 BasicDBObject myCmd = new BasicDBObject();
		 myCmd.append("geoNear", "gps");
		 double[] loc = {coor.getLatitud(),coor.getLongitud()};
		 myCmd.append("near", loc);
		 myCmd.append("spherical", true);
		 myCmd.append("maxDistance",distancia );
		 System.out.println(myCmd);
		 Document myResults = db.runCommand(myCmd);
		
		 String prueba =myResults.toJson();
		 //
		
		return prueba;
	}
	public void eliminar (Gps gps) throws Exception {
		
		MongoDatabase db = MongoClientUtil.getMongoDatabase();
		Long count= db.getCollection("gps").deleteOne(
				new Document("id_usuario", gps.getId_usuario())).getDeletedCount();
		
		if (count == 0 ) {
			throw new Exception("ese usuario no existe en el beacon");
		} 
		
	}

}
