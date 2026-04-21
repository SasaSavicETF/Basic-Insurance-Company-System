using SNI_Projekat_NET.Models;

namespace SNI_Projekat_NET.Services
{
    public interface ISecurityEventService
    {
        Task<List<SecurityEvent>> FindByUser(User user);
    }
}
