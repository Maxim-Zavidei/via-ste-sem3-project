package Shared;

import java.io.Serializable;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class User implements Serializable
{
  @JsonProperty("id")
  private int id;
  private String username;
  private String password;
  private String email;
 // private int securityLevel;
  public User(){}
  public User(int userId, String username, String password, String  email)
  {
    this.id = userId;
    this.username = username;
    this.password = password;
    //this.securityLevel = securityLevel;
    this.email = email;
  }

  public String getEmail() {
      return email;
  }
  public int getId()
  {
    return id;
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

  public void setId(int userId)
  {
    this.id = userId;
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
