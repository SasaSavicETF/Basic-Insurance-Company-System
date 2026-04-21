using Microsoft.EntityFrameworkCore;
using SNI_Projekat_NET.Data;
using SNI_Projekat_NET.Models;

namespace SNI_Projekat_NET.Repositories
{
    public class PolicyRepository : IPolicyRepository
    {
        private readonly SNIProjekatDbContext _context; 

        public PolicyRepository(SNIProjekatDbContext context)
        {
            _context = context;
        }

        public async Task DeactivatePolicyById(int id)
        {
            var policy = await _context.Policies.FindAsync(id);
            if (policy == null) return;

            policy.Active = false;
            await _context.SaveChangesAsync(); 
        }

        public async Task<List<Policy>> FindUnboughtPoliciesByUser(string username)
        {
            //var boughtPoliciesIds = _context.Buyouts.Include(b => b.User)
            //    .Where(b => b.User.Username.Equals(username)).Select(b => b.PolicyId)
            //    .ToList();

            //return await _context.Policies.Where(p => !boughtPoliciesIds.Contains(p.Id))
            //    .ToListAsync(); 

            return await _context.Policies
                .Where(p => !_context.Buyouts.Any(b => b.PolicyId == p.Id
                && b.User.Username.Equals(username))).ToListAsync(); 
        }
    }
}
