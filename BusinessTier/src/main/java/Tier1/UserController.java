package Tier1;

import Shared.User;

import java.util.ArrayList;

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
  public String logIn(User user) throws Exception
  {
    ArrayList<User> users = super.communicator.fetchUsersFromDatabase();
    for (User value : users)
    {
      if (user.getUsername().equals(value.getUsername()))
      {
        if (user.getPassword().equals(value.getPassword()))
        {
          cashedUser = value;
          System.out.println(value.getUsername() + ", " + value.getPassword());
          return "Successful";
        }
        else
          throw new Exception("Incorrect password");
      }
    }
    throw new Exception("User not found");
  }
  public ArrayList<User> fetchUsersFromDatabase() throws Exception
  {
      return super.communicator.fetchUsersFromDatabase();

  }
  public ArrayList<User> fetchUsersSharingFromDatabase() throws Exception
  {
    return super.communicator.fetchUsersSharingFromDatabase();
  }

  public String addUser(User user) throws Exception
  {
    String isExisting = chechIfExist(user.getEmail(), user.getUsername());
    String valiedEmail = validateEmail(user.getEmail());
    cashedUser = super.communicator.addUser(user);
    return valiedEmail;
  }
  public void deleteUser(int userId) throws Exception
  {
    super.communicator.deleteUser(userId);
  }

  public void changeSharingStatus(int userId) throws Exception
  {
    super.communicator.changeSharingStatus(userId);
  }

  public boolean getSharingStatus(int userId) throws Exception{
    return super.communicator.getSharingStatus(userId);
  }

  public User getCashedUser() {
    return cashedUser;
  }

  /**
   * checks for User - if email is valid and if it already exists.
   * @return Successful if the email is valid and if the user does not exist.
   */
  
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

  private String chechIfExist(String email, String username) throws Exception {
    ArrayList<User> users = communicator.fetchUsersFromDatabase();
    for(User user: users)
    {
      if(user.getEmail().equals(email)) throw new Exception("Email is already in use");
      if(user.getUsername().equals(username)) throw new Exception("Username is already in use");
    }
    return "Successful";
  }

}