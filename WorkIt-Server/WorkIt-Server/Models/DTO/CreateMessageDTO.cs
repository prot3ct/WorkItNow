using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace WorkIt_Server.Models.DTO
{
    public class CreateMessageDTO
    {
        public string Text { get; set; }
        public int AuthorId { get; set; }
        public int DialogId { get; set; }
        public DateTime CreatedAt { get; set; }
    }
}