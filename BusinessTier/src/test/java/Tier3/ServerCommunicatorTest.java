package Tier3;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import Shared.Event;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

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
            //assertEquals(0, serverCommunicator.fetchUserEventFromDatabase(1).size());
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
            //evt.setStartTime(new LocalDateTime(03,12,2021,0 ,0,0));
            //assertEquals(0, serverCommunicator.addEvent(1, new Event(){}).size());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }  
    }
}
