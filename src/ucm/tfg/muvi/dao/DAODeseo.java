package ucm.tfg.muvi.dao;


import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import ucm.tfg.muvi.entities.Deseo;
import ucm.tfg.muvi.util.EntityManagerUtil;

public class DAODeseo {
	
	public Deseo crear(Deseo deseo) {
		EntityManager em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
		EntityTransaction transaction = em.getTransaction();
	    transaction.begin();
	    em.persist(deseo);
	    transaction.commit();
	    em.close();
	    return deseo;
	}

	public void eliminar(Deseo deseo) {
		EntityManager em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
		EntityTransaction transaction = em.getTransaction();
	    transaction.begin();
	    em.remove(deseo);
	    transaction.commit();
	    em.close();
	}
	
	
}
