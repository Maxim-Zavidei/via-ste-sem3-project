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
        Task CloseConnection();
        Task StartConnection();
    }
}