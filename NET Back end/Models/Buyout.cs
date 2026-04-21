using System.ComponentModel.DataAnnotations.Schema;

namespace SNI_Projekat_NET.Models
{
    [Table("kupovina")]
    public class Buyout
    {
        [Column("id_kupovina")]
        public int Id { get; set; }

        [Column("iznos")]
        public double Amount { get; set; }

        [Column("datum_uplate")]
        public DateTime DateTime { get; set; }

        public string Status { get; set; } = string.Empty;

        // Foreign Keys: 
        public int UserId { get; set; }
        public int PolicyId { get; set; }

        // Navigation Properties: 
        public required User User { get; set; }
        public required Policy Policy { get; set; }
    }
}
