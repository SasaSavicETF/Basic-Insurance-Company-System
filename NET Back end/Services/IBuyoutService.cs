using SNI_Projekat_NET.Models;

namespace SNI_Projekat_NET.Services
{
    public interface IBuyoutService
    {
        Task<List<Buyout>> FindByUser(User user);
    }
}
