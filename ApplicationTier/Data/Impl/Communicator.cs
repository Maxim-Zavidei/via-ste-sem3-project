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
    public class Communicator : ICommunicator
    {
        private string ip;
        private int port;
        private static TcpClient _client;
        private static NetworkStream _stream;



        public Communicator()
        {

        }


        public async Task<String> read()
        {
            byte[] rcvLenBytes = new byte[4];
            _stream.Read(rcvLenBytes);
            int rcvLen = BitConverter.ToInt32(rcvLenBytes, 0);
            byte[] rcvBytes = new byte[rcvLen];
            _stream.Read(rcvBytes);
            String rcv = Encoding.ASCII.GetString(rcvBytes);
            return rcv;
        }

        public async Task send(String toSend)
        {
            int toSendLen = Encoding.ASCII.GetByteCount(toSend);
            byte[] toSendBytes = Encoding.ASCII.GetBytes(toSend);
            byte[] toSendLenBytes = BitConverter.GetBytes(toSendLen);
            _stream.Write(toSendLenBytes);
            _stream.Write(toSendBytes);
        }

        public async Task StartConnection()
        {
            this.ip = "localhost";
            this.port = 8080;
            _client = new TcpClient(ip, port);
            _stream = _client.GetStream();
        }

        public async Task CloseConnection()
        {
            // await send("close");
            //string r = await read();
            //if (r.Equals("closed"))
            //{
            await send("close");
            _client.Close();
            _stream.Close();
            System.Console.WriteLine("closed");
            //}

        }
    }
}