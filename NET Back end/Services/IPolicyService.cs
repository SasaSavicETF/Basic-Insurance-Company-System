using SNI_Projekat_NET.Models;

namespace SNI_Projekat_NET.Services
{
    public interface IPolicyService
    {
        Task<List<Policy>> FindUnboughtPoliciesByUser(string username);
        Task DeactivatePolicyById(int id);
    }
}
