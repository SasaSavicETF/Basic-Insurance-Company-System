using SNI_Projekat_NET.DTOs;
using SNI_Projekat_NET.Models;

namespace SNI_Projekat_NET.Services
{
    public interface IAuthService
    {
        Task<string?> Login(LoginRequestDTO loginRequest);
        Task<User?> Register(User newUser); 
    }
}
