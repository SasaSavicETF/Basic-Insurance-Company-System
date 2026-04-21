using Microsoft.EntityFrameworkCore;
using SNI_Projekat_NET.Data;
using SNI_Projekat_NET.Models;
using System.Threading.Tasks;

namespace SNI_Projekat_NET.Repositories
{
    public class SecurityEventRepository : ISecurityEventRepository
    {
        private readonly SNIProjekatDbContext _context; 

        public SecurityEventRepository(SNIProjekatDbContext context)
        {
            _context = context;
        }

        public async Task<List<SecurityEvent>> FindByUser(User user)
        {
            return await _context.SecurityEvents.Include(se => se.User)
                .Where(se => se.User == user).ToListAsync(); 
        }
    }
}
