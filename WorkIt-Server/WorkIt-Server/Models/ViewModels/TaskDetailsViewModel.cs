using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace WorkIt_Server.Models.ViewModels
{
    public class TaskDetailsViewModel
    {
        public int TaskId { get; set; }
        public string Title { get; set; }
        public DateTime StartDate { get; set; }
        public int Length { get; set; }
        public string Description { get; set; }
        public string City { get; set; }
        public string Address { get; set; }
        public double Reward { get; set; }
        public string FullName { get; set; }
        public string SupervisorName { get; set; }
        public double SupervisorRating { get; set; }
        public int SupervisorId { get; set; }
    }
}