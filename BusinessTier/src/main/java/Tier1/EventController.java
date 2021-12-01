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
}
