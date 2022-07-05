using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace WorkIt_Server.Models
{
    public class Task
    {
        [Key]
        public int TaskId { get; set; }

        [Required]
        public string Title { get; set; }

        [Required]
        public DateTime StartDate { get; set; }

        [Required]
        public string Description { get; set; }

        [Required]
        public string City { get; set; }

        [Required]
        public string Address { get; set; }

        [Required]
        public double Reward { get; set; }

        [Required]
        public int Length { get; set; }

        public bool IsCompleted { get; set; }

        public bool HasCreatorGivenRating { get; set; }

        public bool HasTaskerGivenRating { get; set; }

        [Required]
        [ForeignKey("Creator")]
        public int CreatorId { get; set; }
        public virtual User Creator { get; set; }

        [ForeignKey("AssignedUser")]
        public int? AssignedUserId { get; set; }
        public virtual User AssignedUser { get; set; }
    }
}