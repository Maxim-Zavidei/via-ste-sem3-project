package Tier3;

import Shared.User;
import com.google.gson.Gson;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;

public class UserCommunicator {
    public UserCommunicator() {
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
            //e.printStackTrace();
        }
        return null;
    }

    public User addUser(RestTemplate restTemplate, String url, User user) {
        //Need validation if email exists (maybe email = better key than id?)
        try
        {
            ResponseEntity<String> responseEntityStr = restTemplate
                .postForEntity(url + "User/CreateUser", user, String.class);
            User u = gson.fromJson(responseEntityStr.getBody(), User.class);
            return u;
        }
        catch (Exception e)
        {

        }
        return null;
    }
}
