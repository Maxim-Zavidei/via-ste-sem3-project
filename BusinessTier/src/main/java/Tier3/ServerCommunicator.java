package Tier3;

import Shared.Event;
import Shared.User;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;

@SpringBootApplication
public class ServerCommunicator {

  private static ServerCommunicator instance;
  private static UserCommunicator userCommunicator;
  private static EventCommunicator eventCommunicator;
  private RestTemplate restTemplate;
  private static final String url = "https://localhost:5000/";

  /**
   * disregards the CA in order to connect to localhost of c# Web API
   */
  @Bean
  public static RestTemplate restTemplate() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
    TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;

    SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy)
        .build();

    SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);

    CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();

    HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();

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

  private ServerCommunicator() throws IOException, KeyManagementException, KeyStoreException, NoSuchAlgorithmException {
    userCommunicator = new UserCommunicator();
    eventCommunicator = new EventCommunicator();
    restTemplate = restTemplate();

  }

  // User
  // Methods
  // Down

  public ArrayList<User> FetchUsersFromDatabase() throws Exception
  {
    return userCommunicator.FetchUsersFromDatabase(restTemplate, url);
  }
  public ArrayList<User> FetchUsersSharingFromDatabase() throws Exception
  {
    return userCommunicator.FetchUsersSharingFromDatabase(restTemplate, url);
  }

  public User addUser(User user) throws Exception
  {
    return userCommunicator.addUser(restTemplate, url, user);
  }

  public void deleteUser(int userId) throws Exception
  {
    userCommunicator.deleteUser(restTemplate, url, userId);
  }

  public void changeSharingStatus(int userId) throws Exception{
    userCommunicator.changeSharingStatus(restTemplate, url, userId);
  }



  // Events
  // Methods
  // Down

  public Event addEvent(int id, Event event) throws Exception
  {
    return eventCommunicator.addEvent(restTemplate, url, event, id);
  }

  public Event addSharedEvent(int id, Event event, int ouId)
  {
    return eventCommunicator.addSharedEvent(restTemplate, url, event,id, ouId);
  }

  public ArrayList<Event> FetchUserEventFromDatabase(int id) throws Exception
  {
    return eventCommunicator.fetchUserEventsFromDatabase(restTemplate, url, id);
  }
}