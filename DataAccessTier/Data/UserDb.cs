using System;
using System.Collections.Generic;
using Microsoft.EntityFrameworkCore;
using DataAccessTier.Model;
using System.Threading.Tasks;

namespace DataAccessTier.Data
{
    public class UserDb : CalendarDbContext
    {

        public async Task<DbSet<User>> GetUsersAsync() {
            return Users;
        }

        public async Task<User> AddUserAsync(User user) {
            this.Add(user);
            this.SaveChanges();
            return user;
        }
    }

}