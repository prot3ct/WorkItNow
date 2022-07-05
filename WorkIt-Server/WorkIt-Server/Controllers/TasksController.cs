using System;
using System.Web.Http;
using WorkIt_Server.BLL;
using WorkIt_Server.Models.DTO;

namespace WorkIt_Server.Controllers
{
    [RoutePrefix("api")]
    public class TasksController : ApiController
    {
        private BaseService service = new BaseService();

        [Route("tasks")]
        [HttpPost]
        public IHttpActionResult CreateTask(CreateTaskDTO jobInformation)
        {
            try
            {
                service.CreateTask(jobInformation);
                return Ok();
            }
            catch
            {
                return InternalServerError();
            }
        }

        [Route("tasks/{taskId}")]
        [HttpPut]
        public IHttpActionResult UpdateTask(EditTaskDTO task)
        {
            try
            {
                service.UpdateTask(task);
                return Ok();
            }
            catch (Exception e)
            {
                return InternalServerError(e);
            }
        }

        [Route("tasks/{taskId}")]
        [HttpGet]
        public IHttpActionResult GetTaskDetails(int taskId)
        {
            try
            {
                return Ok(service.GetTaskDetails(taskId));
            }
            catch
            {
                return InternalServerError();
            }
        }

        [Route("tasks/{taskId}")]
        [HttpDelete]
        public IHttpActionResult DeleteTaskById(int taskId)
        {
            try
            {
                service.DeleteTaskById(taskId);
                return Ok();
            }
            catch
            {
                return InternalServerError();
            }
        }

        [Route("users/{userId}/available-tasks/page/{page}")]
        [HttpGet]
        public IHttpActionResult GetAllAvailableTasks(int userId, int page, [FromUri] string search)
        {
            try
            {
                return Ok(service.GetAllAvailableTasks(userId, page, search));
            }
            catch (Exception e)
            {
                return InternalServerError(e);
            }
        }

        [Route("users/{userId}/my-tasks")]
        [HttpGet]
        public IHttpActionResult GetMyTasks(int userId)
        {
            try
            {
                return Ok(service.GetMyTasks(userId));
            }
            catch (Exception)
            {
                return InternalServerError();
            }
        }

        [Route("users/{userId}/assigned-tasks")]
        [HttpGet]
        public IHttpActionResult GetAssignedTasks(int userId)
        {
            try
            {
                return Ok(service.GetAssignedTasks(userId));
            }
            catch (Exception)
            {
                return InternalServerError();
            }
        }

        [Route("users/{userId}/completed-tasks")]
        [HttpGet]
        public IHttpActionResult GetCompletedTasksByUser(int userId)
        {
            try
            {
                return Ok(service.GetCompletedTasksByUser(userId));
            }
            catch (Exception e)
            {
                return InternalServerError(e);
            }
        }

        [Route("tasks/can-assign")]
        [HttpPost]
        public IHttpActionResult PostCanAssignToTask(CanAssignToTaskDTO canAssignToTask)
        {
            try
            {
                return Ok(service.IsUserAssignable(canAssignToTask));
            }
            catch (Exception e)
            {
                return InternalServerError(e);
            }
        }

        [Route("tasks/{taskId}/assigned-user")]
        [HttpPut]
        public IHttpActionResult UpdateAssignedUser(UpdateAssignedUserDTO updateAssignedUserDTO)
        {
            try
            {
                service.UpdateAssignedUser(updateAssignedUserDTO);
                return Ok();
            }
            catch (Exception e)
            {
                return InternalServerError(e);
            }
        }
    }
}
