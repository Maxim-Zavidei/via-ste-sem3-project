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

  public Event addSharedEvent(int id, Event evt, int ouId)
  {
    return super.communicator.addSharedEvent(id, evt, ouId);
  }
}
