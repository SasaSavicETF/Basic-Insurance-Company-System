using Microsoft.IdentityModel.Tokens;
using SNI_Projekat_NET.DTOs;
using SNI_Projekat_NET.Models;
using SNI_Projekat_NET.Repositories;
using System.IdentityModel.Tokens.Jwt;
using System.Security.Claims;
using System.Text;

namespace SNI_Projekat_NET.Services
{
    public class AuthService : IAuthService
    {
        private readonly IUserRepository _repo;
        private readonly IConfiguration _config; 

        public AuthService(IUserRepository repo, IConfiguration config)
        {
            _repo = repo;
            _config = config; 
        }

        public async Task<string?> Login(LoginRequestDTO loginRequest)
        {
            var user = await _repo.FindByUsername(loginRequest.Username);
            if (user is null) return null;

            if (!BCrypt.Net.BCrypt.Verify(loginRequest.RawPassword, user.Password)) return null;

            return GenerateJWT(user); 
        }

        public async Task<User?> Register(User newUser)
        {
            if(! await _repo.ExistsByUsername(newUser.Username)) return null;

            newUser.Password = BCrypt.Net.BCrypt.HashPassword(newUser.Password);
            _repo.Create(newUser);
            _repo.Save();

            return newUser; 
        }

        private string GenerateJWT(User user)
        {
            var claims = new List<Claim>
            {
                new (ClaimTypes.Name, user.Username),
                new (ClaimTypes.Email, user.Email),
                new (ClaimTypes.Role, user.Role.ToString())
            };

            var tokenDescriptor = new JwtSecurityToken(
                issuer: _config["Jwt:Issuer"],
                audience: _config["Jwt:Audience"],
                claims: claims,
                expires: DateTime.UtcNow.AddMinutes(
                    _config.GetValue<int>(_config["Jwt:ExpirationInMinutes"]!)),
                signingCredentials: new SigningCredentials(
                    new SymmetricSecurityKey(Encoding.UTF8.GetBytes(_config["Jwt:Secret"]!)),
                    SecurityAlgorithms.HmacSha256
                )
            );

            return new JwtSecurityTokenHandler().WriteToken(tokenDescriptor); 
        }
    }
}
