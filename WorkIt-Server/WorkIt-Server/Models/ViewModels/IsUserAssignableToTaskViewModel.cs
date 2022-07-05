using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace WorkIt_Server.Models.ViewModels
{
    public class IsUserAssignableToTaskViewModel
    {
        public int PendingRequestId { get; set; }
        public string IsUserAssignableToTaskMessage { get; set; }
    }
}