package Logic.users;
import Tier1.ClientCommunicator;
import Tier1.UserNotFoundException;
import Tier3.ServerCommunicator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import Shared.User;

@SpringBootApplication
public class UsersLogic
{
  
  public static void main(String[] args) throws Exception {
    //ClientCommunicator clientCommunicator = new ClientCommunicator(8080);
    SpringApplication.run(UsersLogic.class, args);
  }
  @Bean
  ClientCommunicator server()
  {
    return new ClientCommunicator(8080);
  }
}
