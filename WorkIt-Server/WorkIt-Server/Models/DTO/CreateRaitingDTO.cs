namespace WorkIt_Server.Models.DTO
{
    public class CreateRaitingDTO
    {
        public int ReceiverUserId { get; set; }

        public int TaskId { get; set; }

        public int ReceiverUserRoleId { get; set; }

        public string Description { get; set; }

        public int Value { get; set; }
    }
}