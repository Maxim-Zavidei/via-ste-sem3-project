package Shared;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AddressTest
{

  Address address;

  @BeforeEach void setUp()
  {
    address = new Address();
  }

  @AfterEach void tearUp()
  {

  }

  @Test
  void setNull()
  {
    /** ID */
    assertThrows(IllegalArgumentException.class, () -> address.setId(-1));
    
    /** Country */
    assertThrows(IllegalArgumentException.class, () -> address.setCountry(""));
    assertThrows(IllegalArgumentException.class, () -> address.setCountry(" "));
    assertThrows(IllegalArgumentException.class, () -> address.setCountry(null));
    
    /** City */
    assertThrows(IllegalArgumentException.class, () -> address.setCity(""));
    assertThrows(IllegalArgumentException.class, () -> address.setCity(" "));
    assertThrows(IllegalArgumentException.class, () -> address.setCity(null));
    
    /** Street name */
    assertThrows(IllegalArgumentException.class, () -> address.setStreetName(""));
    assertThrows(IllegalArgumentException.class, () -> address.setStreetName(" "));
    assertThrows(IllegalArgumentException.class, () -> address.setStreetName(null));
    
    /** Street number */
    assertThrows(IllegalArgumentException.class, () -> address.setNumber(""));
    assertThrows(IllegalArgumentException.class, () -> address.setNumber(" "));
    assertThrows(IllegalArgumentException.class, () -> address.setNumber(null));
  }

  @Test
  void setOne()
  {
    /** ID */
    address.setId(2);
    assertEquals(address.getId(), 2);

    /** Country */
    assertThrows(IllegalArgumentException.class, () -> address.setCountry("A"));
    assertThrows(IllegalArgumentException.class, () -> address.setCountry("e"));

    /** City */
    assertThrows(IllegalArgumentException.class, () -> address.setCity("B"));
    assertThrows(IllegalArgumentException.class, () -> address.setCity("d"));

    /** Street name */
    assertThrows(IllegalArgumentException.class, () -> address.setStreetName("J"));
    assertThrows(IllegalArgumentException.class, () -> address.setStreetName("k"));

    /** Street number */
    assertThrows(IllegalArgumentException.class, () -> address.setNumber("1"));
    assertThrows(IllegalArgumentException.class, () -> address.setNumber("0"));
  }
  @Test
  void setMany()
  {
    /** ID */
    address.setId(23);
    assertEquals(address.getId(), 23);

    /** Country */
    address.setCountry("Austria");
    assertEquals(address.getCountry(), "Austria");
    address.setCountry("Japan");
    assertEquals(address.getCountry(), "Japan");

    assertThrows(IllegalArgumentException.class, () -> address.setCountry("Co"));
    assertThrows(IllegalArgumentException.class, () -> address.setCountry("Country that supposedly has more character than it should have"));


    /** City */
    address.setCity("Bangladesh");
    assertEquals(address.getCity(), "Bangladesh");
    address.setCity("Tokyo");
    assertEquals(address.getCity(), "Tokyo");

    assertThrows(IllegalArgumentException.class, () -> address.setCity("Ci"));
    assertThrows(IllegalArgumentException.class, () -> address.setCity("City that supposedly has more characters than it should have"));

    /** Street name */
    address.setStreetName("Horsensvej");
    assertEquals(address.getStreetName(), "Horsensvej");
    address.setStreetName("Jamaican St.");
    assertEquals(address.getStreetName(), "Jamaican St.");

    assertThrows(IllegalArgumentException.class, () -> address.setStreetName("St"));
    assertThrows(IllegalArgumentException.class, () -> address.setStreetName("Streeet that supposedly has a lot more characters than it should have"));


    /** Street number */
    address.setNumber("28");
    assertEquals(address.getNumber(), "28");
    address.setNumber("13");
    assertEquals(address.getNumber(), "13");
    address.setNumber("410");
    assertEquals(address.getNumber(), "410");

    assertThrows(IllegalArgumentException.class, () -> address.setNumber("4199"));

  }
  @Test
  void setBoundary()
  {
    /** ID */
    //No test cases//

    /** Country */
    try
    {
      address.setCountry("Exactly32 CharactersThisSupposed");
    }
    catch (Exception e)
    {
      fail(e.getMessage());
    }

    /** City */
    try
    {
      address.setCity("Exactly 16 chars");
    }
    catch (Exception e)
    {
      fail(e.getMessage());
    }

    /** Street name */
    try
    {
      address.setStreetName("Exactly32 CharactersThisSupposed");
    }
    catch (Exception e)
    {
      fail(e.getMessage());
    }

    /** Street number */
    try
    {
      address.setNumber("999");
    }
    catch (Exception e)
    {
      fail(e.getMessage());
    }

  }

  @Test
  void setExceptions()
  {
    // No test cases //
  }
}
