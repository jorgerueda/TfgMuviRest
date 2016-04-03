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

@Path("/beacons")
public class ServicioBeacon {
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response agregar(String datos) throws JSONException {
		JSONObject json = new JSONObject(datos);
		Iterator<?> val = json.keys();
		Beacon beacon = new Beacon();
		String key;
		while (val.hasNext()) {
			key = (String) val.next();
		    if (key.equals("id_usuario")) {
		    	beacon.setID_usuario(Long.parseLong(json.getString("id_usuario")));
		    } else if (key.equals("id_beacon")) {
		    	beacon.setID_beacon(json.getString("id_beacon"));
		    }
		}
		DAOUsuario dao_user = new DAOUsuario();
		String nombre = dao_user.buscarPorID(beacon.getID_usuario());
		if (nombre != null) {
			beacon.setName(nombre);
			DAOBeacon dao_beacon = new DAOBeacon();
			try {
				dao_beacon.crear(beacon);
			} catch (Exception e) {
				return Response.status(422).entity(new ErrorToJson(e.getMessage())).build();
			}
			return Response.status(201).entity(beacon).build();
		} else {
			return Response.status(422).entity(new ErrorToJson("ese usuario no existe")).build();
		}
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listar(@QueryParam("beacon") String id_beacon) throws JSONException {
		ArrayList<Document> lista_usuarios;
		DAOBeacon dao = new DAOBeacon();
		try {
			lista_usuarios = dao.listar(id_beacon);
		} catch (Exception e) {
			return Response.status(422).entity(new ErrorToJson(e.getMessage())).build();
		}
		return Response.status(200).entity(lista_usuarios).build();
	}
	
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public Response eliminar(@QueryParam("usuario") String id_usuario, @QueryParam("beacon") String id_beacon) throws JSONException {
		Beacon beacon = new Beacon();
		beacon.setID_usuario(Long.parseLong(id_usuario));
		beacon.setID_beacon(id_beacon);
		DAOUsuario dao_user = new DAOUsuario();
		String nombre = dao_user.buscarPorID(beacon.getID_usuario());
		if (nombre != null) {
			beacon.setName(nombre);
			DAOBeacon dao_beacon = new DAOBeacon();
			try {
				dao_beacon.eliminar(beacon);
			} catch (Exception e) {
				return Response.status(422).entity(new ErrorToJson(e.getMessage())).build();
			}
			return Response.status(204).entity(null).build();
		} else {
			return Response.status(422).entity(new ErrorToJson("ese usuario no existe")).build();
		}
	}
}



