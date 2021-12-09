package Tier3;

import Shared.Address;
import Shared.Event;
import Shared.User;
import org.junit.jupiter.api.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

//@SpringBootTest
public class ServerCommunicatorTest {
  /*  @Autowired
	private ServerCommunicator sCommunicator;

	@Test
	void getEvents() throws Exception
	{
		//assertEquals("[]", sCommunicator.fetchUserEventFromDatabase(2));
	}*/

    static ServerCommunicator serverCommunicator;
    static Event evt = new Event();
    static User user = new User();

    @BeforeAll static void addProperties() throws Exception
    {
      System.out.println();
      System.out.println("--------------------------------------------------------------------------------------");
      System.out.println("-------------------------------- Adding properties -----------------------------------");
      System.out.println("--------------------------------------------------------------------------------------\n\n");

      try {
        serverCommunicator = ServerCommunicator.getInstance();
        user.setId(0);
        user.setUsername("testingUsername");
        user.setPassword("string321");
        user.setEmail("email@host.domain");
        user.setBirthday("2000-12-23T23:32:23");
        user.setFirstName("User1");
        user.setLastName("None");
        user.setSharingCalendar(true);
        user.setEvents(null);
        user = serverCommunicator.addUser(user);

        evt.setId(0);
        evt.setTitle("Test event");
        evt.setDescription("Testing event In java");
        evt.setStartTime("2021-12-08T11:10:05");
        evt.setEndTime("2021-12-08T11:10:05");
        Address address = new Address();
        address.setCity("Horsens");
        address.setCountry("Denmark");
        address.setId(0);
        address.setStreetName("Vestergade");
        address.setNumber("23");
        evt.setAddress(address);
        evt = serverCommunicator.addEvent(2,evt);

      }
      catch (Exception e) {
        e.printStackTrace();
      }
      System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
      System.out.println(user.toString());
      System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
      System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
      System.out.println(evt.toString());
      System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n\n");
  }

  @BeforeEach
  void testIndicator()
  {
    System.out.println();
    System.out.println("--------------------------------------------------------------------------------------");
    System.out.print("--------------------------- Testing");

  }

    /* User Methods */

    @Test  
    void testGetUsers() {
      System.out.println(" Get Users (Test #1) ------------------------------");
      System.out.println("--------------------------------------------------------------------------------------\n\n");
        try {
            ArrayList<User> usersTest =  serverCommunicator.fetchUsersFromDatabase();
          System.out.println("\n\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
          System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Result (Test #1)~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
          System.out.println(usersTest);
          System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
        
        } catch (Exception e) {
            fail(e.getMessage());
        }  
    }
    
    @Test 
    void testGetUsersSharing(){
      System.out.println(" Get Users Sharing (Test #2) ----------------------");
      System.out.println("--------------------------------------------------------------------------------------\n\n");
      System.out.println();
      System.out.println();
      try {
        ArrayList<User> usersTest =  serverCommunicator.fetchUsersSharingFromDatabase();
        System.out.println("\n\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Result (Test #2)~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println(usersTest);
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");

      } catch (Exception e) {
        fail(e.getMessage());
      }
    }
    

    /*@Test void testAddUser()
    {
      try
      {
        serverCommunicator.deleteUser(user.getId());
      }
      catch (Exception e)
      {
        fail(e.getMessage());
      }
    }*/
    
    @Test 
    void testChangeSharingStatus()
    {
      System.out.println(" Changing sharing status (Test #3) ----------------");
      System.out.println("--------------------------------------------------------------------------------------\n\n");
      try{
        serverCommunicator.changeSharingStatus(user.getId());
      }
      catch (Exception e)
      {
        fail(e.getMessage());
      }
    }
    
    @Test
    void TestGetSharingStatus()
    {
      System.out.println(" Get Sharing status (Test #7) ---------------------");
      System.out.println("--------------------------------------------------------------------------------------");
      try{
        user.setSharingCalendar(true);
        assertTrue(user.isSharingCalendar);
        user.setSharingCalendar(false);
        assertFalse(user.isSharingCalendar);
      }
      catch (Exception e)
      {
        fail(e.getMessage());
      }
    }

    
    /* Event Methods */

    @Test  
    void testGetEvents() {
      System.out.println(" Get Events (Test #6) -----------------------------");
      System.out.println("--------------------------------------------------------------------------------------\n\n");
        try {
            ArrayList<Event> events = serverCommunicator.FetchUserEventFromDatabase(1);
          System.out.println("\n\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
          System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Result (Test #6)~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
          System.out.println(events.toString());
          System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");


        } catch (Exception e) {
            fail(e.getMessage());
        }  
    }

    /*@Test  
    void testAddEvents() {
        try {
           // JSONObject jo = new JSONObject(evt);
            //Gson gson = new GsonBuilder().setDateFormat("EEE, dd MMMM yyyy HH:mm:ss zzz").create();
           // String response = gson.toJson(evt);
            serverCommunicator.removeEvent(evt.getId());
            //assertEquals("m", response);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }*/
    //TODO
    @Test
    void testAddSharedEvent()
    {
      System.out.println(" Add shared event (Test #5) -----------------------");
      System.out.println("--------------------------------------------------------------------------------------\n\n");
      Event evt2 = new Event();
      User user2 = new User();
      User user3 = new User();
      try{

        evt2.setId(0);
        evt2.setTitle("Shared event");
        evt2.setDescription("Testing shared event with 2 users");
        evt2.setStartTime("2021-12-08T11:10:05");
        evt2.setEndTime("2021-12-08T11:10:05");
        Address address = new Address();
        address.setCity("Horsens");
        address.setCountry("Denmark");
        address.setId(0);
        address.setStreetName("Vestergade");
        address.setNumber("27");
        evt2.setAddress(address);

        user2.setId(0);
        user2.setUsername("Username2");
        user2.setPassword("123456789");
        user2.setEmail("email@host.domain");
        user2.setBirthday("1999-12-23T23:32:23");
        user2.setFirstName("User2");
        user2.setLastName("None");
        user2.setSharingCalendar(true);
        user2.setEvents(null);
        user2 = serverCommunicator.addUser(user2);

        user3.setId(0);
        user3.setUsername("Username3");
        user3.setPassword("123456789");
        user3.setEmail("email@host.domain");
        user3.setBirthday("1989-08-23T13:32:23");
        user3.setFirstName("User3");
        user3.setLastName("None");
        user3.setSharingCalendar(true);
        user3.setEvents(null);
        user3 = serverCommunicator.addUser(user3);

        evt2 = serverCommunicator.addSharedEvent(user2.getId(), evt2, user3.getId());

      }
      catch (Exception e)
      {
        fail(e.getMessage());
      }
      finally
      {
        System.out.println("\n\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Result (Test #5)~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.printf("user2Id : %s, user3Id : %s, evt2Id : %s\n",
          user2.getId(), user3.getId(), evt2.getId());
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");

        /*serverCommunicator.removeEvent(evt2.getId());
        serverCommunicator.deleteUser(user.getId());
        serverCommunicator.deleteUser(user2.getId());*/
      }
    }  

    @Test
    void testEditEvent() {
      System.out.println(" Edit event (Test #4) -----------------------------");
      System.out.println("--------------------------------------------------------------------------------------\n\n");
        try {
            evt.setTitle("Changed event");
            Event response = serverCommunicator.editEvent(1, evt);
          System.out.println("\n\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
          System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Result (Test #4)~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
          System.out.println(response.toString());
          System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
    @AfterAll
    @Test static void deleteProperties(){
      System.out.println();
      System.out.println("--------------------------------------------------------------------------------------");
      System.out.println("----------------------------------- Deleting properties ------------------------------");
      System.out.println("--------------------------------------------------------------------------------------");

        try{
          serverCommunicator.removeEvent(evt.getId());
          serverCommunicator.deleteUser(user.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
