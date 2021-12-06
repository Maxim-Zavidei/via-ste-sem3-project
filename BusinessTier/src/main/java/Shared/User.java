package Shared;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class User implements Serializable
{
  @JsonProperty("id")
  private int id;

  @JsonProperty("username")
  private String username;

  @JsonProperty("password")
  private String password;

  @JsonProperty("email")
  private String email;
 // private int securityLevel;
  public User(){}
  public User(int userId, String username, String password, String  email)
  {
    setId(userId);
    setUsername(username);
    setPassword(password);
    //this.securityLevel = securityLevel;
    setEmail(email);
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

  public void setEmail(String email) throws IllegalArgumentException {
    if(email.isEmpty() || email == null) throw new IllegalArgumentException("Email cannot be empty");
    String[] emailParts = email.split("[@._]");
    if (emailParts.length < 1 || emailParts[0] == null || emailParts[0].isEmpty())
      throw new IllegalArgumentException("User part of email can't be empty, user@host.domain .");
    if (emailParts.length < 2 || emailParts[1] == null || emailParts[1].isEmpty())
      throw new IllegalArgumentException("Host part of email can't be empty, user@host.domain .");
    if (emailParts.length < 3 || emailParts[2] == null || emailParts[2].isEmpty())
      throw new IllegalArgumentException("Domain part of email can't be empty, user@host.domain .");
    if (!email.contains("@") || !email.substring(email.indexOf("@") + 1).contains("."))
        throw new IllegalArgumentException("Invalid email format. Email must respect user@host.domain format.");


      if (!emailParts[1].matches("[a-zA-Z0-9]*"))
        throw new IllegalArgumentException("Host part of email can not contain any symbols, user@host.domain .");
      if (!emailParts[2].matches("[a-zA-Z0-9]*"))
        throw new IllegalArgumentException("Domain part of email can not contain any symbols, user@host.domain .");
      if (emailParts[0].length() > 64)
        throw new IllegalArgumentException("User part of email can't be more then 64 chars, user@host.domain .");
      if (emailParts[1].length() > 63)
        throw new IllegalArgumentException("Host part of email can't be more then 63 chars, user@host.domain .");
      if (emailParts[2].length() > 63)
        throw new IllegalArgumentException("Domain part of email can't be more then 63 chars, user@host.domain .");
      char c = emailParts[1].toUpperCase().charAt(0);
      if (!('A' <= c && c <= 'Z'))
        throw new IllegalArgumentException(
            "The first char of the email host part has to be a letter, user@host.domain .");
      if (!emailParts[2].matches(".*[a-zA-Z]+.*"))
        throw new IllegalArgumentException("Domain part of email has to have at least one letter, user@host.domain .");
      this.email = email;
  }

  public void setId(int userId) throws IllegalArgumentException
  {
    if(userId < 0) throw new IllegalArgumentException("User ID cannot be less than 0!");
    this.id = userId;
  }

  public void setUsername(String username) throws IllegalArgumentException
  {
    if(username == null || username.isEmpty()) throw new IllegalArgumentException("Username cannot be empty");
    if (username.length() <= 2) throw new IllegalArgumentException("First name can't have less than 3 characters.");
    if (username.length() > 16) throw new IllegalArgumentException("First name can't have more than 16 characters.");
    this.username = username;
  }

  public void setPassword(String password) throws IllegalArgumentException
  {
    if (password == null || password.isEmpty() || password.isBlank()) throw new IllegalArgumentException("Password can't be empty.");
   // if (password.length() < 8) throw new IllegalArgumentException("Password must be at least 8 characters long.");
    //if (!password.matches(".*[A-Z]+.*")) throw new IllegalArgumentException("Password must have at least one uppercase letter.");
    //if (!password.matches(".*[0-9]+.*")) throw new IllegalArgumentException("Password must have at least one digit.");
    this.password = password;
  }

 /* public void setSecurityLevel(int securityLevel)
  {
    this.securityLevel = securityLevel;
  }*/

  public void runChecks() throws Exception{
    setId(this.id);
    setUsername(this.username);
    setPassword(this.password);
    setEmail(this.email);
  }
}
