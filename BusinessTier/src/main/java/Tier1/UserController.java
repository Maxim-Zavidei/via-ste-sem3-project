package Tier1;

import Logic.users.*;
import Shared.User;

import java.util.ArrayList;

import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


public class UserController extends Controller
{
  //private final UsersLogic logic;
  //private final UserModelAssembler assembler;
  private User cashedUser;

  public UserController()
  {
    cashedUser = new User();
   // this(null, null);
  }

  /*UserController(UsersLogic logic, UserModelAssembler assembler)
  {
    this.logic = logic;
    this.assembler = assembler;
  }*/
  public String logIn(User user)
  {
      ArrayList<User> users = super.communicator.getUsersFromDatabase();
      for(int i = 0; i < users.size(); i++){
          if(user.getUsername().equals(users.get(i).getUsername())){
              if(user.getPassword().equals(users.get(i).getPassword()))
              {
                  cashedUser = users.get(i);
                  System.out.println(users.get(i));
                  return "Successful";
              } else return "Incorrect password";
          }
      }
      return "User not found";
  }
  public User getCashedUser() {
      return cashedUser;
  }

}