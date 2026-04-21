using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using SNI_Projekat_NET.Services;

namespace SNI_Projekat_NET.Controllers
{
    [Route("policy")]
    [ApiController]
    public class PolicyController : ControllerBase
    {
        private readonly IPolicyService _service;

        public PolicyController(IPolicyService service)
        {
            _service = service;
        }

        [HttpGet("unbought/{name}")]
        public async Task<IActionResult> GetUnboughtPoliciesByUser([FromRoute] string username)
        {
            return Ok(await _service.FindUnboughtPoliciesByUser(username));
        }

        [HttpPut("deactivate/{id}")]
        public async Task<IActionResult> DeactivatePolicyById([FromRoute] int id)
        {
            await _service.DeactivatePolicyById(id);
            return NoContent(); 
        }
    }
}
