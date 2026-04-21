using System.ComponentModel.DataAnnotations.Schema;

namespace SNI_Projekat_NET.Models
{
    [Table("polisa_osiguranja")]
    public class Policy
    {
        [Column("id_polisa")]
        public int Id { get; set; }

        [Column("tip")]
        public string Type { get; set; } = string.Empty;

        [Column("opis")]
        public string Description { get; set; } = string.Empty; 

        [Column("cijena")]
        public double Price { get; set; }

        [Column("aktivna")]
        public bool Active { get; set; }

        // 1:N 
        public required List<Buyout> Buyouts { get; set; }
    }
}
