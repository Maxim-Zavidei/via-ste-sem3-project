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
        private IList<Event> Events;
        private ICommunicator Communicator { get; set; }

        public EventData()
        {
            
        }

        public async Task FetchEvents()
        {
            try
            {
                //Sending request
                string request = "fetchevents";
                await Communicator.send(request);
                
                //Receiving message
                String rcv = await Communicator.read();
                if (!rcv.Equals("No events in database"))
                {
                    rcv = await Communicator.read();
                    Events = JsonSerializer.Deserialize<List<Event>>(rcv);
                }
                else throw new Exception("Could not fetch events");

            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
                await CloseConnection();
            }
        }









        public async Task<IList<Event>> GetEventsAsync()
        {
            try
            {
                await FetchEvents();
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
            }

            return Events;        }

        public async Task<Event> AddEventAsync(Event eventToAdd)
        {
            Event eventTemp = new Event();
            try
            {
                await Communicator.send("addevent");
                string toSend = JsonSerializer.Serialize(eventToAdd);
                await Communicator.send(toSend);
                string rcv = await Communicator.read();
                if (rcv.Equals("Successful"))
                {
                    string eventJson = await Communicator.read();
                    eventTemp = JsonSerializer.Deserialize<Event>(eventJson);
                }
                else
                {
                    //
                }

            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
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