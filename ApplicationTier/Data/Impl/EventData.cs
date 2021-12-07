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
            return await UserService.AddEventAsync(userId, eventToAdd);
        }


        public async Task<IList<Event>> GetEventsOnDay(DateTime onDay)
        {
            /* return new List<Event>() {
                 new Event() {
                     Address = new Address(),
                     Description = "hello 1",
                     EndTime = new DateTime(2020, 1, 1),
                     Id = 1,
                     StartTime = new DateTime(2020, 1, 1),
                     Title = "hello 1"
                 },
                 new Event() {
                     Address = new Address(),
                     Description = "hello 2",
                     EndTime = new DateTime(2020, 1, 1),
                     Id = 1,
                     StartTime = new DateTime(2020, 1, 1),
                     Title = "hello 2"
                 }
             };*/
            
            if(filteredEvents.ContainsKey(onDay.Date)) return filteredEvents.GetValueOrDefault(onDay);
            return new List<Event>();
        }
    }
}