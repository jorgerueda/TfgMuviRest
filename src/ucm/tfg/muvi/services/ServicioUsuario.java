package ucm.tfg.muvi.services;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ucm.tfg.muvi.dao.DAOUsuario;
import ucm.tfg.muvi.entities.Usuario;
import ucm.tfg.muvi.util.ErrorToJson;


@Path("/usuarios")
public class ServicioUsuario {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
    public Response registro(Usuario usuario) { 
		DAOUsuario dao = new DAOUsuario();
		Usuario user;
		try {
			user = dao.crear(usuario);
		} catch (Exception e) {
			
			Logger.getLogger(ServicioUsuario.class.getName()).log(Level.WARNING, e.getMessage(),e);
			return Response.status(409).entity(new ErrorToJson(e.getMessage())).build();
		}
		return Response.status(201).entity(user).build();
    }
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{usuario}")
	public Response login(@PathParam("usuario") String usuario, String pass) {
		String password[] = pass.split("\"");
		
		DAOUsuario dao = new DAOUsuario();
		Usuario user = null;
	    try{
		user = dao.login(usuario,password[3]);
	    }catch (Exception e){
	    	
			Logger.getLogger(ServicioUsuario.class.getName()).log(Level.WARNING,e.getMessage(),e);	    	
	    }
	    if(user!=null){
	    	return Response.status(200).entity(user).build() ;
	    }else{
	    	return Response.status(401).build() ;

	    }
	    
	    
	    
	  }
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response Search(@QueryParam("usuario") String usuario) {
		DAOUsuario dao = new DAOUsuario();
		Usuario user = null;

		try{
	    user = dao.buscarPorNombre(usuario);
	    
		}catch (Exception e){
			Logger.getLogger(ServicioValoracion.class.getName()).log(Level.WARNING, e.getMessage(),e);

		}
	    if(user!=null){
	    	return Response.status(200).entity(user).build() ;
	    }else{
	    	
	    	return Response.status(401).build() ;

	    }
	    
	    
	    
	  }
}
