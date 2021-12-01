using System;
using System.Collections.Generic;
using Microsoft.EntityFrameworkCore;
using DataAccessTier.Model;
using System.Threading.Tasks;
using System.Linq;

namespace DataAccessTier.Data
{
    public class UserRepo : IUserRepo
    {
        CalendarDbContext db {get;set;}

        public UserRepo(CalendarDbContext calendarDbContext)
        {
            db = calendarDbContext;
        }
        public async Task<DbSet<User>> GetUsersAsync() {
            return db.Users;
        }

        public async Task<User> AddUserAsync(User user) {
            await db.Users.AddAsync(user);
            await db.SaveChangesAsync();
            User u = await db.Users.FirstAsync(us => us.Username==user.Username);
            return u;
        }

        public async Task<IList<Event>> GetUserEvents(int userId)
        {
            
            return db.Event.Include(e => e.Address)
            .Where(e => e.UserId == userId).ToList<Event>();
            //throw new NotImplementedException();
        }
    }

}