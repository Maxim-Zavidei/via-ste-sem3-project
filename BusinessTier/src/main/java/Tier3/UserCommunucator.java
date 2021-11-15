package Tier3;

import java.util.ArrayList;
import java.util.Arrays;

import com.google.gson.Gson;

import Shared.User;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class UserCommunucator {
    public UserCommunucator() {
        gson = new Gson();

    }

    Gson gson;

    public ArrayList<User> getUsersFromDatabase(RestTemplate restTemplate, String url) {
        try {

            ResponseEntity<User[]> responseEntity = restTemplate.getForEntity(url + "User/GetUsers", User[].class);
            User[] users = responseEntity.getBody();
            /*
             * MediaType contentType = responseEntity.getHeaders().getContentType();
             * HttpStatus statusCode = responseEntity.getStatusCode();
             */
            return new ArrayList<>(Arrays.asList(users));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public User addUser(RestTemplate restTemplate, String url, User user) {
        ResponseEntity<String> responseEntityStr = restTemplate.postForEntity(url+"User/CreateUser", user, String.class);
        User u = gson.fromJson(responseEntityStr.getBody(), User.class);
        return u;
    }
}