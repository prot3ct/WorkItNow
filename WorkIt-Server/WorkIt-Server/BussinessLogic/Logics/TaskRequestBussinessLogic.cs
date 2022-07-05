using System;
using System.Collections.Generic;
using System.Linq;
using WorkIt_Server.Models;
using WorkIt_Server.Models.Context;
using WorkIt_Server.Models.DTO;
using WorkIt_Server.Models.ViewModels;

namespace WorkIt_Server.BLL
{
    public class TaskRequestBussinessLogic
    {
        private WorkItDbContext db;

        public TaskRequestBussinessLogic(WorkItDbContext db)
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

        public void CreateTaskRequest(CreateTaskRequestDTO taskRequest)
        {
            var taskRequestToBeInserted = new TaskRequest
            {
                TaskId = taskRequest.TaskId,
                UserId = taskRequest.UserId,
                RequestStatusId = 1
            };

            Db.TaskRequests.Add(taskRequestToBeInserted);
            Db.SaveChanges();
        }

        public void UpdateTaskRequest(UpdateTaskRequestDTO taskRequest)
        {
            var updatedTaskRequest = db.TaskRequests.FirstOrDefault(tr => tr.TaskRequestId == taskRequest.TaskRequestId);
            updatedTaskRequest.RequestStatusId = taskRequest.RequestStatusId;

            if (updatedTaskRequest.RequestStatusId == 3)
            {
                var updatedTask = updatedTaskRequest.Task;
                updatedTask.AssignedUserId = updatedTaskRequest.User.UserId;

                var taskRequestsForTheSameTask = db.TaskRequests.Where(tr => tr.TaskId == updatedTaskRequest.TaskId && tr.TaskRequestId != updatedTaskRequest.TaskRequestId).ToList();
                taskRequestsForTheSameTask.ForEach(tr => tr.RequestStatusId = 2);
            }

            db.SaveChanges();
        }

        public IEnumerable<TaskRequestsListViewModel> GetRequestsForTask(int taskId)
        {
            return Db.TaskRequests
                .Where(tr => tr.TaskId == taskId && tr.RequestStatus.Name == "Pending")
                .Select(tr => new TaskRequestsListViewModel
                {
                    TaskRequestId = tr.TaskRequestId,
                    FullName = tr.User.FullName,
                    ProfilePictureAsString = tr.User.Picture,
                    RequesterId = tr.UserId
                })
                .ToList();
        }

        public void DeleteTaskRequest(int taskRequestId)
        {
            var jobRequest = Db.TaskRequests.FirstOrDefault(tr => tr.TaskRequestId == taskRequestId);

            Db.TaskRequests.Remove(jobRequest);
            Db.SaveChanges();
        }
    }
}