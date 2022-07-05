using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace WorkIt_Server.Models.ViewModels
{
    public class ProfileDetailsViewModel
    {
        public int UserId { get; set; }
        public string Email { get; set; }
        public string FullName { get; set; }
        public string Phone { get; set; }
        public double RatingAsTasker { get; set; }
        public double RatingAsSupervisor { get; set; }
        public int NumberOfReviewsAsTasker { get; set; }
        public int NumberOfReviewsAsSupervisor { get; set; }
        public string PictureAsString { get; set; }
    }
}