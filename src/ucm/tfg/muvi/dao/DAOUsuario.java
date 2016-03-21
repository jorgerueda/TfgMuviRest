package ucm.tfg.muvi.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import ucm.tfg.muvi.entities.Usuario;
import ucm.tfg.muvi.util.EntityManagerUtil;

public class DAOUsuario {
	
	public Usuario crear(Usuario usuario) throws Exception {
		EntityManager em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
		EntityTransaction transaction = em.getTransaction();
	    transaction.begin();
	    ArrayList<Usuario> usersByAlias = (ArrayList<Usuario>) em.createNamedQuery("Usuario.findByAlias", Usuario.class)
	    		.setParameter("alias", usuario.getAlias()).getResultList();
	    if (usersByAlias.size() > 0) {
	    	throw new Exception("nombre de usuario no disponible");
	    }
	    ArrayList<Usuario> usersByEmail = (ArrayList<Usuario>) em.createNamedQuery("Usuario.findByEmail", Usuario.class)
	    		.setParameter("email", usuario.getEmail()).getResultList();
	    if (usersByEmail.size() > 0) {
	    	throw new Exception("correo electronico no disponible");
	    }
	    em.persist(usuario);
	    transaction.commit();
	    em.close();
	    return usuario;
	}
	
	public Usuario buscar(String usuario, String pass) {
		EntityManager em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
		EntityTransaction transaction = em.getTransaction();
	    transaction.begin();
	    new Usuario();
	    TypedQuery<Usuario> query = em.createQuery(
	    	      "SELECT u FROM Usuario u WHERE u.alias = :alias AND u.password = :pass", Usuario.class)
	    .setParameter("alias", usuario)
	    .setParameter("pass", pass);
	    
	    List<Usuario> results = query.getResultList();
	    
	    Usuario user=null;
	    
	    if(!results.isEmpty()){
	        // ignores multiple results
	    	user = (Usuario) results.get(0);
	    }
	   
	    em.close();
	    return user;
	}
}
