package Shared;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class EventTest
{
  Event event;

  @BeforeEach
  void setUp()
  {
    event = new Event();
  }

  @AfterEach
  void tearDown()
  {

  }

  @Test
  void setNull()
  {
    /** ID */
    assertThrows(IllegalArgumentException.class, () -> event.setId(-1));
    
    /** Title */
    assertThrows(IllegalArgumentException.class, () -> event.setTitle(""));
    assertThrows(IllegalArgumentException.class, () -> event.setTitle(" "));
    assertThrows(IllegalArgumentException.class, () -> event.setTitle(null));
    
    /** Description */
    assertThrows(IllegalArgumentException.class, () -> event.setDescription(""));
    assertThrows(IllegalArgumentException.class, () -> event.setDescription(" "));
    assertThrows(IllegalArgumentException.class, () -> event.setDescription(null));
    
    
    /** Start time */
    assertThrows(IllegalArgumentException.class, () -> event.setStartTime(""));
    assertThrows(IllegalArgumentException.class, () -> event.setStartTime(" "));
    assertThrows(IllegalArgumentException.class, () -> event.setStartTime(null));
    
    /** End Time */
    assertThrows(IllegalArgumentException.class, () -> event.setEndTime(""));
    assertThrows(IllegalArgumentException.class, () -> event.setEndTime(" "));
    assertThrows(IllegalArgumentException.class, () -> event.setEndTime(null));
    
    /** Address */
    // Already tested in Class AddressTest //
  }

  @Test
  void setOne()
  {
    /** ID */
    // Nothing to be tested here //

    /** Title */
    assertThrows(IllegalArgumentException.class, () -> event.setTitle("T"));
    assertThrows(IllegalArgumentException.class, () -> event.setTitle("t"));
    assertThrows(IllegalArgumentException.class, () -> event.setTitle("@"));
    assertThrows(IllegalArgumentException.class, () -> event.setTitle("."));
    assertThrows(IllegalArgumentException.class, () -> event.setTitle("1"));

    /** Description */
    assertThrows(IllegalArgumentException.class, () -> event.setDescription("D"));
    assertThrows(IllegalArgumentException.class, () -> event.setDescription("d"));
    assertThrows(IllegalArgumentException.class, () -> event.setDescription("@"));
    assertThrows(IllegalArgumentException.class, () -> event.setDescription("."));
    assertThrows(IllegalArgumentException.class, () -> event.setDescription("1"));

    /** Start time */
    //No cases? //

    /** End Time */
    // No cases? //

    /** Address */
    // Already tested in Class AddressTest //
  }

  @Test
  void setMany()
  {
    /** ID */
    // Nothing to be tested here //

    /** Title */
    assertThrows(IllegalArgumentException.class, () -> event.setTitle("TT"));
    assertThrows(IllegalArgumentException.class, () -> event.setTitle("MoreThan16Characters"));


    /** Description */
    assertThrows(IllegalArgumentException.class, () -> event.setDescription("Dd"));
    assertThrows(IllegalArgumentException.class, () -> event.setDescription("This Is Supposed To Have More Than 64 Characters"
        + " so I hope that is true. But I am not sure so I continue writing... Now it should be more than 64"));


    /** Start time */
    //No cases? //

    /** End Time */
    // No cases? //

    /** Address */
    // Already tested in Class AddressTest //
  }
  @Test
  void setBoundary()
  {

    /** Title */
    //Exactly 16 //
    try
    {
      event.setTitle("Exactly16Charact");
    }
    catch (Exception e) {Assertions.fail(e.getMessage());}

    /** Description */

    //Exactly 64//
    try
    {
      event.setDescription(
          "0123456789012345678901234567890123456789012345678901234567891234");
    }
    catch (Exception e)
    {
      Assertions.fail(e.getMessage());
    }

    /** Start time */
    //TODO

    /** End time */
    //TODO
  }

  @Test
  void setExceptions()
  {
    assertThrows(IllegalArgumentException.class, () -> event.setDescription("Dd"));
    assertThrows(IllegalArgumentException.class, () -> event.setStartTime(" "));
    assertThrows(IllegalArgumentException.class, () -> event.setTitle(" "));

  }
}
