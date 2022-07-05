using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Web;

namespace WorkIt_Server.Models
{
    public class Dialog
    {
        [Key]
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public int DialogId { get; set; }

        public string LastMessageText { get; set; }

        public DateTime? LastMessageCreatedAt { get; set; }

        [Required]
        [ForeignKey("User1")]
        public int User1Id { get; set; }
        public virtual User User1 { get; set; }

        [Required]
        [ForeignKey("User2")]
        public int User2Id { get; set; }
        public virtual User User2 { get; set; }
    }
}