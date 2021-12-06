﻿using System.Collections.Generic;
using System.Threading.Tasks;
using ApplicationTier.Data.Impl;
using ApplicationTier.Models;

namespace ApplicationTier.Data
{
    public interface IUserService
    {        
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
    }
}