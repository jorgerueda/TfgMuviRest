package ucm.tfg.muvi.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.PostUpdate;
import facebook4j.PrivacyBuilder;
import facebook4j.PrivacyParameter;
import facebook4j.PrivacyType;
import facebook4j.auth.AccessToken;
import facebook4j.auth.OAuthAuthorization;
import facebook4j.auth.OAuthSupport;
import facebook4j.conf.Configuration;
import facebook4j.conf.ConfigurationBuilder;
import facebook4j.internal.org.json.JSONException;

@Path("/facebook")
public class ServicioFacebook {
	
	
	@POST
	public Response compartir(String mensaje) throws URISyntaxException, JSONException, FacebookException {
		
		Configuration configuration = createConfiguration();//
		Facebook facebook = new FacebookFactory().getInstance();
		AccessToken accessToken=null;//
		OAuthSupport oAuthSupport=new OAuthAuthorization (configuration);//
		accessToken = oAuthSupport.getOAuthAppAccessToken();//
		
		facebook.setOAuthAppId("605298096288420", "d0b92cf4ac1cc7a4d2194ffbb034dc06");
		//facebook.setOAuthAccessToken(new AccessToken("CAACEdEose0cBAPeZBEZBEmUzos0T1SEdVFq9K2o1iGtip4lloFdYeCyZA5Txn1DkBsonRqrNlzUTcWZAummVz73KNslvEx7yh1f9U3ZC8lSkWIpCstySVSlzLrqKmTzJGnnDnXE0aanKieezmTc0TFpL8zelY35PogZCXoHzbgw5m9MwyT7239lys7hnMYqonUVLjuOBJ51pGPV10ZCVXZCE", null));
		facebook.setOAuthAccessToken(accessToken);
		PrivacyParameter privacy = new PrivacyBuilder().setValue(PrivacyType.ALL_FRIENDS).build();
		PostUpdate postUpdate = null;
		try {
			postUpdate = new PostUpdate(new URL("http://facebook4j.org"))
			    .picture(new URL("http://ia.media-imdb.com/images/M/MV5BOTAzODEzNDAzMl5BMl5BanBnXkFtZTgwMDU1MTgzNzE@._V1_SX300.jpg"))
			    .name("Escaneado con Muvi")
			    .caption("Muvi")
			    .description("Muvi es lo mejor")
			    .privacy(privacy);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			String postId = facebook.postFeed(postUpdate);
		} catch (FacebookException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return Response.status(200).build() ;
	
	}

	private Configuration createConfiguration() {
		ConfigurationBuilder confBuilder = new ConfigurationBuilder();
		
		confBuilder.setOAuthAppId("605298096288420");
		confBuilder.setOAuthAppSecret("d0b92cf4ac1cc7a4d2194ffbb034dc06");
		Configuration configuration = confBuilder.build();
		return configuration;
	}
	

}
