using System.Collections.Generic;
using System.Threading.Tasks;
using ApplicationTier.Data.Impl;
using ApplicationTier.Models;

namespace ApplicationTier.Data
{
    public interface ICommunicator
    {
        Task send(string toSend);
        Task<string> read();
        Task CloseConnection();
        Task StartConnection();
    }
}