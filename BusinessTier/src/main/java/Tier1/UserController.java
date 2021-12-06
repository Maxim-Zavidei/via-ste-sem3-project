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
    user.runChecks();
    cashedUser = super.communicator.addUser(user);
    return user.getEmail();
  }
  public void deleteUser(int userId) throws Exception
  {
    super.communicator.deleteUser(userId);
  }

  public void changeSharingStatus(int userId) throws Exception
  {
    super.communicator.changeSharingStatus(userId);
  }

  public User getCashedUser() {
    return cashedUser;
  }

  /**
   * checks for User - if email is valid and if it already exists.
   * @return Successful if the email is valid and if the user does not exist.
   */


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