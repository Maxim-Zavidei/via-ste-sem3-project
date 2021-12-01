using System;
using System.Collections.Generic;
using Microsoft.EntityFrameworkCore;
using DataAccessTier.Model;
using System.Threading.Tasks;

namespace DataAccessTier.Data
{
    public interface IUserRepo
    {

        
        public Task<DbSet<User>> GetUsersAsync();

        public Task<User> AddUserAsync(User user);

        public Task<IList<Event>> GetUserEvents(int userId); 
    }

}