using System;
using System.Collections.Generic;
using Microsoft.EntityFrameworkCore;
using DataAccessTier.Model;
using System.Threading.Tasks;
using System.Linq;
using System.Runtime.InteropServices.ComTypes;

namespace DataAccessTier.Data
{
    public class EventRepo : IEventRepo
    {
        CalendarDbContext db { get; set; }

        public EventRepo(CalendarDbContext calendarDbContext)
        {
            db = calendarDbContext;
        }
        public async Task<List<Event>> GetUserEvents(int userId)
        {
            List<Event> events = db.Event.Include(e => e.Address)
                    .Where(e => e.UserId == userId).OrderByDescending(e => e.Id).ToList();
                return events;
        }

        public async Task<Event> AddEventAsync(Event evt)
        {
            try
            {
                Address address = await db.Address.FirstOrDefaultAsync(ad =>
                    ad.StreetName == evt.Address.StreetName && ad.Number == evt.Address.Number &&
                    ad.City == evt.Address.City && ad.Country == evt.Address.Country);
                if (address != null)
                {
                    evt.Address = address;
                    evt.AddressId = address.Id;
                }
                else
                {
                    evt.Address.Id = await db.Address.MaxAsync(a => a.Id + 1);
                
                    db.Address.Add(evt.Address);
                    await db.SaveChangesAsync();
                    address = await db.Address.FirstOrDefaultAsync(a => a.Id == evt.Address.Id);
                    evt.Address = address;
                }
                
                evt.Id = await db.Event.MaxAsync(e => e.Id) + 1;
                await db.Event.AddAsync(evt);
                await db.SaveChangesAsync();
                return evt;
                    
                /*Event u = await db.Event.Include(e => e.Address).OrderByDescending(ev => ev.Id).FirstOrDefaultAsync(ev => ev.Title == evt.Title);
                return u;*/
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
                throw;
            }
        }

        public async Task<Event> EditEvent(Event evt)
        {
           /* Address address = await db.Address.FirstOrDefaultAsync(ad => ad.StreetName == evt.Address.StreetName && ad.Number == evt.Address.Number && ad.City == evt.Address.City && ad.Country == evt.Address.Country);
            if (address == null)
            {
                evt.Address.Id = await db.Address.MaxAsync(a => a.Id + 1);
                
                db.Address.Add(evt.Address);
                await db.SaveChangesAsync();
                address = await db.Address.FirstOrDefaultAsync(a => a.Id == evt.Address.Id);

            }

            //evt.Address = address;
            evt.AddressId = address.Id;
            try
            {
                
                await db.BatchUpdate<Event>()
                    .Set(b => b.Title, b => evt.Title)
                    .Set(b => b.Description, b => evt.Description)
                    .Set(b => b.StartTime, b => evt.StartTime)
                    .Set(b => b.EndTime, b => evt.EndTime)
                    .Set(b => b.AddressId, b => evt.AddressId)
                    .Where(b => b.StartTime == evt.StartTime && b.EndTime == evt.EndTime)
                    .ExecuteAsync();
                //db.Event.Update(evt);
                //
                //     await db.BatchUpdate<Address>()
                //    .Set(a => a.Country, a => address.Country)
                //    .Set(a => a.City, a => address.City)
                //    .Set(a => a.StreetName, a => address.StreetName)
                //    .Set(a => a.Number, a => address.Number)
                //    .Where(a => a.Id == address.Id)
                //    .ExecuteAsync();
                 
                await db.SaveChangesAsync();

            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
            }
            return evt;*/
             Address address = await db.Address.FirstOrDefaultAsync(ad => ad.StreetName == evt.Address.StreetName && ad.Number == evt.Address.Number && ad.City == evt.Address.City && ad.Country == evt.Address.Country);
            if (address == null)
            {
                db.Address.Add(evt.Address);
                address = db.Address.OrderBy(ad => ad.Id).Last();

            }
            evt.Address = null;
            evt.AddressId = address.Id;
            try
            {
                db.Event.Update(evt);
                await db.SaveChangesAsync();

            }
            catch (Exception e)
            {

            }
            return evt;
        }

        public async Task RemoveEvent(int id)
        {
            /*Event eventToRemove = db.Event.First(event1 => event1.Id == id);
            //db.Event.Remove(eventToRemove);
            await db.DeleteRangeAsync<Event>(e => e.Title == eventToRemove.Title && e.Description == eventToRemove.Description
            && e.StartTime == eventToRemove.StartTime && e.EndTime == eventToRemove.EndTime);
            await db.SaveChangesAsync();*/
            Event eventToRemove = db.Event.First(event1 => event1.Id == id);
            db.Event.Remove(eventToRemove);
            await db.SaveChangesAsync();
        }
    }

}