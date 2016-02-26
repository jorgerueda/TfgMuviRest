package ucm.tfg.muvi.dao;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import ucm.tfg.muvi.entities.Pelicula;
import ucm.tfg.muvi.util.EntityManagerUtil;

public class DAOPelicula {
	
	public void crear(Pelicula pelicula) {
		EntityManager em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
		EntityTransaction transaction = em.getTransaction();
	    transaction.begin();
	    ArrayList<Pelicula> pelisById = (ArrayList<Pelicula>) em.createNamedQuery("Pelicula.findById", Pelicula.class)
	    		.setParameter("id", pelicula.getId_IMDB()).getResultList();
	    if (pelisById.size() == 0) {
	    	 em.persist(pelicula);
	    }
	    transaction.commit();
	    em.close();
	}
}
