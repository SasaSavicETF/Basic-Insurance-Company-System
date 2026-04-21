using SNI_Projekat_NET.Models;

namespace SNI_Projekat_NET.Repositories
{
    public interface IUserRepository
    {
        Task<User?> FindByUsername(string username); 
        Task<User?> FindByEmail(string email);
        Task<bool> ExistsByUsername(string username);
        void Create(User user);
        void Save(); 
    }
}
