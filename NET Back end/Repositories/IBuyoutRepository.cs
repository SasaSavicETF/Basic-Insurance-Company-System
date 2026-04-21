using SNI_Projekat_NET.Models;

namespace SNI_Projekat_NET.Repositories
{
    public interface IBuyoutRepository
    {
        Task<List<Buyout>> FindByUser(User user); 
    }
}
