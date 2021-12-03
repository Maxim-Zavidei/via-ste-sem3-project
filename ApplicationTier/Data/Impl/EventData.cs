using System;
using System.Collections;
using System.Collections.Generic;
using System.Text.Json;
using System.Threading.Tasks;
using ApplicationTier.Models;

namespace ApplicationTier.Data.Impl
{
    public class EventData : IEventService
    {
        private IList<Event> _events;
        ICommunicator Communicator { get; set; }

        public EventData() { }

        public async Task<IList<Event>> GetUserEventsAsync(int userId)
        {
            try
            {
                //Sending request
                string request = "fetchEvents";
                await Communicator.send(request);
                request = userId + "";
                await Communicator.send(request);
                
                //Receiving message
                String rcv = await Communicator.read();
                if (!rcv.Equals("Could not fetch events for user id " + userId))
                {
                    rcv = await Communicator.read();
                    IList<Event> events = JsonSerializer.Deserialize<List<Event>>(rcv);
                    return events;
                } else throw new Exception("Server unavailable. Try again later.");
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
                throw new Exception($"Could not fetch events for user {userId} ");
            }
            return null;
        }

        public async Task<Event> AddEventAsync(int userId, Event eventToAdd)
        {
            Event eventTemp = new Event();
            try
            {
                await Communicator.send("addEvent");
                string toSend = JsonSerializer.Serialize(eventToAdd);
                await Communicator.send(toSend);
                toSend = userId + "";
                await Communicator.send(toSend);
                string rcv = await Communicator.read();
                if (rcv.Equals("Successful"))
                {
                    string eventJson = await Communicator.read();
                    eventTemp = JsonSerializer.Deserialize<Event>(eventJson);
                } else throw new Exception("Server unavailable. Try again later.");
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
                throw new Exception($"Could not add event for user {userId}");
            }
            return eventTemp;
        }
        public async Task StartConnection()
    
        {
            Communicator = new Communicator();
            await Communicator.StartConnection();
        }
        public async Task CloseConnection()
        {
            await Communicator.CloseConnection();
        }
    }
}