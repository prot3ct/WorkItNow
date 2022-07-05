using System;
using System.Web.Http;
using WorkIt_Server.BLL;
using WorkIt_Server.Models.DTO;

namespace WorkIt_Server.Controllers
{
    [RoutePrefix("api")]
    public class AuthController : ApiController
    {
        private BaseService service = new BaseService();

        [Route("auth/login")]
        [HttpPost]
        public IHttpActionResult Login(LoginDTO credentials)
        {
            try
            {
                if (service.LoginUser(credentials))
                {
                    return Ok(service.getUserInfo(credentials.Email));
                }
                return NotFound();
            }
            catch (Exception)
            {
                return InternalServerError();
            }
        }

        [Route("auth/register")]
        [HttpPost]
        public IHttpActionResult Register(RegisterDTO credentials)
        {
            try
            {
                if (service.RegisterUser(credentials))
                {
                    return Ok();
                }
                return NotFound();
            }
            catch (Exception)
            {
                return InternalServerError();
            }
        }

        [Route("auth/autologin")]
        [HttpPost]
        public IHttpActionResult AutoLogin(AutoLoginDTO autoLoginDTO)
        {
            try
            {
                return Ok(service.AutoLogin(autoLoginDTO));
            }
            catch (Exception)
            {
                return InternalServerError();
            }
        }
    }
}
