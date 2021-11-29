using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Text.Json;
using System.Text.Json.Serialization;

namespace DataAccessTier.Model {
    public class Address {
        [JsonPropertyName("id")]
        [Key]
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public int Id {get;set;}
        [JsonPropertyName("streetName")]
        public string StreetName { get; set; }
        
        [JsonPropertyName("number")]
        public string Number { get; set; }
        
        [JsonPropertyName("city")]
        public string City { get; set; }
        
        [JsonPropertyName("country")]
        public string Country { get; set; }

        public override string ToString() {
            return JsonSerializer.Serialize(this);
        }
    }
}