using Microsoft.EntityFrameworkCore;
using SNI_Projekat_NET.Models;

namespace SNI_Projekat_NET.Data
{
    public class SNIProjekatDbContext : DbContext
    {
        public SNIProjekatDbContext(DbContextOptions options) : base(options) {}

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            base.OnModelCreating(modelBuilder);

            modelBuilder.Entity<User>()
                .Property(u => u.Role)
                .HasConversion<string>();
        }

        public DbSet<User> Users { get; set; }
        public DbSet<Policy> Policies { get; set; }
        public DbSet<VerificationCode> VerificationCodes { get; set; }
        public DbSet<SecurityEvent> SecurityEvents { get; set; }
        public DbSet<Buyout> Buyouts { get; set; }
    }
}
