package Tier3;

import Shared.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.client.ClientConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;


/*public class Communicator{
  public static void main(String[] args) {
    ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);
		WebTarget target = client.target("https://localhost:5002/");
		GenericType<ArrayList<User>> carDTOArrayListType = new GenericType<ArrayList<User>>() {
		};
		ArrayList<User> allUsers = target.path("User").request().accept(MediaType.APPLICATION_JSON).get(carDTOArrayListType);
		System.out.println(allUsers);
  }
}*/
@SpringBootApplication
public class ServerCommunicator {
 /* private ObjectOutputStream outToServer;
  private ObjectInputStream inFromServer;*/

  //private static final Logger log = LoggerFactory.getLogger(Communicator.class);
  private static ServerCommunicator instance;
  private static final String url = "https://localhost:5002/";
  @Bean
public static RestTemplate restTemplate() 
                throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
    TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;

    SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom()
                    .loadTrustMaterial(null, acceptingTrustStrategy)
                    .build();

    SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);

    CloseableHttpClient httpClient = HttpClients.custom()
                    .setSSLSocketFactory(csf)
                    .build();

    HttpComponentsClientHttpRequestFactory requestFactory =
                    new HttpComponentsClientHttpRequestFactory();

    requestFactory.setHttpClient(httpClient);
    RestTemplate restTemplate = new RestTemplate(requestFactory);
    return restTemplate;
 }

  public synchronized static ServerCommunicator getInstance() throws Exception {
    if (instance == null) {
      instance = new ServerCommunicator();
    }
    return instance;
  }

  private ServerCommunicator() throws IOException {

    /*Socket socket = new Socket("localhost", 1098);

    outToServer = new ObjectOutputStream(socket.getOutputStream());
    inFromServer = new ObjectInputStream(socket.getInputStream());*/
   // HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
    //  public boolean verify(String hostname, SSLSession session) {
    //      return true;
    //  }
  //});
    //ClientConfig config = new ClientConfig();
		//Client client = ClientBuilder.newClient(config);
		//target = client.target("https://localhost:5002/");
		//carDTOArrayListType = new GenericType<ArrayList<User>>() {
		//};

  }

  public ArrayList<User> getUserFromDatabase() {
    try {
      /*Request request = new Request("getUser", username);
      outToServer.writeObject(request);
      UserDTO userDto = (UserDTO) inFromServer.readObject();
      User user = new User();
      if (userDto != null) {
        user.setUserId(userDto.getUserId());
        user.setPassword(userDto.getPassword());
        user.setUsername(userDto.getUsername());
        user.setSecurityLevel(userDto.getSecurityLevel());
        System.out.println(user.getUserId());
        return user;
      }*/

      ArrayList<User> allUsers = restTemplate().getForObject(url+"User/GetUsers", ArrayList.class);
		  System.out.println(allUsers);
      return allUsers;

    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
}