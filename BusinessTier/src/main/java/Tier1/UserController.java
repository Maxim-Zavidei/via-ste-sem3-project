package Tier1;

import Logic.UsersLogic;
import Shared.User;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController
{
  private final UsersLogic logic;
  private final UserModelAssembler assembler;

  UserController(UsersLogic logic, UserModelAssembler assembler)
  {
    this.logic = logic;
    this.assembler = assembler;
  }

  @PostMapping("/login")
  EntityModel<User> one(@RequestBody User user)
  {
    User loggingUser = logic.login(user.getUsername(),user.getPassword());
    return assembler.toModel(loggingUser);
  }


}