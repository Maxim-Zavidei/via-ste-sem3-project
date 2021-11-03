package Logic.users;
import Tier1.ClientCommunicator;
import Tier1.UserNotFoundException;
import Tier3.ServerCommunicator;

import Shared.User;

public class UsersLogic
{
  public static void main(String[] args) throws Exception {
    ClientCommunicator clientCommunicator = new ClientCommunicator(8080);
  }
}
