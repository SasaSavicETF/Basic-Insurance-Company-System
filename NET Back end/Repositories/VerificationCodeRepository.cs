using Microsoft.EntityFrameworkCore;
using Org.BouncyCastle.Asn1.Mozilla;
using SNI_Projekat_NET.Data;
using SNI_Projekat_NET.Models;

namespace SNI_Projekat_NET.Repositories
{
    public class VerificationCodeRepository : IVerificationCodeRepository
    {
        private readonly SNIProjekatDbContext _context; 

        public VerificationCodeRepository(SNIProjekatDbContext context)
        {
            _context = context;
        }

        public async Task<VerificationCode> FindTopByUserOrderByLengthDesc(User user)
        {
            return await _context.VerificationCodes.Include(vc => vc.User)
                .Where(vc => vc.User == user).OrderByDescending(vc => vc.Length)
                .FirstAsync(); 
        }
    }
}
