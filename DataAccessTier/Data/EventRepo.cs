using System;
using System.Collections.Generic;
using Microsoft.EntityFrameworkCore;
using DataAccessTier.Model;
using System.Threading.Tasks;
using System.Linq;

namespace DataAccessTier.Data
{
    public class EventRepo : IEventRepo
    {
        CalendarDbContext db { get; set; }

        public EventRepo(CalendarDbContext calendarDbContext)
        {
            db = calendarDbContext;
        }
        public async Task<IList<Event>> GetUserEvents(int userId)
        {

            return db.Event.Include(e => e.Address)
            .Where(e => e.UserId == userId).ToList<Event>();
        }

        public async Task<Event> AddEventAsync(Event evt)
        {
            Address address = await db.Address.FirstOrDefaultAsync(ad => ad.StreetName == evt.Address.StreetName && ad.Number == evt.Address.Number && ad.City == evt.Address.City && ad.Country == evt.Address.Country);
            if (address != null)
            {
                evt.Address = null;
                evt.AddressId = address.Id;
                await db.Event.AddAsync(evt);
                await db.SaveChangesAsync();
            }
            else
            {
                await db.Event.AddAsync(evt);
                await db.SaveChangesAsync();
            }
            Event u = await db.Event.Include(e => e.Address).OrderBy(ev => ev.Id).LastOrDefaultAsync(ev => ev.Title == evt.Title);
            return u;
        }
    }

}