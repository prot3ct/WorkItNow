
using System.Collections.Generic;

namespace WorkIt_Server.Models.DTO
{
    public class CreateTaskRequestDTO
    {
        public int TaskId { get; set; }
        public int UserId { get; set; }
    }
}