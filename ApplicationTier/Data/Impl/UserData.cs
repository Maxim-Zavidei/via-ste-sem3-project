using System;
using System.Collections.Generic;
using System.IO;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Text.Json;
using System.Threading.Tasks;
using ApplicationTier.Models;

namespace ApplicationTier.Data.Impl
{
    public class UserData : IUserService
    {
        private IList<User> _users;
        ICommunicator Communicator { get; set; }


        public UserData()
        {
           // Communicator = communicator;
        }

        public async Task FetchUsers()
        {
            try
            {
                //Sending request
                string request = "fetchusers";
                await Communicator.send(request);


                //Receiving message
                String rcv = await Communicator.read();
                if (!rcv.Equals("No users in database"))
                {
                    rcv = await Communicator.read();
                    _users = JsonSerializer.Deserialize<List<User>>(rcv);
                }
                else throw new Exception("Wrong credentials");
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
                await CloseConnection();
            }
        }

        public async Task<User> ValidateUserAsync(string username, string password)
        {
            User userToReturn = new User();
            try
            {
                //Sending
                User user = new User
                {
                    Id = 0,
                    Username = username,
                    Password = password,
                    Email = ""
                };

                //Sending request
                string request = "login";
                await Communicator.send(request);


                string userToJson = user.ToString();
                Console.WriteLine(userToJson);
                await Communicator.send(userToJson);

                //Receiving
                String rcv = await Communicator.read();
                switch (rcv)
                {
                    case "Successful":
                        {
                            rcv = await Communicator.read();
                            userToReturn = JsonSerializer.Deserialize<User>(rcv);
                            Console.WriteLine(userToReturn);
                            break;
                        }
                    case "Incorrect password":
                        {
                            throw new Exception("Incorrect password");
                            
                        }
                    case "Incorrect user":
                        {
                            throw new Exception("Incorrect user");
                        } 
                    case "Server down":
                        {
                            throw new Exception("Server is currently unavailable. Try again later.");
                        }
                }
            }
            catch (Exception e)
            {
                /*if (e is IOException || e is SocketException) Console.WriteLine(e.Message);
                else */throw new Exception("Sever is currently unavailable. Try again later.");
            }


            return userToReturn;
        }

        //TODO
        public async Task<IList<User>> GetUsersAsync()
        {
            
            try
            {
                 await FetchUsers();
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
            }

            return _users;
        }

        public async Task<User> AddUserAsync(User user)
        {
            User userToLog = new();
            try
            {
                await Communicator.send("adduser");
                string toSend = JsonSerializer.Serialize(user);
                await Communicator.send(toSend);
                string rcv = await Communicator.read();
                if (rcv.Equals("Successful"))
                {
                    string userJson = await Communicator.read();
                    userToLog = JsonSerializer.Deserialize<User>(userJson);
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
            return userToLog;
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

        public async Task<IList<Event>> GetEventsAsync()
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
                    IList<Event> events = JsonSerializer.Deserialize<List<Event>>(rcv);
                    return events;
                }
                else throw new Exception("Could not fetch events");

            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
                await CloseConnection();
            }
            return null;
        }

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
    }
}