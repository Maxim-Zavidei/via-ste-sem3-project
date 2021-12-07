using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using ApplicationTier.Models;

namespace ApplicationTier.Data
{
    public interface IEventService
    {
        Task GetUserEventsAsync(int userId);
        Task<Event> AddEventAsync(int userId, Event eventToAdd);
        Task<IList<Event>> GetEventsOnDay(DateTime onDay);
    }
}