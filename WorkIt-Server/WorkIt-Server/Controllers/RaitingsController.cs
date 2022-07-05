using System;
using System.Web.Http;
using WorkIt_Server.BLL;
using WorkIt_Server.Models.DTO;

namespace WorkIt_Server.Controllers
{
    [RoutePrefix("api")]
    public class RaitingsController : ApiController
    {
        private BaseService service = new BaseService();

        [Route("raitings")]
        [HttpPost]
        public IHttpActionResult CreateRaiting(CreateRaitingDTO raiting)
        {
            try
            {
                service.CreateRaiting(raiting);
                return Ok();
            }
            catch (Exception)
            {
                return InternalServerError();
            }
        }
    }
}
