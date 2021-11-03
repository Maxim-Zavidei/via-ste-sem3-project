using System;
using System.Text.Json;
using System.Text.Json.Serialization;

namespace ApplicationTier.Models
{
    public class User
    {
        [JsonPropertyName("id")]
        public int Id {get;set;}
        [JsonPropertyName("username")]
        public string Username {get;set;}
        [JsonPropertyName("password")]
        public string Password {get;set;}
        [JsonPropertyName("email")]
        public string Email {get;set;}

        public override string ToString()
        {
            return JsonSerializer.Serialize(this);
        }
    }
}