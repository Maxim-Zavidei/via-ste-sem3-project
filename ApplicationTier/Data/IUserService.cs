using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using ApplicationTier.Data.Impl;
using ApplicationTier.Models;
using ApplicationTier.Pages;

namespace ApplicationTier.Data
{
    public interface IUserService
    {  
        Delegates GetDelegates();
        User cashedUser {get;set;}
        Task<User> ValidateUserAsync(string username, string password);
        Task<IList<User>> GetUsersAsync();
        Task<User> AddUserAsync(User user);
        Task ChangeSharingStatus(int userId);
        Task<bool> GetSharingStatus(int userId);
        Task<IList<User>> GetAllSharing();
        Task CloseConnection();
        Task StartConnection();
        
        /// related to Events
        Task<IList<Event>> GetUserEventsAsync(int userId);
        Task<Event> AddEventAsync(int userId, Event eventToAdd);
        Task RemoveEventAsync( Event eventToRemove);

    }
}