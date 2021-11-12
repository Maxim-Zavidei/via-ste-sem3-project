package Tier1;

import Logic.users.*;
import Shared.User;

import java.util.ArrayList;

import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

public class UserController extends Controller {
  // private final UsersLogic logic;
  // private final UserModelAssembler assembler;
  private User cashedUser;

  public UserController() {
    cashedUser = new User();
    // this(null, null);
  }

  /*
   * UserController(UsersLogic logic, UserModelAssembler assembler) { this.logic =
   * logic; this.assembler = assembler; }
   */
  public String logIn(User user) {
    ArrayList<User> users = super.communicator.getUsersFromDatabase();
    for (int i = 0; i < users.size(); i++) {
      if (user.getUsername().equals(users.get(i).getUsername())) {
        if (user.getPassword().equals(users.get(i).getPassword())) {
          cashedUser = users.get(i);
          System.out.println(users.get(i));
          return "Successful";
        } else
          return "Incorrect password";
      }
    }
    return "User not found";
  }

  public String addUser(User user) throws Exception
  {
    String toReturn = validateEmail(user.getEmail());
    
    cashedUser = communicator.addUser(user);
    return toReturn;
  }

  public User getCashedUser() {
    return cashedUser;
  }

  private String validateEmail(String email) {
    if (!email.contains("@") || !email.substring(email.indexOf("@") + 1).contains("."))
      throw new IllegalArgumentException("Invalid email format. Email must respect user@host.domain format.");
    String[] emailParts = email.split("[@._]");
    if (emailParts.length < 1 || emailParts[0] == null || emailParts[0].isEmpty())
      throw new IllegalArgumentException("User part of email can't be empty, user@host.domain .");
    if (emailParts.length < 2 || emailParts[1] == null || emailParts[1].isEmpty())
      throw new IllegalArgumentException("Host part of email can't be empty, user@host.domain .");
    if (emailParts.length < 3 || emailParts[2] == null || emailParts[2].isEmpty())
      throw new IllegalArgumentException("Domain part of email can't be empty, user@host.domain .");
    if (!emailParts[1].matches("[a-zA-Z0-9]*"))
      throw new IllegalArgumentException("Host part of email can not contain any symbols, user@host.domain .");
    if (!emailParts[2].matches("[a-zA-Z0-9]*"))
      throw new IllegalArgumentException("Domain part of email can not contain any symbols, user@host.domain .");
    if (emailParts[0].length() > 64)
      throw new IllegalArgumentException("User part of email can't be more then 64 chars, user@host.domain .");
    if (emailParts[1].length() > 63)
      throw new IllegalArgumentException("Host part of email can't be more then 63 chars, user@host.domain .");
    if (emailParts[2].length() > 63)
      throw new IllegalArgumentException("Domain part of email can't be more then 63 chars, user@host.domain .");
    char c = emailParts[1].toUpperCase().charAt(0);
    if (!('A' <= c && c <= 'Z'))
      throw new IllegalArgumentException(
          "The first char of the email host part has to be a letter, user@host.domain .");
    if (!emailParts[2].matches(".*[a-zA-Z]+.*"))
      throw new IllegalArgumentException("Domain part of email has to have at least one letter, user@host.domain .");
    return "Successful";
  }

}