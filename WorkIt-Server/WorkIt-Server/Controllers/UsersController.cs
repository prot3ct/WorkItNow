using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using WorkIt_Server.BLL;
using WorkIt_Server.Models.DTO;

namespace WorkIt_Server.Controllers
{
    [RoutePrefix("api")]
    public class UsersController : ApiController
    {
        private BaseService service = new BaseService();

        [Route("users/{userId}")]
        [HttpGet]
        public IHttpActionResult GetProfileDetails(int userId)
        {
            try
            {
                return Ok(service.GetProfileDetails(userId));
            }
            catch (Exception e)
            {
                return InternalServerError(e);
            }
        }

        [Route("users/{userId}")]
        [HttpPut]
        public IHttpActionResult UpdateProfile(UpdateProfileDTO updatedProfile)
        {
            try
            {
                service.UpdateProfile(updatedProfile);
                return Ok();
            }
            catch (Exception e)
            {
                return InternalServerError(e);
            }
        }
    }
}
