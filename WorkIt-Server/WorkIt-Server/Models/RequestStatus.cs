using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace WorkIt_Server.Models
{
    public class RequestStatus
    {
        [Key]
        public int RequestStatusId { get; set; }

        [Required]
        public string Name { get; set; }
    }
}