package Tier3;

import Shared.Address;
import Shared.Event;
import Shared.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

	@Test
	void contextLoads() {
	}
    ServerCommunicator serverCommunicator;
    Event evt = new Event();
    User user = new User();

    void addEvent (int userId) throws Exception
    {
    evt.setId(0);
    evt.setTitle("String");
    evt.setDescription("String");
    evt.setStartTime("2021-12-08T11:10:05");
    evt.setEndTime("2021-12-08T11:10:05");
    Address address = new Address();
    address.setCity("String");
    address.setCountry("Strin");
    address.setId(0);
    address.setStreetName("Stringche");
    address.setNumber("string");
    evt.setAddress(address);
    evt = serverCommunicator.addEvent(userId, evt);
  }

    void addUser () throws Exception
    {
      user.setId(0);
      user.setUsername("string123");
      user.setPassword("string321");
      user.setEmail("email@domain.com");
      user.setBirthday("0001-01-01T00:00:00");
      user.setFirstName("string");
      user.setLastName("string");
      user.setSharingCalendar(true);
      user.setEvents(null);
      user = serverCommunicator.addUser(user);
    }

    @BeforeEach                                         
    void setUp()  {
        try {
            serverCommunicator = ServerCommunicator.getInstance();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /* User Methods */

    @Test  
    void testGetUsers() {
        try {
            ArrayList<User> usersTest =  serverCommunicator.fetchUsersFromDatabase();
          System.out.println(usersTest);
        
        } catch (Exception e) {
            fail(e.getMessage());
        }  
    }
    
    @Test 
    void testGetUsersSharing(){
      try {
        ArrayList<User> usersTest =  serverCommunicator.fetchUsersSharingFromDatabase();
        System.out.println(usersTest);

      } catch (Exception e) {
        fail(e.getMessage());
      }
    }
    
    @Test
    void testAddUser()
    {
      try
      {
        addUser();
        serverCommunicator.deleteUser(user.getId());
      }
      catch (Exception e)
      {
        fail(e.getMessage());
      }
    }
    
    @Test 
    void testChangeSharingStatus()
    {
      try{
        addUser();
        serverCommunicator.changeSharingStatus(user.getId());
        testDeleteUser();
      }
      catch (Exception e)
      {
        fail(e.getMessage());
      }
    }
    
    @Test
    void TestGetSharingStatus()
    {
      try{
        addUser();
        user.setSharingCalendar(true);
        assertTrue(user.isSharingCalendar);
        user.setSharingCalendar(false);
        assertFalse(user.isSharingCalendar);
        testDeleteUser();
      }
      catch (Exception e)
      {
        fail(e.getMessage());
      }
    }
    
  @Test void testDeleteUser()
  {
    try
    {
      addUser();
      serverCommunicator.deleteUser(user.getId());
    }
    catch (Exception e)
    {
      fail(e.getMessage());
    }
  }
    
    /* Event Methods */

    @Test  
    void testGetEvents() {
        try {
            ArrayList<Event> events = serverCommunicator.FetchUserEventFromDatabase(1);
            System.out.println(events.toString());

        } catch (Exception e) {
            fail(e.getMessage());
        }  
    }

    @Test  
    void testAddEvents() {
        try {
            addEvent(1);
           // JSONObject jo = new JSONObject(evt);
            //Gson gson = new GsonBuilder().setDateFormat("EEE, dd MMMM yyyy HH:mm:ss zzz").create();
           // String responce = gson.toJson(evt);
            serverCommunicator.removeEvent(evt.getId());
            //assertEquals("m", responce);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    //TODO
    @Test
    void testAddSharedEvent() throws Exception
    {
      User user2 = new User();
      try{
        addUser();
        addEvent(user.getId());

        user2.setId(0);
        user2.setUsername("string2");
        user2.setPassword("string2");
        user2.setEmail("email@domain.com");
        user2.setBirthday("0001-01-01T00:00:00");
        user2.setFirstName("string");
        user2.setLastName("string");
        user2.setSharingCalendar(true);
        user2.setEvents(null);
        user2 = serverCommunicator.addUser(user2);
        System.out.printf("userId : %s, user2Id : %s, evtId : %s",
            user.getId(), user2.getId(), evt.getId());
        serverCommunicator.addSharedEvent(user.getId(), evt, user2.getId());

      }
      catch (Exception e)
      {
        fail(e.getMessage());
      }
      finally
      {
        testRemoveEvet();
        testDeleteUser();
        serverCommunicator.deleteUser(user2.getId());
      }
    }  

    @Test
    void testEditEvent() {
        try {
            addEvent(1);
            evt.setTitle("Changed event");
            Event response = serverCommunicator.editEvent(1, evt);
            serverCommunicator.removeEvent(evt.getId());
            System.out.println(response.toString());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            fail(e.getMessage());
        }
    }
    @Test
    void testRemoveEvet(){
        try{
            addEvent(1);
            serverCommunicator.removeEvent(evt.getId());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
