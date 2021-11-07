using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Text.Json.Serialization;

namespace DataAccessTier.Model
{
    public class User
    {
        [JsonPropertyName("id")]
        [Key]
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public int Id {get;set;}
        [JsonPropertyName("username")]
        public string Username {get;set;}
        [JsonPropertyName("password")]
        public string Password {get;set;}
        [JsonPropertyName("email")]
        public string Email {get;set;}
    }
}