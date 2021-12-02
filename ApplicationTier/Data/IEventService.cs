using System.Collections;
using System.Collections.Generic;
using System.Threading.Tasks;
using ApplicationTier.Models;

namespace ApplicationTier.Data
{
    public interface IEventService
    {
        Task<IList<Event>> GetUserEventsAsync(int userId);
        Task<Event> AddEventAsync(int userId, Event eventToAdd);
        
    }
}