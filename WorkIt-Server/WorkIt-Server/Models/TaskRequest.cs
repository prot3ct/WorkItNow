using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace WorkIt_Server.Models
{
    public class TaskRequest
    {
        [Key]
        public int TaskRequestId { get; set; }

        [Required]
        [ForeignKey("User")]
        public int UserId { get; set; }
        public virtual User User { get; set; }

        [Required]
        [ForeignKey("Task")]
        public int TaskId { get; set; }
        public virtual Task Task { get; set; }

        [Required]
        [ForeignKey("RequestStatus")]
        public int RequestStatusId { get; set; }
        public virtual RequestStatus RequestStatus { get; set; }
    }
}