using System.Collections.Generic;
using System.Threading.Tasks;
using ApplicationTier.Models;

namespace ApplicationTier.Data.Impl
{
    public class UserData : IUserService
    {
        
        
        
        public Task<User> ValidateUserAsync(string username, string password)
        {
            throw new System.NotImplementedException();
        }

        public Task<IList<User>> GetUsersAsync()
        {
            throw new System.NotImplementedException();
        }
    }
}