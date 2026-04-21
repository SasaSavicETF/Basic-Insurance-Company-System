using SNI_Projekat_NET.Models;
using SNI_Projekat_NET.Repositories;
using System.Runtime.CompilerServices;

namespace SNI_Projekat_NET.Services
{
    public class PolicyService : IPolicyService
    {
        private readonly IPolicyRepository _repo; 

        public PolicyService(IPolicyRepository repo)
        {
            _repo = repo;
        }

        public async Task DeactivatePolicyById(int id)
        {
            await _repo.DeactivatePolicyById(id);
        }

        public async Task<List<Policy>> FindUnboughtPoliciesByUser(string username)
        {
            return await _repo.FindUnboughtPoliciesByUser(username); 
        }
    }
}
