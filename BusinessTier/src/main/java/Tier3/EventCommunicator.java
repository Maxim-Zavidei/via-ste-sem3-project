package Tier3;

import Shared.Event;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

public class EventCommunicator {
  public EventCommunicator() {
    gson = new Gson();

  }

  private Gson gson;

  public ArrayList<Event> fetchUserEventsFromDatabase(RestTemplate restTemplate, String url, int id) throws Exception {
    try {
      ResponseEntity<Event[]> responseEntity = restTemplate.getForEntity(url + "Event/" + id,
          Event[].class);
      Event[] events = responseEntity.getBody();
      return new ArrayList<>(Arrays.asList(events));
    } catch (Exception e) {
      throw new Exception("Could not fetch events");
    }
  }
  public Event addEvent(RestTemplate restTemplate, String url, Event evt, int id) throws Exception
  {
    try {
      ResponseEntity<String> responseEntityStr = restTemplate
          .postForEntity(url + "Event/" + id, evt, String.class);
      Event u = gson.fromJson(responseEntityStr.getBody(), Event.class);
      return u;
    } catch (Exception e) {
      throw new Exception("Could not add event");
    }
  }

  public Event addSharedEvent(RestTemplate restTemplate, String url, Event evt, int userId, int otherUserId) throws Exception{
    try {
      ResponseEntity<String> responseEntityStr = restTemplate
          .postForEntity(url + "Event/" + userId + "/SharedEvent/" + otherUserId, evt, String.class);
      Event u = gson.fromJson(responseEntityStr.getBody(), Event.class);
      return u;
    } catch (Exception e) {
      throw new Exception("Could not add shared event");
    }
  }
}
