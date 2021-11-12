using Microsoft.AspNetCore.Components.Authorization;
using Microsoft.JSInterop;
using System.Security.Claims;
using System.Threading.Tasks;
using System.Text.Json;
using System;
using System.Collections.Generic;
using ApplicationTier.Data;
using ApplicationTier.Models;
using ApplicationTier.Data.Impl;

namespace Authentication
{
    public class CustomAuthenticationStateProvider: AuthenticationStateProvider
    {
        private readonly IJSRuntime jsRuntime;
        private readonly IUserService userService;

        private User cachedUser;
        public CustomAuthenticationStateProvider(IJSRuntime jsRuntime, IUserService userService)
        {
            this.jsRuntime = jsRuntime;
            this.userService = userService;
        }

        public override async Task<AuthenticationState> GetAuthenticationStateAsync()
        {
           var identity = new ClaimsIdentity();
            if (cachedUser == null)
            {
                string userAsJson =  await jsRuntime.InvokeAsync<string>("sessionStorage.getItem", "currentUser");
                if (!string.IsNullOrEmpty(userAsJson))
                {
                    cachedUser = JsonSerializer.Deserialize<User>(userAsJson);
                    await ValidateLogin(cachedUser.Username, cachedUser.Password);
                }
            }
            else
            {
                identity = SetupClaimsForUser(cachedUser);
            }
            ClaimsPrincipal cachedClaimsPrincipal = new ClaimsPrincipal(identity);
            return await Task.FromResult(new AuthenticationState(cachedClaimsPrincipal));
        }
        public async Task ValidateLogin(string username, string pass)
        {
            if(string.IsNullOrEmpty(username)) throw new Exception("Enter username");
            if(string.IsNullOrEmpty(pass)) throw new Exception("Enter pass");

            ClaimsIdentity identity = new ClaimsIdentity();
            try{
                User user = await userService.ValidateUserAsync(username, pass);
                identity = SetupClaimsForUser(user);
                string serilializedData = JsonSerializer.Serialize(user);
                jsRuntime.InvokeVoidAsync("sessionStorage.getItem", "currentUSer", serilializedData);
                cachedUser = user;
            } catch(Exception e)
            {
                throw e;
            }
            NotifyAuthenticationStateChanged(Task.FromResult(new AuthenticationState(new ClaimsPrincipal(identity))));
        }
        public async Task Logout()
        {
            await userService.CloseConnection();
            cachedUser = null;
            var user = new ClaimsPrincipal(new ClaimsIdentity());
            await jsRuntime.InvokeVoidAsync("sessionStorage.getItem", "currentUSer", "");
            NotifyAuthenticationStateChanged(Task.FromResult(new AuthenticationState(user)));
        }

        private ClaimsIdentity SetupClaimsForUser(User user)
        {
            List<Claim> claims = new List<Claim>();
            claims.Add(new Claim(ClaimTypes.Name, user.Username));
           // claims.Add(new Claim("Role", user.Role));

            ClaimsIdentity identity = new ClaimsIdentity(claims, "apiauth_type");
            return identity;
        }
    }
}