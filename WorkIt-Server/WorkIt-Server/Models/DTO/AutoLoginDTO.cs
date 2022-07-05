using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace WorkIt_Server.Models.DTO
{
    public class AutoLoginDTO
    {
        public int UserId { get; set; }
        public string AuthToken { get; set; }
    }
}