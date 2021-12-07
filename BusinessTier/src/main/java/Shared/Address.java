package Shared;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class Address implements Serializable
{
  @JsonProperty("id")
  private int id;
  @JsonProperty("streetName")
  private String streetName;

  @JsonProperty("number")
  private String number;

  @JsonProperty("city")
  private String city;

  @JsonProperty("country")
  private String country;

  public Address(){}

  public void setCity(String city) throws IllegalArgumentException {
   /* if(city == null || city.isBlank() || city.isEmpty()) throw new IllegalArgumentException("Address city cannot be null");
    if(city.length() <= 2) throw new IllegalArgumentException("Address city cannot have less then 3 characters.");
    if (city.length() > 16) throw new IllegalArgumentException("Address city cannot have more than 16 characters.");*/
      this.city = city;
  }
  public void setCountry(String country) throws IllegalArgumentException {
   /* if(country == null || country.isBlank() || country.isEmpty()) throw new IllegalArgumentException("Address country cannot be null");
    if(country.length() <= 2) throw new IllegalArgumentException("Address country cannot have less then 3 characters.");
    if (country.length() > 32) throw new IllegalArgumentException("Address country cannot have more than 32 characters.");*/
      this.country = country;
  }
  public void setId(int id) throws IllegalArgumentException {
    if(id < 0) throw new IllegalArgumentException("Address ID cannot be less than 0!");
      this.id = id;
  }
  public void setNumber(String number) throws IllegalArgumentException {
   /* if(number == null || number.isBlank() || number.isEmpty()) throw new IllegalArgumentException("Address number cannot be null");
    if (number.length() > 3) throw new IllegalArgumentException("Address number cannot have more than 3 characters.");*/
      this.number = number;
  }public void setStreetName(String streetName) throws IllegalArgumentException {
  /*if(streetName == null || streetName.isBlank() || streetName.isEmpty()) throw new IllegalArgumentException("Address street name cannot be null");
  if(streetName.length() <= 2) throw new IllegalArgumentException("Address street name cannot have less then 3 characters.");
  if (streetName.length() > 32) throw new IllegalArgumentException("Address street name cannot have more than 32 characters.");*/
      this.streetName = streetName;
  }

  public int getId()
  {
    return id;
  }

  public String getCountry()
  {
    return country;
  }

  public String getCity()
  {
    return city;
  }

  public String getStreetName()
  {
    return streetName;
  }

  public String getNumber()
  {
    return number;
  }

  public void runChecks() throws Exception
  {
    setId(this.id);
    setCity(this.city);
    setCountry(this.country);
    setNumber(this.number);
    setStreetName(this.streetName);
  }
}
