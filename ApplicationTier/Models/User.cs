using System;
using System.Collections.Generic;
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
        [JsonPropertyName("firstName")]
        public string FirstName { get; set; }
        [JsonPropertyName("lastName")]
        public string LastName { get; set; }
        [JsonPropertyName("isSharingCalendar")]

        public bool IsSharingCalendar { get; set; }
        [JsonPropertyName("birthday")]
        public DateTime Birthday { get; set; }

        [JsonPropertyName("events")]
        public ICollection<Event> Events { get; set; }

        public override string ToString()
        {
            return JsonSerializer.Serialize(this);
        }
    }
}