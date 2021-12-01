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
        private IEventRepo db {get;set;}
        public EventController(IEventRepo edb)
        {
            db = edb;
        }

        [HttpGet]
        [Route("{id:int}/GetEvents")]
        public async Task<IActionResult> GetEvents([FromRoute] int id)
        {
            try
            {
                var events = await db.GetUserEvents(id);
                return Ok(events);
            } catch(Exception e)
            {
                return StatusCode(500, e.Message);
            }


        }

        [HttpPost]
        [Route("{id:int}/AddEvent")]
        public async Task<IActionResult> AddEvent([FromRoute] int id, [FromBody] Event evt)
        {
            try
            {
                evt.UserId = id;
                var events = await db.AddEventAsync(evt);
                return Ok(events);
            } catch(Exception e)
            {
                return StatusCode(500, e.Message);
            }
        }
    }
}
