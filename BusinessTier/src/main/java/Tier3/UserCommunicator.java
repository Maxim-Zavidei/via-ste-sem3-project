package Tier3;

import Shared.User;
import com.google.gson.Gson;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;

public class UserCommunicator {
    private Gson gson;

    public UserCommunicator() {
        gson = new Gson();

    }

    public ArrayList<User> fetchUsersFromDatabase(RestTemplate restTemplate, String url) throws Exception {
        try {

            ResponseEntity<User[]> responseEntity = restTemplate.getForEntity(url + "User/All", User[].class);
            User[] users = responseEntity.getBody();
            /*
             * MediaType contentType = responseEntity.getHeaders().getContentType();
             * HttpStatus statusCode = responseEntity.getStatusCode();
             */
            return new ArrayList<>(Arrays.asList(users));
        } catch (Exception e) {
            throw new Exception("Could not fetch users from Database");
        }
    }

    public ArrayList<User> fetchUsersSharingFromDatabase(RestTemplate restTemplate, String url) throws Exception {
        try {
            ResponseEntity<User[]> responseEntity = restTemplate.getForEntity(url + "User/AllSharing", User[].class);
            User[] tempUsers = responseEntity.getBody();
            assert tempUsers != null;
            return new ArrayList<>(Arrays.asList(tempUsers));
        } catch (Exception e) {
            throw new Exception("Could not fetch sharing users from Database");
        }
    }

    public User addUser(RestTemplate restTemplate, String url, User user) throws Exception {
        try {
            ResponseEntity<String> responseEntityStr = restTemplate
                    .postForEntity(url + "User", user, String.class);
            User u = gson.fromJson(responseEntityStr.getBody(), User.class);
            return u;
        } catch (Exception e) {
            throw new Exception("Could not create user:{ " + user.getId() + ", " + user.getUsername() + " } in database");
        }
    }

    public void deleteUser(RestTemplate restTemplate, String url, int userId) throws Exception {
        try {
            restTemplate.delete(url + "User/" + userId);

        } catch (Exception e) {
            throw new Exception("User id " + userId + " not found in database");
        }
    }

    public void changeSharingStatus(RestTemplate restTemplate, String url, int userId) throws Exception {
        try {
            restTemplate.patchForObject(url + "User/" + userId + "/SharingStatus", userId, Integer.class);
        } catch (Exception e) {
            throw new Exception("User id " + userId + " not found in database");
        }
    }

    public boolean getSharingStatus(RestTemplate restTemplate, String url, int userId) throws Exception {
        try {
            ResponseEntity<Boolean> responseEntity = restTemplate.getForEntity(url + "User/" + userId + "/SharingStatus", Boolean.class);
            Boolean sharingStatus = responseEntity.getBody();
            return sharingStatus;

        } catch (Exception e) {
            throw new Exception("Could not get sharing status for id " + userId + " in database");
        }
    }


}
