package ucm.tfg.muvi.services;



import java.util.ArrayList;
import java.util.Iterator;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.bson.Document;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import ucm.tfg.muvi.dao.DAOBeacon;
import ucm.tfg.muvi.dao.DAOUsuario;
import ucm.tfg.muvi.entities.Beacon;
import ucm.tfg.muvi.util.ErrorToJson;

@Path("/beacon")
public class ServicioBeacon {
	
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response agregar(String data) throws JSONException {
		JSONObject json = new JSONObject(data);
		Iterator<?> val = json.keys();
		Beacon beacon = new Beacon();
		String key;
		while (val.hasNext()) {
			key = (String) val.next();
		    if (key.equals("id_usuario")) {
		    	beacon.setID_usuario(Long.parseLong(json.getString("id_usuario")));
		    } else if (key.equals("id_beacon")) {
		    	beacon.setID_beacon(Long.parseLong(json.getString("id_beacon")));
		    }
		}
		DAOUsuario daoUser = new DAOUsuario();
		beacon.setName(daoUser.buscarPorID(beacon.getID_usuario()));
		DAOBeacon dao = new DAOBeacon();
		try {
			dao.crear(beacon);
		} catch (Exception e) {
			return Response.status(422).entity(new ErrorToJson(e.getMessage())).build();
		}
		return Response.status(201).entity(beacon).build();
	}
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listar(@QueryParam("id_beacon") String id_beacon) throws JSONException {
		
		Long beacon = Long.parseLong(id_beacon);
		ArrayList<Document> lista;
		DAOBeacon dao = new DAOBeacon();
		try {
			lista = dao.listar(beacon);
		} catch (Exception e) {
			return Response.status(422).entity(new ErrorToJson(e.getMessage())).build();
		}
		return Response.status(200).entity(lista).build();
	}
	
	
	
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response eliminar(String data) throws JSONException {
		JSONObject json = new JSONObject(data);
		Iterator<?> val = json.keys();
		Beacon beacon = new Beacon();
		String key;
		while (val.hasNext()) {
			key = (String) val.next();
		    if (key.equals("id_usuario")) {
		    	beacon.setID_usuario(Long.parseLong(json.getString("id_usuario")));
		    } else if (key.equals("id_beacon")) {
		    	beacon.setID_beacon(Long.parseLong(json.getString("id_beacon")));
		    }
		}
		
		DAOBeacon dao = new DAOBeacon();
		try {
			dao.eliminar(beacon);
		} catch (Exception e) {
			return Response.status(422).entity(new ErrorToJson(e.getMessage())).build();
		}
		return Response.status(200).entity(beacon).build();
	}
	
	
	
	
}



