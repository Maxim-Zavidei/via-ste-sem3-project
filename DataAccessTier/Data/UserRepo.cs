using System;
using System.Collections.Generic;
using System.Data.Common;
using Microsoft.EntityFrameworkCore;
using DataAccessTier.Model;
using System.Threading.Tasks;
using System.Linq;
using System.Net;
using System.Net.Sockets;
using Microsoft.EntityFrameworkCore.Internal;

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
                throw new Exception($"User {userId} not found in database");
            }
        }

        public async Task<DbSet<User>> GetUsersAsync() {
            try
            {
                return db.Users;
            }
            catch (Exception e)
            {
                Console.WriteLine(e);
                throw new Exception("Could not fetch users from DB");
            }
        }

        public async Task<List<User>> GetUsersSharingAsync()
        {
            try
            { 
                return db.Users.Where(dbUser => dbUser.IsSharingCalendar).ToList();
            }
            catch (Exception e)
            {
                Console.WriteLine(e);
                throw new Exception("Could not fetch sharing users from DB");
            }
        }

        public async Task<User> AddUserAsync(User user) {
            try
            {
                await db.Users.AddAsync(user);
                await db.SaveChangesAsync();
                User u = await db.Users.FirstAsync(us => us.Username == user.Username);
                return u;
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
                throw new Exception($"Could not create user {user.Id}, {user.Username} in database");
            }
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
                throw new Exception($"User {userId} not found in database");
            }
        }

        public async Task ChangeSharingStatus(int userId)
        {
            try
            {
                User user = await GetUserById(userId);
                user.IsSharingCalendar = !user.IsSharingCalendar;
                db.Users.Update(user);
                await db.SaveChangesAsync();
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
                throw new Exception($"User {userId} not found in database");
            }
        }

        public async Task<bool> GetSharingStatus(int userId)
        {
            User user = await GetUserById(userId);
            bool sharingStatus = user.IsSharingCalendar;
            return sharingStatus;


        }
    }

}