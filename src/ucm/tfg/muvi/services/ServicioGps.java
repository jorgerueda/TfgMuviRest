package ucm.tfg.muvi.services;


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

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import ucm.tfg.muvi.dao.DAOGps;
import ucm.tfg.muvi.dao.DAOUsuario;
import ucm.tfg.muvi.entities.Gps;
import ucm.tfg.muvi.entities.Gps.Loc;
import ucm.tfg.muvi.util.ErrorToJson;

@Path("/geolocalizacion")

public class ServicioGps {
	
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	
	public Response agregar(String data) throws JSONException {
		JSONObject json = new JSONObject(data);
		DAOUsuario daoUser = new DAOUsuario();
		Iterator<?> val = json.keys();
		Gps gps = new Gps();
		String key;
		while (val.hasNext()) {
			key = (String) val.next();
		    if (key.equals("id_usuario")) {
		    	gps.setId_usuario(Long.parseLong(json.getString("id_usuario")));
		    } else if (key.equals("longitud")) {
		    	gps.getLoc().setLongitud(Double.parseDouble(json.getString("longitud")));
		    }else if (key.equals("latitud")) {
		    	gps.getLoc().setLatitud(Double.parseDouble(json.getString("latitud")));
		    }
		    
		    gps.setName(daoUser.buscarPorID(gps.getId_usuario()));
		    
		}
		//DAOUsuario daoUser = new DAOUsuario();
		//gps.setName(daoUser.buscarPorID(gps.getID_usuario()));
		DAOGps dao = new DAOGps();
		try {
			dao.crear(gps);
		} catch (Exception e) {
			return Response.status(422).entity(new ErrorToJson(e.getMessage())).build();
		}
		return Response.status(201).entity(gps).build();
	}
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listar(@QueryParam("lat") String latitud,@QueryParam("lon") String longitud,@QueryParam("max") String distancia) throws JSONException {

		JSONArray usersGps = new JSONArray();
		Gps gps = new Gps();
		Loc coor = gps.new Loc();
		coor.setLatitud(Double.parseDouble(latitud));
		coor.setLongitud(Double.parseDouble(longitud));
		double dis = Double.parseDouble(distancia)/1609.344;
		dis= dis /3959.8728;
		String lista;
		DAOGps dao = new DAOGps();
		try {
			lista = dao.listar(coor,1,dis);
			
			JSONObject json = new JSONObject(lista);
			Iterator<?> users = json.keys();
			boolean fin = false;
			String key;
			while (users.hasNext() && !fin) {
				DAOUsuario daoUser = new DAOUsuario();
				 key = (String) users.next();
				    if (key.equals("results")) {
				    	JSONArray values = json.getJSONArray(key);
				    	if (values.length() == 0) {
				    		return Response.status(422).entity(new ErrorToJson("No hay usuarios cercanos")).build();
				    	}
				
				    	for (int t= 0; t< values.length();t++){
				    		JSONObject jsonRespuesta = new JSONObject();
					    	JSONObject info = values.getJSONObject(t);			
					    	JSONObject subInfo =info.getJSONObject("obj");
					    	JSONObject infoUser =subInfo.getJSONObject("id_usuario");
					    	Double distanciaMetros=Double.parseDouble( info.getString("dis"))*3959.8728;
					    	distanciaMetros=distanciaMetros*1609.344 ;
							jsonRespuesta.put("id_usuario",Long.parseLong(infoUser.getString("$numberLong")));
							jsonRespuesta.put("name", daoUser.buscarPorID(Long.parseLong(infoUser.getString("$numberLong"))));
							jsonRespuesta.put("distancia",distanciaMetros );
							
							usersGps.put(jsonRespuesta);
							
				    	
				    	}
				
				    }
				
			}
			
			
		} catch (Exception e) {
			return Response.status(422).entity(new ErrorToJson(e.getMessage())).build();
		}
		return Response.status(200).entity(usersGps).build();
	}
	
	
	
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public Response eliminar(@QueryParam("id_usuario") Long id_usuario) throws JSONException {
		Gps gps = new Gps();
		
		gps.setId_usuario(id_usuario);
		
		DAOGps dao = new DAOGps();
		try {
			dao.eliminar(gps);
		} catch (Exception e) {
			return Response.status(422).entity(new ErrorToJson(e.getMessage())).build();
		}
		return Response.status(200).build();
	}
	
	
	
	
}

