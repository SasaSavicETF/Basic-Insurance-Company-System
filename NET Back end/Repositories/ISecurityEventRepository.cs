using SNI_Projekat_NET.Models;

namespace SNI_Projekat_NET.Repositories
{
    public interface ISecurityEventRepository
    {
        Task<List<SecurityEvent>> FindByUser(User user); 
    }
}
