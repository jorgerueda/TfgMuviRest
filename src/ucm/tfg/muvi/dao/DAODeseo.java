package ucm.tfg.muvi.dao;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import ucm.tfg.muvi.entities.Deseo;
import ucm.tfg.muvi.util.EntityManagerUtil;

public class DAODeseo {
	
	public Deseo crear(Deseo deseo) {
		EntityManager em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
		EntityTransaction transaction = em.getTransaction();
	    transaction.begin();
	    em.persist(deseo);
	    em.flush();
	    em.refresh(deseo);
	    transaction.commit();
	    em.close();
	    return deseo;
	}

	public void eliminar(Deseo deseo) throws Exception {
		EntityManager em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
		EntityTransaction transaction = em.getTransaction();
	    transaction.begin();
	    Deseo d= buscar(deseo.getClave().getId_usuario(),deseo.getClave().getId_pelicula());
	    if (d !=null){
	    em.remove(em.contains(d) ? d: em.merge(d));}
	    else{
	    	
	 	  throw new Exception("No existe este deseo");
	 	    
	    }
	    transaction.commit();
	    em.close();
	}
	
	public boolean consultarDeseo (Deseo deseo) {
		
		Deseo d= buscar(deseo.getClave().getId_usuario(),deseo.getClave().getId_pelicula());
	    if (d !=null){
	    return true;
	    
	    }else{
	    	
	 	return false;
	 	    
	    }
		
		
	}
	
	public Deseo buscar(int usuario, int pelicula){
		EntityManager em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
		EntityTransaction transaction = em.getTransaction();
	    transaction.begin();
	    new Deseo();
	    TypedQuery<Deseo> query = em.createQuery(
	    	      "SELECT d FROM Deseo d WHERE d.clave.id_pelicula = :pelicula AND d.clave.id_usuario = :usuario", Deseo.class)
	    .setParameter("pelicula", pelicula)
	    .setParameter("usuario", usuario);
	    
	    List<Deseo> results = query.getResultList();
	    
	    Deseo deseo=null;
	    
	    if(!results.isEmpty()){
	        // ignores multiple results
	    	deseo = (Deseo) results.get(0);
	    }
	   
	    em.close();
	    return deseo;
	}
	
	
}
