package Logic.users;
import Tier1.ClientCommunicator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class UsersLogic
{
  
  public static void main(String[] args) throws Exception {
    SpringApplication.run(UsersLogic.class, args);
  }
  @Bean
  ClientCommunicator server()
  {
    return new ClientCommunicator(8080);
  }
}
