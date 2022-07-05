using System.Collections.Generic;
using System.Linq;
using WorkIt_Server.Models;
using WorkIt_Server.Models.Context;
using WorkIt_Server.Models.DTO;
using WorkIt_Server.Models.ViewModels;

namespace WorkIt_Server.BLL
{
    public class DialogBussinessLogic
    {
        private WorkItDbContext db;

        public DialogBussinessLogic(WorkItDbContext db)
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

        public DialogBussinessLogic() { }

        public int CreateDialog(CreateDialogDTO createDialogDTO)
        {
            var dialogToBeAdded = Db.Dialogs.FirstOrDefault(d => (d.User1Id == createDialogDTO.User1Id || d.User1Id == createDialogDTO.User2Id) &&
            (d.User2Id == createDialogDTO.User1Id || d.User2Id == createDialogDTO.User2Id));

            if (dialogToBeAdded == null)
            {
                dialogToBeAdded = new Dialog
                {
                    User1Id = createDialogDTO.User1Id,
                    User2Id = createDialogDTO.User2Id,
                    LastMessageText = null,
                    LastMessageCreatedAt = null
                };

                Db.Dialogs.Add(dialogToBeAdded);
                Db.SaveChanges();
            }

            return dialogToBeAdded.DialogId;
        }

        public IEnumerable<DialogsListViewModel> GetDialogsByUser(int userId)
        {
            return Db.Dialogs
                .Where(d => d.User1Id == userId || d.User2Id == userId)
                .OrderBy(d => d.LastMessageCreatedAt)
                .Select(d => new DialogsListViewModel
                {
                    DialogId = d.DialogId,
                    LastMessageCreatedAt = d.LastMessageCreatedAt,
                    LastMessageText = d.LastMessageText,
                    User1Name = d.User1.FullName,
                    User1Id = d.User1Id,
                    User1Picture = d.User1.Picture,
                    User2Picture = d.User2.Picture,
                    User2Name = d.User2.FullName,
                    User2Id = d.User2Id
                });
        }
    }
}