using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace WorkIt_Server.Models.DTO
{
    public class CanAssignToTaskDTO
    {
        public int UserId { get; set; }
        public int TaskId { get; set; }
    }
}