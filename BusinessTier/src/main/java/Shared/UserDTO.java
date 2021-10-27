package Shared;

import java.io.Serializable;

public class UserDTO implements Serializable
{
  private static final long serialVersionID = 1L;
  private int userId;
  private String username;
  private String password;
  private int securityLevel;
  public UserDTO(int userId, String username, String password, int securityLevel)
  {
    this.userId = userId;
    this.username = username;
    this.password = password;
    this.securityLevel = securityLevel;
  }
  public UserDTO(User user)
  {
    this(user.getUserId(), user.getUsername(), user.getPassword(),
        user.getSecurityLevel());
  }

  public int getUserId()
  {
    return userId;
  }

  public String getUsername()
  {
    return username;
  }

  public String getPassword()
  {
    return password;
  }

  public int getSecurityLevel()
  {
    return securityLevel;
  }
}
