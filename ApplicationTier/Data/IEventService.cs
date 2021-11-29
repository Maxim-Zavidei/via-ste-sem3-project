using System.Collections;
using System.Collections.Generic;
using System.Threading.Tasks;
using ApplicationTier.Models;

namespace ApplicationTier.Data
{
    public interface IEventService
    {
        Task<IList<Event>> GetEventsAsync();
        Task<Event> AddEventAsync(Event eventToAdd);
        
    }
}