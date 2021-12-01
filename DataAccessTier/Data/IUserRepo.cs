using System;
using System.Collections.Generic;
using Microsoft.EntityFrameworkCore;
using DataAccessTier.Model;
using System.Threading.Tasks;

namespace DataAccessTier.Data
{
    public interface IUserRepo
    {

        ///<sumary>Actions related to <c>User</c> table</sumary>
        public Task<DbSet<User>> GetUsersAsync();
        public Task<User> AddUserAsync(User user);
    }
}