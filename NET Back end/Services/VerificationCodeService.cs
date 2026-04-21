using SNI_Projekat_NET.Models;
using SNI_Projekat_NET.Repositories;

namespace SNI_Projekat_NET.Services
{
    public class VerificationCodeService : IVerificationCodeService
    {
        private readonly IVerificationCodeRepository _repo; 

        public VerificationCodeService(IVerificationCodeRepository repo)
        {
            _repo = repo;
        }

        public async Task<VerificationCode> FindTopByUserOrderByLengthDesc(User user)
        {
            return await _repo.FindTopByUserOrderByLengthDesc(user);
        }
    }
}
