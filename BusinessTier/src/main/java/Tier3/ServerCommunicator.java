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
  private static final String url = "https://localhost:5002/";
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

  public ArrayList<User> fetchUsersFromDatabase() throws Exception
  {
    return userCommunicator.fetchUsersFromDatabase(restTemplate, url);
  }
  public ArrayList<User> fetchUsersSharingFromDatabase() throws Exception
  {
    return userCommunicator.fetchUsersSharingFromDatabase(restTemplate, url);
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

  public boolean getSharingStatus(int userId) throws Exception{
   return userCommunicator.getSharingStatus(restTemplate, url, userId);
  }



  // Events
  // Methods
  // Down

  public Event addEvent(int userId, Event event) throws Exception
  {
    return eventCommunicator.addEvent(restTemplate, url, event, userId);
  }

  public Event addSharedEvent(int userId, Event event, int otherUserId) throws Exception
  {
    return eventCommunicator.addSharedEvent(restTemplate, url, event, userId,
        otherUserId);
  }

  public ArrayList<Event> FetchUserEventFromDatabase(int userId) throws Exception
  {
    return eventCommunicator.fetchUserEventsFromDatabase(restTemplate, url,
        userId);
  }
  public Event editEvent(int userId, Event evt) throws Exception
  {
    return eventCommunicator.editEvent(restTemplate, url, evt, userId);
  }
  public void removeEvent(int evtId) throws Exception
  {
    eventCommunicator.removeEvent(restTemplate, url, evtId);
  }
}