using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using DataAccessTier.Model;
using DataAccessTier.Data;

namespace DataAccessTier.Controllers
{
    [ApiController]
    [Route("[controller]")]
    public class UserController : ControllerBase
    {
        private IUserRepo db { get; set; }
        private int userCount;

        public UserController(IUserRepo udb)
        {
            db = udb;
        }

       /* private List<User> GetUsers()
        {
            return new List<User>{
                new User{Id = 1, Username = "Yoyo", Password = "123", Email = "yoyo@gmail.com"}
            };
        }*/

        [HttpGet("GetUsers")]
        public async Task<IActionResult> Get()
        {
            try
            {
                var ussers = await db.GetUsersAsync();
                return Ok(ussers);
            }
            catch (Exception e)
            {
                return StatusCode(500, e.Message);
            }


        }

        /* [HttpGet("GetUser")]
         public IActionResult GetUser([FromQuery] string username, [Fro] string password)
         {
             var ussers = db.Users.FirstOrDefault(u => u.Username.Equals(username) && u.Password.Equals(password));
             return Ok(ussers);
         }*/

        [HttpPost("CreateUser")]
        public async Task<IActionResult> Create([FromBody] User user)
        {
            User u = await db.AddUserAsync(user);
            return Ok(u);
        }

        /* [HttpPut("UpdateUser")]
         public IActionResult Update()
         {
             return Ok();
         }*/

    
        [HttpDelete]
        [Route("DeleteUser/{userId:int}")]
        public async Task<IActionResult> DeleteUser([FromRoute] int userId)
        {
            try
            {
                await db.DeleteUser(userId);
                return Ok();
            }
            catch (Exception e)
            {
                return StatusCode(500, e.Message);
            }
        }
        
        [HttpPatch]
        [Route("/{userId:int}/ChangeSharingStatus")]
        public async Task<IActionResult> ChangeSharedStatus([FromRoute] int userId)
        {
            try
            {
                await db.ChangeSharingStatus(userId);
                return Ok();
            }
            catch (Exception e)
            {
                return StatusCode(500, e.Message);

            }
        }
    }
}
