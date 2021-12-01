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

        public async Task<User> GetUserById(int userId)
        {
            try
            {
                return db.Users.FirstOrDefault(user => user.Id == userId);
            }
            catch (Exception e)
            {
                Console.WriteLine(e);
                throw new Exception("No such user in DB");
            }
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

        public async Task DeleteUser(int userId)
        {
            try
            {
                db.Users.Remove(await GetUserById(userId));
                await db.SaveChangesAsync();
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
                throw new Exception("No such user found in DB");
            }
        }
    }

}