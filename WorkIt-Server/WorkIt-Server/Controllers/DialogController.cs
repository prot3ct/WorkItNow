using System;
using System.Web.Http;
using WorkIt_Server.BLL;
using WorkIt_Server.Models.DTO;

namespace WorkIt_Server.Controllers
{
    [RoutePrefix("api")]
    public class DialogController : ApiController
    {
        private BaseService service = new BaseService();

        [Route("dialogs")]
        [HttpPost]
        public IHttpActionResult CreateDialog(CreateDialogDTO createDialogDTO)
        {
            try
            {
                return Ok(service.CreateDialog(createDialogDTO));
            }
            catch (Exception e)
            {
                return InternalServerError(e);
            }
        }

        [Route("users/{userId}/dialogs")]
        [HttpGet]
        public IHttpActionResult GetDialogsByUserId(int userId)
        {
            try
            {
                return Ok(service.GetDialogsByUserId(userId));
            }
            catch (Exception)
            {
                return InternalServerError();
            }
        }
    }
}
