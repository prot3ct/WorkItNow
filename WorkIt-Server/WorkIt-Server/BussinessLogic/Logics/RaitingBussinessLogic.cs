using System.Collections.Generic;
using System.Linq;
using WorkIt_Server.Models;
using WorkIt_Server.Models.Context;
using WorkIt_Server.Models.DTO;

namespace WorkIt_Server.BussinessLogic.Logics
{
    public class RaitingBussinessLogic
    {
        private WorkItDbContext db;

        public RaitingBussinessLogic(WorkItDbContext db)
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

        public void CreateRatiing(CreateRaitingDTO raiting)
        {
            var user = db.Users.FirstOrDefault(u => u.UserId == raiting.ReceiverUserId);
            var task = db.Tasks.FirstOrDefault(t => t.TaskId == raiting.TaskId);

            var raitingToBeInserted = new Raiting
            {
                Value = raiting.Value,
                Description = raiting.Description,
                ReceiverUserId = raiting.ReceiverUserId,
                TaskId = raiting.TaskId,
                ReceiverUserRoleId = raiting.ReceiverUserRoleId
            };

            if (raiting.ReceiverUserRoleId == 3)
            {
                var currentRait = user.RaitingAsTasker * user.ReviewsAsTasker;
                currentRait += raiting.Value;
                currentRait /= (user.ReviewsAsTasker + 1);
                user.RaitingAsTasker = currentRait;
                task.HasCreatorGivenRating = true;
            }
            else if (raiting.ReceiverUserRoleId == 4)
            {
                var currentRait = user.RaitingAsSupervisor * user.ReviewsAsSupervisor;
                currentRait += raiting.Value;
                currentRait /= (user.ReviewsAsSupervisor + 1);
                user.RaitingAsSupervisor = currentRait;
                task.HasTaskerGivenRating = true;
            }

            db.Raitings.Add(raitingToBeInserted);
            db.SaveChanges();
        }
    }
}