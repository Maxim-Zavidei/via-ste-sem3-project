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
                    case "Could not fetch users from Database":
                        {
                            throw new Exception("Server is currently unavailable. Try again later.");
                        }
                    case "User not found":
                    {
                        throw new Exception("No such user");
                    }
                }
            }
            catch (Exception e)
            {
                throw new Exception("Sever is currently unavailable. Try again later.");
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
                } else {
                    throw new Exception(rcv);
                }
            }
            catch (Exception e)
            {
                //Console.WriteLine(e.Message);
                throw new Exception($"Server unavailable. Could not create user {user.Id}, {user.Username} in database");
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
                } else throw new Exception("Server unavailable. Try again later.");
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
                throw new Exception($"Could not change status");
            }
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