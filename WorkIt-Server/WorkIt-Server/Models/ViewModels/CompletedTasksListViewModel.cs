using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace WorkIt_Server.Models.ViewModels
{
    public class CompletedTasksListViewModel
    {
        public int TaskId { get; set; }
        public string Title { get; set; }
        public DateTime StartDate { get; set; }
        public int SupervisorId { get; set; }
        public string SupervisorFullName { get; set; }
        public string TaskerFullName { get; set; }
        public bool HasSupervisorGivenRating { get; set; }
        public bool HasTaskerGivenrating { get; set; }
        public int TaskerId { get; set; }
    }
}