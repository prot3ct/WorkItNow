using System.ComponentModel.DataAnnotations;

namespace WorkIt_Server.Models
{
    public class UserRole
    {
        [Key]
        public int UserRoleId { get; set; }

        [Required]
        public string Name { get; set; }
    }
}