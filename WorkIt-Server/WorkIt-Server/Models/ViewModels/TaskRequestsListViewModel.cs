using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace WorkIt_Server.Models.ViewModels
{
    public class TaskRequestsListViewModel
    {
        public int TaskRequestId { get; set; }
        public int RequesterId { get; set; }
        public string FullName { get; set; }
        public string ProfilePictureAsString { get; set; }
    }
}