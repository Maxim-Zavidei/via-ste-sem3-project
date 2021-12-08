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
        [Route("{id:int}")]
        public async Task<IActionResult> GetEvents([FromRoute] int id)
        {
            try
            {
                var events = await db.GetUserEvents(id);
                return Ok(events);
            }
            catch (Exception e)
            {
                return StatusCode(500, e.Message);
            }
        }

        [HttpPost]
        [Route("{id:int}")]
        public async Task<IActionResult> AddEvent([FromRoute] int id, [FromBody] Event evt)
        {
            try
            {
                evt.UserId = id;
                var events = await db.AddEventAsync(evt);
                return Ok(events);
            }
            catch (Exception e)
            {
                return StatusCode(500, e.Message);
            }
        }

        [HttpPost]
        [Route("{userId:int}/SharedEvent/{otherUserId:int}")]
        public async Task<IActionResult> AddSharedEvent([FromRoute] int userId, [FromRoute] int otherUserId, [FromBody] Event evt)
        {
            try
            {
                evt.UserId = otherUserId;
                var events = await db.AddEventAsync(evt);
                evt.UserId = userId;
                events = await db.AddEventAsync(evt);
                return Ok(events);
            } catch(Exception e)
            {
                return StatusCode(500, e.Message);
            }
        }

        [HttpPatch]
        [Route("{id:int}")]
        public async Task<IActionResult> EditEvent([FromRoute] int id, [FromBody] Event evt)
        {
            try
            {
                evt.UserId = id;
                var events = await db.EditEvent(evt);
                return Ok(events);
            }
            catch (Exception e)
            {
                return StatusCode(500, e.Message);
            }
        }


        [HttpDelete]
        [Route("{id:int}")]
        public async Task<IActionResult> RemoveEvent([FromRoute] int id)
        {
            try
            {
                await db.RemoveEvent(id);
                return Ok();
            }
            catch (Exception e)
            {
                return StatusCode(500, e.Message);
            }
        }
    }
}
