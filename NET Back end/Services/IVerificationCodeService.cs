using SNI_Projekat_NET.Models;

namespace SNI_Projekat_NET.Services
{
    public interface IVerificationCodeService
    {
        Task<VerificationCode> FindTopByUserOrderByLengthDesc(User user);
    }
}
