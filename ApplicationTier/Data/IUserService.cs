using System.Collections.Generic;
using System.Threading.Tasks;
using ApplicationTier.Models;

namespace ApplicationTier.Data
{
    public interface IUserService
    {
        Task<User> ValidateUserAsync(string username, string password);
        Task<IList<User>> GetUsersAsync();
    }
}