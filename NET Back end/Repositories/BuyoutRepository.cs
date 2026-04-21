using Microsoft.EntityFrameworkCore;
using SNI_Projekat_NET.Data;
using SNI_Projekat_NET.Models;

namespace SNI_Projekat_NET.Repositories
{
    public class BuyoutRepository : IBuyoutRepository
    {
        private readonly SNIProjekatDbContext _context; 

        public BuyoutRepository(SNIProjekatDbContext context)
        {
            _context = context;
        }

        public async Task<List<Buyout>> FindByUser(User user)
        {
            return await _context.Buyouts.Include(b => b.User)
                .Where(b => b.User == user).ToListAsync(); 
        }
    }
}
