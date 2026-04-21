using SNI_Projekat_NET.Models;
using SNI_Projekat_NET.Repositories;

namespace SNI_Projekat_NET.Services
{
    public class BuyoutService : IBuyoutService
    {
        private readonly IBuyoutRepository _repo; 

        public BuyoutService(IBuyoutRepository repo)
        {
            _repo = repo;
        }

        public async Task<List<Buyout>> FindByUser(User user)
        {
            return await _repo.FindByUser(user);
        }
    }
}
