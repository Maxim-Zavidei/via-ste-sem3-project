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
    public class EventController : ControllerBase
    {
        private IEventRepo db { get; set; }

        public EventController(IEventRepo edb)
        {
            db = edb;
        }

        [HttpGet]
        [Route("{userId:int}")]
        public async Task<IActionResult> GetEvents([FromRoute] int userId)
        {
            try
            {
                var events = await db.GetUserEvents(userId);
                return Ok(events);
            }
            catch (Exception e)
            {
                return StatusCode(500, e.Message);
            }
        }

        [HttpPost]
        [Route("{userId:int}")]
        public async Task<IActionResult> AddEvent([FromRoute] int userId, [FromBody] Event evt)
        {
            try
            {
                evt.UserId = userId;
                var events = await db.AddEventAsync(evt);
                return Ok(events);
            }
            catch (Exception e)
            {
                return StatusCode(500, e.Message);
            }
        }

        //Needs modifying//
        [HttpPost]
        [Route("{userId:int}/SharedEvent/{otherUserId:int}")]
        public async Task<IActionResult> AddSharedEvent([FromRoute] int userId, [FromRoute] int otherUserId, [FromBody] Event evt)
        {
            try
            {
                Event tempEvent = evt;
                evt.UserId = userId;
                Event event1 = await db.AddEventAsync(evt);
                
                tempEvent.UserId = otherUserId;
                Event event2 = await db.AddEventAsync(tempEvent);
                
                Console.WriteLine($"\n user1: {userId}, {event1} \n\n user2: {otherUserId}, {event2}");
                return Ok(event2);
            } catch(Exception e)
            {
                return StatusCode(500, e.Message);
            }
        }

        [HttpPatch]
        [Route("{userId:int}")]
        public async Task<IActionResult> EditEvent([FromRoute] int userId, [FromBody] Event evt)
        {
            try
            {
                evt.UserId = userId;
                var events = await db.EditEvent(evt);
                return Ok(events);
            }
            catch (Exception e)
            {
                return StatusCode(500, e.Message);
            }
        }


        [HttpDelete]
        [Route("{eventId:int}")]
        public async Task<IActionResult> RemoveEvent([FromRoute] int eventId)
        {
            try
            {
                await db.RemoveEvent(eventId);
                return Ok();
            }
            catch (Exception e)
            {
                return StatusCode(500, e.Message);
            }
        }
    }
}
