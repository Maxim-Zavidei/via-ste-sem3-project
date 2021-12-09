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
        public Task<List<User>> GetUsersAsync();

        public Task<List<User>> GetUsersSharingAsync();

        public Task<User> AddUserAsync(User user);

        public Task DeleteUser(int userId);
        public Task ChangeSharingStatus(int userId);
        public Task<bool> GetSharingStatus(int userId);
    }

}