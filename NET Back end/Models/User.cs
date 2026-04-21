using SNI_Projekat_NET.Enums;
using System.ComponentModel.DataAnnotations.Schema;

namespace SNI_Projekat_NET.Models
{
    [Table("korisnik")]
    public class User
    {
        [Column("id_korisnik")]
        public int Id { get; set; }

        [Column("ime")]
        public string FirstName { get; set; } = string.Empty;

        [Column("prezime")]
        public string LastName { get; set; } = string.Empty;

        [Column("korisnicko_ime")]
        public string Username { get; set; } = string.Empty;

        [Column("lozinka")]
        public string Password { get; set; } = string.Empty;

        public string Email { get; set; } = string.Empty; 

        [Column("uloga")]
        public Role Role { get; set; }

        // 1:N 
        public required List<VerificationCode> VerificationCodes { get; set; }
        public required List<SecurityEvent> SecurityEvents { get; set; }
        public required List<Buyout> Buyouts { get; set; }
    }
}
