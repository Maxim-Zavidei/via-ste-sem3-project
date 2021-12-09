using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using ApplicationTier.Models;

namespace ApplicationTier.Data.Impl
{
    public class EventData : IEventService
    {
        private IList<Event> _events;
        Dictionary<DateTime, IList<Event>> filteredEvents {get;set;}
        IUserService UserService { get; set; }

        public EventData(IUserService userService) { UserService = userService; }

        public async Task GetUserEventsAsync(int userId)
        {
            filteredEvents = new Dictionary<DateTime, IList<Event>>();
            IList<Event> events =  await UserService.GetUserEventsAsync(userId);
            foreach (var item in events)
            {
                if(filteredEvents.ContainsKey(item.StartTime.Date))
                {
                    filteredEvents.GetValueOrDefault(item.StartTime.Date).Add(item);
                } else filteredEvents.Add(item.StartTime.Date, new List<Event>(){item});
            }
        }

        public async Task<Event> AddEventAsync(int userId, Event eventToAdd)
        {
            Event evt = await UserService.AddEventAsync(userId, eventToAdd);
            if(filteredEvents.ContainsKey(evt.StartTime.Date)) filteredEvents.GetValueOrDefault(evt.StartTime.Date).Add(evt);
            else filteredEvents.Add(evt.StartTime.Date, new List<Event>(){evt});
            return evt;
        }


        public async Task<IList<Event>> GetEventsOnDay(DateTime onDay)
        {
            if(filteredEvents.ContainsKey(onDay.Date)) return filteredEvents.GetValueOrDefault(onDay);
            return new List<Event>();
        }

        public Dictionary<DateTime, IList<Event>> GetFilteredByDate()
        {
            return filteredEvents;
        }

        public async Task RemoveEventAsync(Event eventToRemove)
        {
            string rcv = await UserService.RemoveEventAsync(eventToRemove);
            if(rcv.Equals("Successful")) filteredEvents.GetValueOrDefault(eventToRemove.StartTime.Date).Remove(eventToRemove);

        }

        public async Task EditEventAsync(int UserId, Event eventToEdit)
        {
             Event evt = await UserService.EditEventAsync(UserId,  eventToEdit);
             foreach(var e in filteredEvents.GetValueOrDefault(evt.StartTime.Date))
             {
                 if(e.Id == evt.Id)
                 {
                     filteredEvents.GetValueOrDefault(evt.StartTime.Date).Remove(e);
                     filteredEvents.GetValueOrDefault(evt.StartTime.Date).Add(evt);
                     break;
                 }
             }
        }

        public async Task AddSharedEvent(int cashedId, int userId, Event sharedEvent)
        {
            Event evt = await UserService.AddSharedEvent( cashedId,  userId,  sharedEvent);
            if(filteredEvents.ContainsKey(evt.StartTime.Date)) filteredEvents.GetValueOrDefault(evt.StartTime.Date).Add(evt);
            else filteredEvents.Add(evt.StartTime.Date, new List<Event>(){evt});
        }
    }
}