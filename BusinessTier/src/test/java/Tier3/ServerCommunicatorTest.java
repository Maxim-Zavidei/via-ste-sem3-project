package Tier3;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import Shared.Address;
import Shared.Event;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;

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

    @BeforeEach                                         
    void setUp()  {
        try {
            serverCommunicator = ServerCommunicator.getInstance();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    
    @Test  
    void testGetUsers() {
        try {
            assertEquals(2, serverCommunicator.fetchUsersFromDatabase().size());
        
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }  
    }

    @Test  
    void testGetEvents() {
        try {
            ArrayList<Event> events = serverCommunicator.FetchUserEventFromDatabase(1);
            System.out.println(events.toString());
            assertEquals(2, serverCommunicator.FetchUserEventFromDatabase(1).size());

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }  
    }

    @Test  
    void testAddEvents() {
        try {
            Event evt = new Event();
            evt.setId(0);
            evt.setTitle("SEP");
            evt.setDescription("SEP3 Project");
            evt.setStartTime("2021-12-07T00:00:00");
            evt.setEndTime("2021-12-17T13:00:00");
            Address address = new Address();
            address.setCity("Horsens");
            address.setCountry("Denmark");
            address.setId(0);
            address.setStreetName("VIA UC");
            address.setNumber("2");
            evt.setAddress(address);
           // JSONObject jo = new JSONObject(evt);
            //Gson gson = new GsonBuilder().setDateFormat("EEE, dd MMMM yyyy HH:mm:ss zzz").create();
           // String responce = gson.toJson(evt);
            Event responce = serverCommunicator.addEvent(1, evt);
            System.out.println(responce.toString());
            assertEquals("m", responce);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }  
    }
}