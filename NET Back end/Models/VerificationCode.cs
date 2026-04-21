using System.ComponentModel.DataAnnotations.Schema;

namespace SNI_Projekat_NET.Models
{
    [Table("verifikacioni_kod")]
    public class VerificationCode
    {
        public int Id { get; set; }

        [Column("kod")]
        public string Code { get; set; } = string.Empty;

        [Column("trajanje")]
        public DateTime Length { get; set; }

        // Foreign Key: 
        public int UserId { get; set; }

        // Navigation Property:   
        public required User User { get; set; }
    }
}
