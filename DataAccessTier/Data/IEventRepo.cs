using System;
using System.Collections.Generic;
using Microsoft.EntityFrameworkCore;
using DataAccessTier.Model;
using System.Threading.Tasks;

namespace DataAccessTier.Data
{
    public interface IEventRepo
    {
        ///<sumary>Actions related to <c>Event</c> table</sumary>
        public Task<IList<Event>> GetUserEvents(int userId); 
        public Task<Event> AddEventAsync(Event evt);
    }
}