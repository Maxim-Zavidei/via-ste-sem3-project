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
        private IList<Event> Events;
        private IUserService UserService { get; set; }

        public EventData(IUserService userService)
        {
            UserService = userService;
        }

        public async Task FetchEvents()
        {
            Events = await UserService.GetEventsAsync();
        }

        public async Task<IList<Event>> GetEventsAsync()
        {
            try
            {
                await FetchEvents();
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
            }

            return Events;        }

        public async Task<Event> AddEventAsync(Event eventToAdd)
        {
            return await UserService.AddEventAsync(eventToAdd);
        }    
    }
}