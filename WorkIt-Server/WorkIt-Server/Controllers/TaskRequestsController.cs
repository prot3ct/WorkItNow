using System;
using System.Web.Http;
using WorkIt_Server.BLL;
using WorkIt_Server.Models.DTO;

namespace WorkIt_Server.Controllers
{
    [RoutePrefix("api")]
    public class TaskRequestsController : ApiController
    {
        private BaseService service = new BaseService();

        [Route("requests")]
        [HttpPost]
        public IHttpActionResult CreateTaskRequest(CreateTaskRequestDTO taskRequest)
        {
            try
            {
                service.CreateTaskRequest(taskRequest);
                return Ok();
            }
            catch (Exception e)
            {
                return InternalServerError(e);
            }
        }

        [Route("requests/{requestId}")]
        [HttpPut]
        public IHttpActionResult UpdateTaskRequest(UpdateTaskRequestDTO taskRequest)
        {
            try
            {
                service.UpdateTaskRequest(taskRequest);
                return Ok();
            }
            catch (Exception e)
            {
                return InternalServerError(e);
            }
        }

        [Route("requests/{requestId}")]
        [HttpDelete]
        public IHttpActionResult DeleteTaskRequestById(int requestId)
        {
            try
            {
                service.DeleteTaskRequestById(requestId);
                return Ok();
            }
            catch (Exception)
            {
                return InternalServerError();
            }
        }

        [Route("tasks/{taskId}/requests")]
        [HttpGet]
        public IHttpActionResult GetRequestsForTask(int taskId)
        {
            try
            {
                return Ok(service.GetRequestsForTask(taskId));
            }
            catch (Exception)
            {
                return InternalServerError();
            }
        }
    }
}
