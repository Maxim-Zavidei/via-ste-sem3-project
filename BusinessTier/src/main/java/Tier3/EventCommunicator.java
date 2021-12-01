package Tier3;

import Shared.Event;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;

public class EventCommunicator
{
  public EventCommunicator(){}

  public ArrayList<Event> fetchUserEventsFromDatabase(RestTemplate restTemplate, String url, int id) throws Exception
  {
    try {

      ResponseEntity<Event[]> responseEntity = restTemplate.getForEntity(url + "Event/"+ id + "/GetEvents", Event[].class);
      Event[] events = responseEntity.getBody();
      return new ArrayList<>(Arrays.asList(events));
    } catch (Exception e) {
      System.out.println(e.getMessage());
      throw new Exception("Could not fetch events for user id " +id);
    }
  }
}
