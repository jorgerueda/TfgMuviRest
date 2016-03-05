package ucm.tfg.muvi.util;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class MongoClientUtil {
	
	public static MongoDatabase getMongoDatabase() {
		@SuppressWarnings("resource")
		MongoClient mongoClient = new MongoClient();
		MongoDatabase db = mongoClient.getDatabase("muviapp");
		return db;
	}
}
