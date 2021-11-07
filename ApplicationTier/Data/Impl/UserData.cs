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


        public UserData(ICommunicator communicator)
        {
            Communicator = communicator;
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
                if (rcv.Equals("Fetching..."))
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
                }
            }
            catch (Exception e)
            {
                if (e is IOException || e is SocketException) Console.WriteLine(e.Message);
                else throw;
            }


            return userToReturn;
        }

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
                if (rcv.Equals("Succsessful"))
                {
                    string userJson = await Communicator.read();
                    userToLog = JsonSerializer.Deserialize<User>(userJson);
                }

            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
            }
            return userToLog;
        }

        public async Task CloseConnection()
        {
            await Communicator.CloseConnection();
        }
    }
}