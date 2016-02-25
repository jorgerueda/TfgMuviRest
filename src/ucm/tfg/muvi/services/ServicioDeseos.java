package ucm.tfg.muvi.services;

import java.io.IOException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONException;

import ucm.tfg.muvi.dao.DAODeseo;
import ucm.tfg.muvi.entities.Deseo;

@Path("/deseos")
public class ServicioDeseos {
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
    public Response addDeseo (Deseo deseo) throws IOException, JSONException {
		DAODeseo dao = new DAODeseo();
		Deseo d = dao.crear(deseo);
		return Response.status(201).entity(d).build();
		
    }
	

}

