using System;
using System.Collections.Generic;
using Microsoft.EntityFrameworkCore;
using DataAccessTier.Model;
using System.Threading.Tasks;

namespace DataAccessTier.Data
{
    public class UserDb
    {
        CalendarDbContext db {get;set;}

        public UserDb(CalendarDbContext calendarDbContext)
        {
            db = calendarDbContext;
        }
        public async Task<DbSet<User>> GetUsersAsync() {
            return db.Users;
        }

        public async Task<User> AddUserAsync(User user) {
            await db.Users.AddAsync(user);
            await db.SaveChangesAsync();
            return user;
        }
    }

}