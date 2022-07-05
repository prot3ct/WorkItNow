using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Web;

namespace WorkIt_Server.Models
{
    public class Message
    {
        [Key]
        public int MessageId { get; set; }

        [Required]
        public string Text { get; set; }

        [Required]
        public DateTime CreatedAt { get; set; }

        [Required]
        [ForeignKey("Author")]
        public int AuthorId { get; set; }
        public virtual User Author { get; set; }

        [Required]
        [ForeignKey("Dialog")]
        public int DialogId { get; set; }
        public virtual Dialog Dialog { get; set; }
    }
}