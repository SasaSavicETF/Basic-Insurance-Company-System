using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using SNI_Projekat_NET.DTOs;
using SNI_Projekat_NET.Models;
using SNI_Projekat_NET.Services;

namespace SNI_Projekat_NET.Controllers
{
    [Route("auth")]
    [ApiController]
    public class AuthController : ControllerBase
    {
        private readonly IAuthService _service; 

        public AuthController(IAuthService service)
        {
            _service = service;
        }

        [HttpPost("login")]
        public async Task<IActionResult> Login([FromBody] LoginRequestDTO loginRequest)
        {
            var token = await _service.Login(loginRequest);
            if (token is null) return BadRequest("Wrong username or password.");

            return Ok(token); 
        }

        [HttpPost("register")]
        public async Task<IActionResult> Register([FromBody] User newUser)
        {
            var user = await _service.Register(newUser);
            if (user is null) return BadRequest("Username is already taken.");

            return Ok(newUser); 
        }

        [Authorize(Roles = "ADMIN")]
        [HttpGet("test")]
        public IActionResult TestMethod()
        {
            return Ok("Test successful."); 
        }
    }
}
