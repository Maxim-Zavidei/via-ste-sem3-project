package Shared;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class User
{
  private int userId;
  private String username;
  private String password;
  private String email;
 // private int securityLevel;
  public User(){}
  public User(int userId, String username, String password, String  email)
  {
    this.userId = userId;
    this.username = username;
    this.password = password;
    //this.securityLevel = securityLevel;
    this.email = email;
  }

  public String getEmail() {
      return email;
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

 /* public int getSecurityLevel()
  {
    return securityLevel;
  }*/

  public void setEmail(String email) {
      this.email = email;
  }

  public void setUserId(int userId)
  {
    this.userId = userId;
  }

  public void setUsername(String username)
  {
    this.username = username;
  }

  public void setPassword(String password)
  {
    this.password = password;
  }

 /* public void setSecurityLevel(int securityLevel)
  {
    this.securityLevel = securityLevel;
  }*/
}
