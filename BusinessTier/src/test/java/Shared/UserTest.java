package Shared;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserTest
{

  User user;

  @BeforeEach
  void setUp()
  {
    user =new User();
  }

  @AfterEach
  void tearDown()
  {

  }

  @Test
  void setNull()
  {
    /** ID */
    assertThrows(IllegalArgumentException.class, () -> user.setId(-1));

    /** Email */
    assertThrows(IllegalArgumentException.class, () -> user.setEmail(""));
    assertThrows(IllegalArgumentException.class, () -> user.setEmail(" "));
   // assertThrows(IllegalArgumentException.class, () -> user.setEmail(null));

    /** Username */
    assertThrows(IllegalArgumentException.class, () -> user.setUsername(""));
    assertThrows(IllegalArgumentException.class, () -> user.setUsername(" "));
    assertThrows(IllegalArgumentException.class, () -> user.setUsername(null));


    /** Password */
    assertThrows(IllegalArgumentException.class, () -> user.setPassword(""));
    assertThrows(IllegalArgumentException.class, () -> user.setPassword(" "));
    assertThrows(IllegalArgumentException.class, () -> user.setPassword(null));
  }

  @Test
  void setOne()
  {
    /**ID*/
    // No cases? //

    /**Email*/
    assertThrows(IllegalArgumentException.class, () -> user.setEmail("b"));
    assertThrows(IllegalArgumentException.class, () -> user.setEmail("bob"));

    /**Password*/
    // No cases ? //

    /**Username*/
    assertThrows(IllegalArgumentException.class, () -> user.setUsername("b"));
    assertThrows(IllegalArgumentException.class, () -> user.setUsername("bo"));
  }
  @Test
  void setMany()
  {
    /**ID*/
    // No cases? //


    /**Email*/
    assertThrows(IllegalArgumentException.class, () -> user.setEmail("bobgmail.com"));
    assertThrows(IllegalArgumentException.class, () -> user.setEmail("bob@gmail"));
    user.setEmail("bob@gmail.com");
    assertEquals("bob@gmail.com", user.getEmail());
    user.setEmail("bob@abv.com");
    assertEquals("bob@abv.com", user.getEmail());
    user.setEmail("bob@abv.bg");
    assertEquals("bob@abv.bg", user.getEmail());
    user.setEmail("bob@via.dk");
    assertEquals("bob@via.dk", user.getEmail());

    /**Password*/
    //assertThrows(IllegalArgumentException.class, () -> user.setPassword("Mdgkjgje"));
    //assertThrows(IllegalArgumentException.class, () -> user.setPassword("Asd123"));
    user.setPassword("Asd123456");
    assertEquals("Asd123456", user.getPassword());

    /** USername */
    assertThrows(IllegalArgumentException.class, () -> user.setUsername("thisUsernameHasMoreThan16CharactersAndC"));
    ;
  }
  @Test
  void setBoundary()
  {
    /** No cases ? */
  }

  @Test
  void setExceptions() {
    /**Email*/
    assertThrows(IllegalArgumentException.class, () -> user.setEmail("bobgmail.com"));
    assertThrows(IllegalArgumentException.class, () -> user.setEmail("bob@gmail"));
    assertThrows(IllegalArgumentException.class, () -> user.setEmail("bob.gmail@com"));
    assertThrows(IllegalArgumentException.class, () -> user.setEmail("bob@gmail."));
    assertThrows(IllegalArgumentException.class, () -> user.setEmail("@@.."));

    /**Password*/
    //assertThrows(IllegalArgumentException.class, () -> user.setPassword("Mdgkjgje"));
    //assertThrows(IllegalArgumentException.class, () -> user.setPassword("Asd123"));
    //assertThrows(IllegalArgumentException.class, () -> user.setPassword("12343543515435436"));
    //assertThrows(IllegalArgumentException.class, () -> user.setPassword("sdfghjkdurydtzsrertyjy213"));

    /** Username */
    assertThrows(IllegalArgumentException.class, () -> user.setUsername("thishasexactly15"));
  }
}
