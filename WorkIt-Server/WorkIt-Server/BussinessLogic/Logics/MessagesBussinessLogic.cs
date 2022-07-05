using System;
using System.Collections.Generic;
using System.Linq;
using WorkIt_Server.Models;
using WorkIt_Server.Models.Context;
using WorkIt_Server.Models.DTO;
using WorkIt_Server.Models.ViewModels;

namespace WorkIt_Server.BLL
{
    public class MessagesBussinessLogic
    {
        private WorkItDbContext db;

        public MessagesBussinessLogic(WorkItDbContext db)
        {
            this.Db = db;
        }

        public WorkItDbContext Db
        {
            get
            {
                return this.db;
            }
            set
            {
                this.db = value;
            }
        }

        public object Datetime { get; private set; }

        public MessagesBussinessLogic() { }

        public void CreateMessage(CreateMessageDTO createMessageDTO)
        {
            var updatedDialog = db.Dialogs.FirstOrDefault(d => d.DialogId == createMessageDTO.DialogId);

            updatedDialog.LastMessageText = createMessageDTO.Text;
            updatedDialog.LastMessageCreatedAt = createMessageDTO.CreatedAt;

            var messageToBeAdded = new Message
            {
                CreatedAt = createMessageDTO.CreatedAt,
                AuthorId = createMessageDTO.AuthorId,
                DialogId = createMessageDTO.DialogId,
                Text = createMessageDTO.Text
            };

            Db.Messages.Add(messageToBeAdded);
            Db.SaveChanges();
        }

        public IEnumerable<ListMessagesViewModel> GetMessages(int dialogId)
        {
            return Db.Messages
                .Where(m => m.DialogId == dialogId)
                .Select(m => new ListMessagesViewModel
                {
                    AuthorId = m.AuthorId,
                    CreatedAt = m.CreatedAt,
                    AuthorName = m.Author.FullName,
                    Text = m.Text
                })
                .ToList();
        }
    }
}