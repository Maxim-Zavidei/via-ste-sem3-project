using System;
using System.Collections;
using System.Collections.Generic;
using System.Text.Json;
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
    }
}