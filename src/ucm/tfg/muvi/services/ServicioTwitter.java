package ucm.tfg.muvi.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import twitter4j.JSONException;
import twitter4j.JSONObject;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

@Path("/twitter")
public class ServicioTwitter {
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response compartir(String mensaje) throws URISyntaxException, JSONException {
		JSONObject json_msg = new JSONObject(mensaje);
		String tweet = json_msg.getString("mensaje");
		
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true);
		cb.setOAuthConsumerKey("YzKOroeUWJImOyJ7VATHqSe4D");
		cb.setOAuthConsumerSecret("nWfkh8orOmvMilgUVMJOFeW1vG3DpJW4rtjxlYfpIoNjQR6Nci");
		
		TwitterFactory tf = new TwitterFactory(cb.build());
		Twitter twitter = tf.getInstance();
		//Twitter twitter = TwitterFactory.getSingleton();
		//twitter.setOAuthConsumer("YzKOroeUWJImOyJ7VATHqSe4D", "nWfkh8orOmvMilgUVMJOFeW1vG3DpJW4rtjxlYfpIoNjQR6Nci");
		
		try {
			RequestToken requestToken = twitter.getOAuthRequestToken();
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			AccessToken accessToken = null;
			while (null == accessToken) {
				System.out.println("Open the following URL and grant access to your account:");
			    System.out.println(requestToken.getAuthorizationURL());
				System.out.print("Enter the PIN(if aviailable) or just hit enter. [PIN]:");
				//Desktop desktop = Desktop.getDesktop();
				//desktop.browse(new URI(requestToken.getAuthorizationURL()));
			    
				try {
					String pin = br.readLine();
				
			    	if (pin.length() > 0) {
			    		accessToken = twitter.getOAuthAccessToken(requestToken, pin);
			        } else {
			        	accessToken = twitter.getOAuthAccessToken();
			        }
			    } catch (TwitterException e) {
			    	if (401 == e.getStatusCode()) {
			    		System.out.println("Unable to get the access token");
			    	} else {
			    		e.printStackTrace();
			        }
			    }
				
			    //storeAccessToken(twitter.verifyCredentials().getId(), accessToken);
			    Status status = twitter.updateStatus(tweet);
			    //System.out.println("Successfully updated the status to [" + status.getText() + "].");
			}
		} catch (TwitterException e) {
			e.printStackTrace();
			System.out.println("Failed to get timeline: " + e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return Response.status(200).build();
	}
	
	/*private static void storeAccessToken(int useId, AccessToken accessToken){
	    //store accessToken.getToken()
	    //store accessToken.getTokenSecret()
	}*/
}
