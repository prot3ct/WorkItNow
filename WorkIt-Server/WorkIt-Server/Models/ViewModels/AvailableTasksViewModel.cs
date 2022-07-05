﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace WorkIt_Server.Models.ViewModels
{
    public class AvailableTasksViewModel
    {
        public int TaskId { get; set; }
        public string Title { get; set; }
        public DateTime StartDate { get; set; }
        public string FullName { get; set; }
        public double SupervisorRating { get; set; }
    }
}