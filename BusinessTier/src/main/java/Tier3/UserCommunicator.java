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

    private Gson gson;
/**
 * User
 * @param restTemplate
 * @param url
 * @return
 */
    public ArrayList<User> FetchUsersFromDatabase(RestTemplate restTemplate, String url) throws Exception
    {
        try {

            ResponseEntity<User[]> responseEntity = restTemplate.getForEntity(url + "User/GetUsers", User[].class);
            User[] users = responseEntity.getBody();
            /*
             * MediaType contentType = responseEntity.getHeaders().getContentType();
             * HttpStatus statusCode = responseEntity.getStatusCode();
             */
            return new ArrayList<>(Arrays.asList(users));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Exception("Could not fetch users from DB");
        }
    }
    public ArrayList<User> FetchUsersSharingFromDatabase(RestTemplate restTemplate, String url) throws Exception
    {
        try
        {
            ResponseEntity<User[]> responseEntity = restTemplate.getForEntity(url + "User/GetUsers", User[].class);
            User[] tempUsers = responseEntity.getBody();
            assert tempUsers != null;
            return new ArrayList<>(Arrays.asList(tempUsers));
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            throw new Exception("Could not fetch sharing users from DB");
        }
    }

    public User addUser(RestTemplate restTemplate, String url, User user) throws Exception
    {
        try
        {
            ResponseEntity<String> responseEntityStr = restTemplate
                .postForEntity(url + "User/CreateUser", user, String.class);
            User u = gson.fromJson(responseEntityStr.getBody(), User.class);
            return u;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            throw new Exception("Could not create user:{ " + user.getId() + ", " + user.getUsername() + " } in database");
        }
    }

    public void deleteUser(RestTemplate restTemplate, String url, int userId) throws Exception
    {
        try
        {
            restTemplate.delete(url+"/"+userId);

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            throw new Exception("User id " + userId + "not found in database");
        }
    }
    public void changeSharingStatus(RestTemplate restTemplate, String url, int userId) throws Exception
    {
        try{
            restTemplate.patchForObject(url, userId+"/ChangeSharingStatus", String.class);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            throw new Exception("User id " + userId + "not found in database");
        }
    }


}
