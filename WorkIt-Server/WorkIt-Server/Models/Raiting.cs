using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
namespace WorkIt_Server.Models
{
    public class Raiting
    {
        [Key]
        public int RaitingId { get; set; }

        public string Description { get; set; }

        [Required]
        [ForeignKey("ReceiverUser")]
        public int ReceiverUserId { get; set; }
        public virtual User ReceiverUser { get; set; }

        [Required]
        [ForeignKey("Task")]
        public int TaskId { get; set; }
        public virtual Task Task { get; set; }

        [Required]
        [ForeignKey("ReceiverUserRole")]
        public int ReceiverUserRoleId { get; set; }
        public virtual UserRole ReceiverUserRole { get; set; }

        [Required]
        public int Value { get; set; }
    }
}