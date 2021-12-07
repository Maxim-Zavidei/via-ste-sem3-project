using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using ApplicationTier.Models;

namespace ApplicationTier.Data.Impl
{
    public class EventData : IEventService
    {
        private IList<Event> _events;
        IUserService UserService { get; set; }

        public EventData(IUserService userService) {UserService = userService; }

        public async Task<IList<Event>> GetUserEventsAsync(int userId)
        {
            return await UserService.GetUserEventsAsync(userId);
        }

        public async Task<Event> AddEventAsync(int userId, Event eventToAdd)
        {
           return await UserService.AddEventAsync(userId, eventToAdd);
        }

        
        public async Task<IList<Event>> GetEventsOnDay(DateTime onDay) {
            return new List<Event>() {
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
            };
        }
    }
}