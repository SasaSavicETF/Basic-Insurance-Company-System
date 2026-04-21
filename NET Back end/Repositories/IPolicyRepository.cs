using SNI_Projekat_NET.Models;

namespace SNI_Projekat_NET.Repositories
{
    public interface IPolicyRepository
    {
        Task<List<Policy>> FindUnboughtPoliciesByUser(string username);
        Task DeactivatePolicyById(int id); 
    }
}
