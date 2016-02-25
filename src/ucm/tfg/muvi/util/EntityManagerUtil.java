package ucm.tfg.muvi.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerUtil {
	
	public static EntityManagerFactory getEntityManagerFactory() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("mysqldb-ds");
	    return emf;
	}
}
