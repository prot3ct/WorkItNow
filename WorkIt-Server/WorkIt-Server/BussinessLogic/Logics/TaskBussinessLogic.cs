using System;
using System.Collections.Generic;
using System.Data.Entity.Core.Objects;
using System.Data.Entity;
using System.Globalization;
using System.Linq;
using WorkIt_Server.Models;
using WorkIt_Server.Models.Context;
using WorkIt_Server.Models.DTO;
using WorkIt_Server.Models.ViewModels;

namespace WorkIt_Server.BLL
{
    public class TaskBussinessLogic
    {
        private WorkItDbContext db;

        public TaskBussinessLogic(WorkItDbContext db)
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

        public void CreateTask(CreateTaskDTO taskInfo)
        {
            var creator = Db.Users.Where(u => u.Email == taskInfo.CreatorEmail).FirstOrDefault();
            var jobToBeInserted = new Task
            {
                Reward = taskInfo.Reward,
                CreatorId = creator.UserId,
                Description = taskInfo.Description,
                StartDate = taskInfo.StartDate,
                Length = taskInfo.Length,
                Title = taskInfo.Title,
                Address = taskInfo.Address,
                City = taskInfo.City,
                IsCompleted = false,
                HasCreatorGivenRating = false,
                HasTaskerGivenRating = false,
                AssignedUserId = null
            };


            Db.Tasks.Add(jobToBeInserted);
            Db.SaveChanges();
        }

        public void UpdateTask(EditTaskDTO taskInfo)
        {
            var updatedTask = db.Tasks.FirstOrDefault(t => t.TaskId == taskInfo.Id);

            updatedTask.Title = taskInfo.Title;
            updatedTask.StartDate = taskInfo.StartDate;
            updatedTask.Reward = taskInfo.Reward;
            updatedTask.Length = taskInfo.Length;
            updatedTask.Description = taskInfo.Description;
            updatedTask.City = taskInfo.City;
            updatedTask.Address = taskInfo.Address;

            Db.SaveChanges();
        }

        public TaskDetailsViewModel GetTaskDetails(int taskId)
        {
            var task = Db.Tasks.FirstOrDefault(t => t.TaskId == taskId);

            return new TaskDetailsViewModel
            {
                TaskId = task.TaskId,
                Address = task.Address,
                City = task.City,
                Title = task.Title,
                Description = task.Description,
                Length = task.Length,
                StartDate = task.StartDate,
                Reward = task.Reward,
                SupervisorName = task.Creator.FullName,
                SupervisorRating = task.Creator.RaitingAsSupervisor,
                SupervisorId = task.CreatorId
            };
        }

        public void DeleteTaskById(int taskId)
        {
            var taskToBeDeleted = Db.Tasks.FirstOrDefault(t => t.TaskId == taskId);
            Db.Tasks.Remove(taskToBeDeleted);
            Db.SaveChanges();
        }

        public IEnumerable<AvailableTasksViewModel> GetAllAvailableTasks(int userId, int page, string search)
        {
            if (search == null)
            {
                search = "";
            }

            var today = DateTime.Now;

            return Db.Tasks
                .Where(t => t.StartDate > today && t.CreatorId != userId)
                .Where(t => t.Title.Contains(search))
                .OrderBy(t => t.StartDate)
                .Select(j => new AvailableTasksViewModel
                {
                    TaskId = j.TaskId,
                    Title = j.Title,
                    StartDate = j.StartDate,
                    FullName = j.Creator.FullName,
                    SupervisorRating = j.Creator.RaitingAsSupervisor
                })
                .Skip((page - 1) * 6)
                .Take(6)
                .ToList();
        }

        public IEnumerable<AssignedTasksListViewModel> GetAssignedTasks(int userId)
        {
            return Db.Tasks
                .Where(t => t.AssignedUserId == userId)
                .Where(t => t.StartDate > DateTime.Now)
                .OrderBy(t => t.StartDate)
                .Select(t => new AssignedTasksListViewModel
                {
                    TaskId = t.TaskId,
                    Title = t.Title,
                    StartDate = t.StartDate,
                    FullName = t.Creator.FullName
                })
                .ToList();
        }

        public IEnumerable<CompletedTasksListViewModel> GetCompletedTasksByUser(int userId)
        {
            return Db.Tasks
                .Where(t => (t.AssignedUserId == userId || t.CreatorId == userId) && t.AssignedUserId != null)
                .Where(t => t.StartDate < DateTime.Now)
                .OrderByDescending(t => t.StartDate)
                .Select(t => new CompletedTasksListViewModel
                {
                    TaskId = t.TaskId,
                    Title = t.Title,
                    StartDate = t.StartDate,
                    SupervisorFullName = t.Creator.FullName,
                    SupervisorId = t.Creator.UserId,
                    TaskerFullName = t.AssignedUser.FullName,
                    TaskerId = t.AssignedUser.UserId,
                    HasSupervisorGivenRating = t.HasCreatorGivenRating,
                    HasTaskerGivenrating = t.HasTaskerGivenRating
                })
                .ToList();
        }

        public IEnumerable<GetMyTasksListViewModel> GetMyTasks(int userId)
        {
            return Db.Tasks
                .Where(t => t.Creator.UserId == userId)
                .Where(t => t.StartDate > DateTime.Now)
                .OrderBy(t => t.StartDate)
                .Select(t => new GetMyTasksListViewModel
                {
                    TaskId = t.TaskId,
                    Title = t.Title,
                    StartDate = t.StartDate,
                    HasPendingRequests = db.TaskRequests.Any(tr => tr.TaskId == t.TaskId && tr.RequestStatus.Name == "Pending")
                })
                .ToList();
        }

        public IsUserAssignableToTaskViewModel IsUserAssignable(CanAssignToTaskDTO canAssignToTask)
        {
            var pendingRequest = Db.TaskRequests
                .FirstOrDefault(tr => tr.TaskId == canAssignToTask.TaskId &&
                tr.UserId == canAssignToTask.UserId &&
                tr.RequestStatus.Name == "Pending");

            var assignedUser = Db.Tasks
                .Where(t => t.TaskId == canAssignToTask.TaskId)
                .Any(t => t.AssignedUserId == canAssignToTask.UserId);

            var result = new IsUserAssignableToTaskViewModel();

            if (pendingRequest != null)
            {
                result.PendingRequestId = pendingRequest.TaskRequestId;
                result.IsUserAssignableToTaskMessage = "Cancel pending request";
            }
            else if (assignedUser)
            {
                result.IsUserAssignableToTaskMessage = "I can't do this task anymore";
            }
            else
            {
                result.IsUserAssignableToTaskMessage = "No requests";
            }

            return result;
        }

        public void UpdateAssignedUser(UpdateAssignedUserDTO updateAssignedUserDTO)
        {
            var taskToBeUpdated = Db.Tasks.FirstOrDefault(t => t.TaskId == updateAssignedUserDTO.TaskId);

            taskToBeUpdated.AssignedUserId = null;

            db.SaveChanges();
        }
    }
}