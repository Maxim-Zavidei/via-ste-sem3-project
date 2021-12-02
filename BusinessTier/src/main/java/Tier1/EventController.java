package Tier1;

import Shared.Event;

import java.util.ArrayList;

public class EventController extends Controller
{
    public EventController(){}


  public ArrayList<Event> fetchUserEventsFromDatabase(int userId)
      throws Exception
  {
    try
    {
      return super.communicator.fetchUserEventFromDatabase(userId);
    }
    catch (Exception e)
    {
      throw e;
    }
  }

  public Event addEvent(int id, Event evt)
  {
    return super.communicator.addEvent(id, evt);
  }

  public Event addSharedEvent(int id, Event evt, int ouId)
  {
    return super.communicator.addSharedEvent(id, evt, ouId);
  }
}
