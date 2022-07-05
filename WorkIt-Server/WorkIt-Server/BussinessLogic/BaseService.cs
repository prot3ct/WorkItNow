using System.Collections.Generic;
using WorkIt_Server.BussinessLogic.Logics;
using WorkIt_Server.Models.Context;
using WorkIt_Server.Models.DTO;
using WorkIt_Server.Models.ViewModels;

namespace WorkIt_Server.BLL
{
    public class BaseService
    {
        public BaseService()
        {
            this.WorkItDbContext = new WorkItDbContext();
            this.TaskLogic = new TaskBussinessLogic(this.WorkItDbContext);
            this.AuthLogic = new AuthBussinessLogic(this.WorkItDbContext);
            this.UserLogic = new UserBussinessLogic(this.WorkItDbContext);
            this.TaskRequestLogic = new TaskRequestBussinessLogic(this.WorkItDbContext);
            this.RaitingLogic = new RaitingBussinessLogic(this.WorkItDbContext);
            this.DialogLogic = new DialogBussinessLogic(this.WorkItDbContext);
            this.MessagesLogic = new MessagesBussinessLogic(this.WorkItDbContext);
        }

        public WorkItDbContext WorkItDbContext { get; private set; }

        public TaskBussinessLogic TaskLogic { get; private set; }

        public AuthBussinessLogic AuthLogic { get; private set; }

        public UserBussinessLogic UserLogic { get; private set; }

        public TaskRequestBussinessLogic TaskRequestLogic { get; private set; }

        public RaitingBussinessLogic RaitingLogic { get; private set; }

        public DialogBussinessLogic DialogLogic { get; private set; }
        
        public MessagesBussinessLogic MessagesLogic { get; private set; }

        public LoginViewModel getUserInfo(string email)
        {
            return AuthLogic.getUserInfo(email);
        }

        public bool LoginUser(LoginDTO credentials)
        {
            return AuthLogic.LoginUser(credentials);
        }

        public bool AutoLogin(AutoLoginDTO autoLoginDTO)
        {
            return AuthLogic.AutoLogin(autoLoginDTO);
        }

        public bool RegisterUser(RegisterDTO credentials)
        {
            return AuthLogic.RegisterUser(credentials);
        }

        public void CreateTask(CreateTaskDTO task)
        {
            TaskLogic.CreateTask(task);
        }

        public void UpdateTask(EditTaskDTO task)
        {
            TaskLogic.UpdateTask(task);
        }

        public IEnumerable<AvailableTasksViewModel> GetAllAvailableTasks(int userId, int page, string search)
        {
            return TaskLogic.GetAllAvailableTasks(userId, page, search);
        }

        public IEnumerable<GetMyTasksListViewModel> GetMyTasks(int userId)
        {
            return TaskLogic.GetMyTasks(userId);
        }

        public IEnumerable<AssignedTasksListViewModel> GetAssignedTasks(int userId)
        {
            return TaskLogic.GetAssignedTasks(userId);
        }

        public IEnumerable<CompletedTasksListViewModel> GetCompletedTasksByUser(int userId)
        {
            return TaskLogic.GetCompletedTasksByUser(userId);
        }

        public TaskDetailsViewModel GetTaskDetails(int taskId)
        {
            return TaskLogic.GetTaskDetails(taskId);
        }

        public void DeleteTaskById(int taskId)
        {
            TaskLogic.DeleteTaskById(taskId);
        }

        public void CreateTaskRequest(CreateTaskRequestDTO jobRequest)
        {
            TaskRequestLogic.CreateTaskRequest(jobRequest);
        }

        public void UpdateTaskRequest(UpdateTaskRequestDTO taskRequest)
        {
            TaskRequestLogic.UpdateTaskRequest(taskRequest);
        }

        public IEnumerable<TaskRequestsListViewModel> GetRequestsForTask(int taskId)
        {
            return TaskRequestLogic.GetRequestsForTask(taskId);
        }

        public void DeleteTaskRequestById(int taskId)
        {
            TaskRequestLogic.DeleteTaskRequest(taskId);
        }

        public void CreateRaiting(CreateRaitingDTO raiting)
        {
            this.RaitingLogic.CreateRatiing(raiting);
        }

        public void UpdateProfile(UpdateProfileDTO updatedProfile)
        {
            this.UserLogic.UpdateProfile(updatedProfile);
        }

        public ProfileDetailsViewModel GetProfileDetails(int userId)
        {
            return this.UserLogic.GetProfileDetails(userId);
        }

        public IsUserAssignableToTaskViewModel IsUserAssignable(CanAssignToTaskDTO canAssignToTask)
        {
            return TaskLogic.IsUserAssignable(canAssignToTask);
        }

        public void UpdateAssignedUser(UpdateAssignedUserDTO updateAssignedUserDTO)
        {
            TaskLogic.UpdateAssignedUser(updateAssignedUserDTO);
        }

        public int CreateDialog(CreateDialogDTO createDialogDTO)
        {
            return DialogLogic.CreateDialog(createDialogDTO);
        }

        public IEnumerable<DialogsListViewModel> GetDialogsByUserId(int userId)
        {
            return DialogLogic.GetDialogsByUser(userId);
        }

        public void CreateMessage(CreateMessageDTO createMessageDTO)
        {
            MessagesLogic.CreateMessage(createMessageDTO);
        }

        public IEnumerable<ListMessagesViewModel> GetMessages(int dialogId)
        {
            return MessagesLogic.GetMessages(dialogId);
        }

        //public void CreateTaskReport(TaskReportDTO jobReport)
        //{
        //    TaskReportLogic.CreateTaskReport(jobReport);
        //}

        //public void CreateUserReport(UserReportDTO userReport)
        //{
        //    UserReportLogic.CreateUserReport(userReport);
        //}
    }
}