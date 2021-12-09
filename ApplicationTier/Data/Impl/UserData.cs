using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
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
        public User cashedUser { get; set; }
        ICommunicator Communicator { get; set; }
        Delegates delegates {get;set;}

        public UserData()
        {
            delegates = new Delegates();
            // Communicator = communicator;
        }

        public async Task FetchUsers()
        {
            try
            {
                //Sending request
                string request = "fetchUsers";
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
                    Email = "",
                    FirstName = "",
                    LastName = "",
                    Birthday = new DateTime(),
                    Events = new List<Event>(),
                    IsSharingCalendar = false
                };

                //Sending request
                string request = "login";
                await Communicator.send(request);


                string userToJson = user.ToString();
                Console.WriteLine(userToJson);
                await Communicator.send(userToJson);

                //Receiving
                String rcv = await Communicator.read();
                if (rcv.Equals("Successful"))
                {
                    rcv = await Communicator.read();
                    userToReturn = JsonSerializer.Deserialize<User>(rcv);
                    cashedUser = userToReturn;
                    Console.WriteLine(userToReturn);
                }
                else throw new Exception(rcv);

            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
                throw new Exception("Server is currently unavailable. Try again later.");
            }


            return userToReturn;
        }

        //TODO
        public async Task<IList<User>> GetUsersAsync()
        {
            try
            {
                await FetchUsers();
                return _users;
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
                throw new Exception("Could not fetch users from Database");
            }
        }

        public async Task<IList<User>> GetAllSharing()
        {
            try
            {
                await Communicator.send("fetchSharingUsers");
                String usersJSON = await Communicator.read();
                if (!usersJSON.Equals("Could not fetch sharing users from Database"))
                {
                    IList<User> users = JsonSerializer.Deserialize<List<User>>(usersJSON);
                    User toRemove = users.FirstOrDefault(u => u.Id == cashedUser.Id);
                    Console.WriteLine(users.Remove(toRemove));
                    return users;
                }
                else throw new Exception("No one is sharing.");
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
                throw new Exception("Could not fetch sharing users from Database");
            }
        }

        public async Task<User> AddUserAsync(User user)
        {
            User userToLog = new();
            try
            {
                await Communicator.send("addUser");
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
                    throw new Exception(rcv);
                }
            }
            catch (Exception e)
            {
                //Console.WriteLine(e.Message);
                throw new Exception(
                    $"Server unavailable. Could not create user {user.Id}, {user.Username} in database");
            }

            return userToLog;
        }

        public async Task ChangeSharingStatus(int userId)
        {
            try
            {
                await Communicator.send("changeSharingStatus");
                string toSend = JsonSerializer.Serialize(userId);
                await Communicator.send(toSend);
                string rcv = await Communicator.read();
                if (rcv.Equals("Success"))
                {
                    Console.WriteLine("Status changed");
                }
                else throw new Exception("Server unavailable. Try again later.");
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
                throw new Exception($"Could not change status");
            }
        }

        public async Task<bool> GetSharingStatus(int userId)
        {
            bool SharingStatus = false;
            try
            {
                await Communicator.send("getSharingStatus");
                string toSend = JsonSerializer.Serialize(userId);
                await Communicator.send(toSend);
                string rcv = await Communicator.read();
                if (rcv.Equals("Successful"))
                {
                    string userSharingStatus = await Communicator.read();
                    SharingStatus = JsonSerializer.Deserialize<bool>(userSharingStatus);
                }
                else throw new Exception("Server unavailable. Try again later.");
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
                throw new Exception($"Could not get status");
            }

            return SharingStatus;
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
                if (!rcv.Equals("Could not fetch events"))
                {
                    //rcv = await Communicator.read();
                    IList<Event> events = JsonSerializer.Deserialize<List<Event>>(rcv);
                    return events;
                }
                else throw new Exception("Server unavailable. Try again later.");
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
                    delegates.handler.Invoke();
                }
                else throw new Exception("Server unavailable. Try again later.");
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
                throw new Exception($"Could not add event for user {userId}");
            }

            return eventTemp;
        }

        public Delegates GetDelegates()
        {
            return delegates;
        }

        public async Task RemoveEventAsync(Event eventToRemove)
        {
            try
            {
                //Sending request
                string request = "removeEvent";
                await Communicator.send(request);
                request = eventToRemove.Id + "";
                await Communicator.send(request);

                //Receiving message
                String rcv = await Communicator.read();
                if (rcv.Equals("Successful"))
                {
                    Console.WriteLine("Event removed");
                }
                else
                    throw new Exception("Server unavailable. Try again later.");
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
                throw new Exception($"Could not remove Event");
            }
        }
    }
}