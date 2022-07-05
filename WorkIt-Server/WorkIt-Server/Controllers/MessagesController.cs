using System;
using System.Web.Http;
using WorkIt_Server.BLL;
using WorkIt_Server.Models.DTO;

namespace WorkIt_Server.Controllers
{
    [RoutePrefix("api")]
    public class MessageController : ApiController
    {
        private BaseService service = new BaseService();

        [Route("dialogs/{dialogId}/messages")]
        [HttpPost]
        public IHttpActionResult CreateMessage(CreateMessageDTO createMessageDTO)
        {
            try
            {
                service.CreateMessage(createMessageDTO);
                return Ok();
            }
            catch (Exception)
            {
                return InternalServerError();
            }
        }

        [Route("dialogs/{dialogId}/messages")]
        [HttpGet]
        public IHttpActionResult GetMessages(int dialogId)
        {
            try
            {
                return Ok(service.GetMessages(dialogId));
            }
            catch (Exception)
            {
                return InternalServerError();
            }
        }
    }
}
