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
        private string ip;
        private int port;
        private IList<User> _users;
        private TcpClient _client;
        private NetworkStream _stream;
        public UserData(string ip, int port)
        {
            this.ip = ip;
            StartConnection();
        }

        private void StartConnection()
        {
            IPAddress ipAddress = IPAddress.Parse(ip);
            TcpListener listener = new TcpListener(ipAddress, port);
            listener.Start();
            while (true)
            {
                try
                {
                    _client = listener.AcceptTcpClient();
                    Console.WriteLine("Connection initiated");
                    _stream = _client.GetStream();
                    FetchData();
                }
                catch (Exception e)
                {
                    Console.WriteLine(e.Message);
                    CloseConnection();
                }
            }
        }

        private async Task FetchData()
        {
            try
            {
                StreamReader streamReader = new StreamReader(_stream, Encoding.UTF8);
                string dataReceived = streamReader.ReadLine();

                if (!string.IsNullOrEmpty(dataReceived))
                {
                    _users = JsonSerializer.Deserialize<List<User>>(dataReceived);
                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
                CloseConnection();
            }
        }

        private void CloseConnection()
        {
            _client.Close();
        }
        
        public Task<User> ValidateUserAsync(string username, string password)
        {
            StreamWriter streamWriter = new StreamWriter(_stream, Encoding.UTF8);
            User user = new User();
            user.Username = username;
            user.Password = password;
            string userToJson = JsonSerializer.Serialize(user);
            streamWriter.WriteLineAsync(userToJson);
            throw new NotImplementedException();
        }

        public async Task<IList<User>> GetUsersAsync()
        {
            return _users;
        }
    }
}