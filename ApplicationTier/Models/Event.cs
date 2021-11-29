using System;
using System.Text.Json;
using System.Text.Json.Serialization;

namespace ApplicationTier.Models {
    public class Event {
        
        [JsonPropertyName("title")]
        public string Title { get; set; }
        
        [JsonPropertyName("description")]
        public string Description { get; set; }
        
        [JsonPropertyName("startTime")]
        public DateTime StartTime { get; set; }
        
        [JsonPropertyName("endTime")]
        public DateTime EndTime { get; set; }

        public override string ToString() {
            return JsonSerializer.Serialize(this);
        }
    }
}