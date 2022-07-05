using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace WorkIt_Server.Models.ViewModels
{
    public class DialogsListViewModel
    {
        public int DialogId { get; set; }
        public string User1Picture { get; set; }
        public int User1Id { get; set; }
        public string User1Name { get; set; }
        public string User2Name { get; set; }
        public int User2Id { get; set; }
        public string User2Picture { get; set; }
        public string LastMessageText { get; set; }
        public DateTime? LastMessageCreatedAt { get; set; }
    }
}