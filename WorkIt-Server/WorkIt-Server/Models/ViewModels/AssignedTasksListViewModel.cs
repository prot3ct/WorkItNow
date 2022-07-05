using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace WorkIt_Server.Models.ViewModels
{
    public class AssignedTasksListViewModel
    {
        public int TaskId { get; set; }
        public string Title { get; set; }
        public DateTime StartDate { get; set; }
        public string FullName { get; set; }
    }
}