using System;
using System.Collections.Generic;
using Microsoft.EntityFrameworkCore;
using DataAccessTier.Model;
using System.Threading.Tasks;

namespace DataAccessTier.Data
{
    public interface IUserRepo
    {

        public Task<User> GetUserById(int userId);
        ///<sumary>Actions related to <c>User</c> table</sumary>
        public Task<DbSet<User>> GetUsersAsync();

        public Task<User> AddUserAsync(User user);

        public Task DeleteUser(int userId);
        public Task ChangeSharingStatus(int userId);
    }

}