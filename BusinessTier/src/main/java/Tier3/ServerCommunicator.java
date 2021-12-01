package Tier3;

import Shared.*;

import java.io.IOException;
import java.util.ArrayList;

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

@SpringBootApplication
public class ServerCommunicator {

  private static ServerCommunicator instance;
  private static UserCommunicator userCommunicator;
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
    this.userCommunicator = new UserCommunicator();
    restTemplate = restTemplate();

  }

  public ArrayList<User> getUsersFromDatabase() {
    return userCommunicator.getUsersFromDatabase(restTemplate, url);
  }

  public User addUser(User user) {
    return userCommunicator.addUser(restTemplate, url, user);
  }

  public ArrayList<Event> getUserEventFromDatabase(int id) {
    return userCommunicator.getUserEventsFromDatabase(restTemplate, url, id);
  }
  public void deleteUser(int userId) throws IllegalArgumentException
  {
    userCommunicator.deleteUser(restTemplate, url, userId);
  }
}