using SNI_Projekat_NET.Models;
using SNI_Projekat_NET.Repositories;

namespace SNI_Projekat_NET.Services
{
    public class SecurityEventService : ISecurityEventService
    {
        private readonly ISecurityEventRepository _repo; 

        public SecurityEventService(ISecurityEventRepository repo)
        {
            _repo = repo;
        }

        public async Task<List<SecurityEvent>> FindByUser(User user)
        {
            return await _repo.FindByUser(user);
        }
    }
}
