using Microsoft.EntityFrameworkCore;
using SNI_Projekat_NET.Data;
using SNI_Projekat_NET.Models;

namespace SNI_Projekat_NET.Repositories
{
    public class UserRepository : IUserRepository
    {
        private readonly SNIProjekatDbContext _context; 

        public UserRepository(SNIProjekatDbContext context)
        {
            _context = context;
        }

        public async Task<User?> FindByEmail(string email)
        {
            return await _context.Users.FirstOrDefaultAsync(u => u.Email.Equals(email)); 
        }

        public async Task<User?> FindByUsername(string username)
        {
            return await _context.Users.FirstOrDefaultAsync(u => u.Username.Equals(username)); 
        }

        public async Task<bool> ExistsByUsername(string username)
        {
            return await _context.Users.AnyAsync(u => u.Username.Equals(username));
        }

        public void Create(User user)
        {
            _context.Users.Add(user);
        }

        public void Save()
        {
            _context.SaveChangesAsync();
        }
    }
}
