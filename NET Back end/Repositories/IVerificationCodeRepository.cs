using SNI_Projekat_NET.Models;

namespace SNI_Projekat_NET.Repositories
{
    public interface IVerificationCodeRepository
    {
        Task<VerificationCode> FindTopByUserOrderByLengthDesc(User user); 
    }
}
