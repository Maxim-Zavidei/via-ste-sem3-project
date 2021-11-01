package Logic.users;
import Tier1.ClientCommunicator;
import Tier1.UserNotFoundException;
import Tier3.ServerCommunicator;

import Shared.User;

public class UsersLogic
{
  public static void main(String[] args) throws Exception {
    ClientCommunicator clientCommunicator = new ClientCommunicator(1234);
  }
  //Communicator communicator= Communicator.getInstance();
  /*public UsersLogic() throws Exception
  {
  }

  public User login(String username, String password)
  {
    System.out.println("log in");
    User user;
    try{
      user = getUserFromDatabase(username);
    }catch (Exception e)
    {
      System.out.println(e);
      throw new RuntimeException("Connection failed");
    }

    if(user==null)
    {
      throw new UserNotFoundException("Username not found");
    }
    else if(user.getPassword().equals(password))
    {
      return user;
    }
    throw new UserNotFoundException("Wrong password");

  }
  public User getUserFromDatabase(String username)
  {
    return communicator.getUserFromDatabase(username);
  }*/

}
