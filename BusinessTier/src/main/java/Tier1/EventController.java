package Tier1;

import Shared.Event;

import java.util.ArrayList;

public class EventController extends Controller
{
    public EventController(){}


  public ArrayList<Event> FetchUserEventsFromDatabase(int userId) throws Exception
  {
      return super.communicator.FetchUserEventFromDatabase(userId);
  }

  public Event addEvent(int id, Event evt) throws Exception
  {
    return super.communicator.addEvent(id, evt);
  }

  public Event addSharedEvent(int id, Event evt, int ouId) throws Exception
  {
    return super.communicator.addSharedEvent(id, evt, ouId);
  }
  public Event editEvent(int id, Event evt) throws Exception
  {
    return super.communicator.editEvent(id, evt);
  }
  public void removeEvent(int evtId) throws Exception
  {
    super.communicator.removeEvent(evtId);
  }
}
