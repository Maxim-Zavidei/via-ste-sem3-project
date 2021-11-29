using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Text.Json;
using System.Text.Json.Serialization;

namespace DataAccessTier.Model {
    public class Event {
        [JsonPropertyName("id")]
        [Key]
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public int Id {get;set;}
        
        [JsonPropertyName("title")]
        public string Title { get; set; }
        
        [JsonPropertyName("description")]
        public string Description { get; set; }
        
        [JsonPropertyName("startTime")]
        public DateTime StartTime { get; set; }
        
        [JsonPropertyName("endTime")]
        public DateTime EndTime { get; set; }

        [JsonPropertyName("address")]
        public Address Address { get; set; }

        public override string ToString() {
            return JsonSerializer.Serialize(this);
        }
    }
}