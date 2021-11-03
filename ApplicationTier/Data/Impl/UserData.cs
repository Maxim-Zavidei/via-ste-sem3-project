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
        private TcpClient _client;
        private NetworkStream _stream;

        public async Task StartConnection(string ip, int port)
        {
            TcpClient _client = new TcpClient(ip, port);

            try
            {
                _stream = _client.GetStream();
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
                CloseConnection();
            }
        }

        public async Task FetchUsers()
        {
            try
            {
                //Sending request
                string request = "fetchUsers";
                int toSendLen = Encoding.ASCII.GetByteCount(request);
                byte[] toSendBytes = Encoding.ASCII.GetBytes(request);
                byte[] toSendLenBytes = BitConverter.GetBytes(toSendLen);
                _stream.Write(toSendLenBytes);
                _stream.Write(toSendBytes);


                //Receiving message
                byte[] rcvLenBytes = new byte[4];
                _stream.Read(rcvLenBytes);
                int rcvLen = BitConverter.ToInt32(rcvLenBytes, 0);
                byte[] rcvBytes = new byte[rcvLen];
                _stream.Read(rcvBytes);
                String rcv = Encoding.ASCII.GetString(rcvBytes);
                if (rcv.Equals("Fetching..."))
                {
                    rcvLenBytes = new byte[4];
                    _stream.Read(rcvLenBytes);
                    rcvLen = BitConverter.ToInt32(rcvLenBytes, 0);
                    rcvBytes = new byte[rcvLen];
                    _stream.Read(rcvBytes);
                    rcv = Encoding.ASCII.GetString(rcvBytes);
                    _users = JsonSerializer.Deserialize<List<User>>(rcv);
                }
                else throw new Exception("Wrong credentials");
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
                CloseConnection();
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
                    Username = username,
                    Password = password
                };

                //Sending request
                string request = "login";
                int toSendLen = Encoding.ASCII.GetByteCount(request);
                byte[] toSendBytes = Encoding.ASCII.GetBytes(request);
                byte[] toSendLenBytes = BitConverter.GetBytes(toSendLen);
                _stream.Write(toSendLenBytes);
                _stream.Write(toSendBytes);


                string userToJson = user.ToString();
                Console.WriteLine(userToJson);
                toSendLen = Encoding.ASCII.GetByteCount(userToJson);
                toSendBytes = Encoding.ASCII.GetBytes(userToJson);
                toSendLenBytes = BitConverter.GetBytes(toSendLen);
                _stream.Write(toSendLenBytes);
                _stream.Write(toSendBytes);

                //Receiving
                byte[] rcvLenBytes = new byte[4];
                _stream.Read(rcvLenBytes);
                int rcvLen = BitConverter.ToInt32(rcvLenBytes, 0);
                byte[] rcvBytes = new byte[rcvLen];
                _stream.Read(rcvBytes);
                String rcv = Encoding.ASCII.GetString(rcvBytes);
                switch (rcv)
                {
                    case "Successful":
                    {
                        rcvLenBytes = new byte[4];
                        _stream.Read(rcvLenBytes);
                        rcvLen = BitConverter.ToInt32(rcvLenBytes, 0);
                        rcvBytes = new byte[rcvLen];
                        _stream.Read(rcvBytes);
                        rcv = Encoding.ASCII.GetString(rcvBytes);
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

            /*Console.WriteLine("Debug point 3");
            StreamWriter streamWriter = new StreamWriter(_stream, Encoding.UTF8);
            StreamReader streamReader = new StreamReader(_stream, Encoding.UTF8);
            await streamWriter.WriteLineAsync("login");
            User user = new User
            {
                Username = username,
                Password = password
            };
            string userToJson = JsonSerializer.Serialize(user);
            Console.WriteLine(userToJson);
            await streamWriter.WriteLineAsync(userToJson);
            string dataReceived = await streamReader.ReadLineAsync();
            Console.WriteLine(dataReceived);
            User userReceived = new User();
            if (!string.IsNullOrEmpty(dataReceived))
                userReceived = JsonSerializer.Deserialize<User>(dataReceived);
            return userReceived;*/
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

        public async Task CloseConnection()
        {
            _client.Close();
            _stream.Close();
        }
    }
}