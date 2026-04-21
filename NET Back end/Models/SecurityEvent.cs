using System.ComponentModel.DataAnnotations.Schema;

namespace SNI_Projekat_NET.Models
{
    [Table("bezbjednosni_dogadjaj")]
    public class SecurityEvent
    {
        [Column("id_dogadjaj")]
        public int Id { get; set; }

        [Column("vrijeme")]
        public DateTime DateTime { get; set; }

        [Column("akcija")]
        public string Action { get; set; } = string.Empty;

        [Column("detalji")]
        public string Details { get; set; } = string.Empty; 

        // Foreign Key: 
        public int UserId { get; set; }

        // Navigation Property: 
        public required User User { get; set; }
    }
}
