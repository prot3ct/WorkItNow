namespace WorkIt_Server.Migrations
{
    using System;
    using System.Data.Entity;
    using System.Data.Entity.Migrations;
    using System.Linq;
    using WorkIt_Server.Models;

    internal sealed class Configuration : DbMigrationsConfiguration<WorkIt_Server.Models.Context.WorkItDbContext>
    {
        public Configuration()
        {
            AutomaticMigrationsEnabled = false;
        }

        protected override void Seed(WorkIt_Server.Models.Context.WorkItDbContext context)
        {
            context.RequestStatuses.AddOrUpdate(
                new RequestStatus { Name = "Pending" },
                new RequestStatus { Name = "Declined" },
                new RequestStatus { Name = "Accepted" }
            );

            context.UserRoles.AddOrUpdate(
                new UserRole { Name = "Tasker" },
                new UserRole { Name = "Supervisor" }
            );


            context.SaveChanges();
        }
    }
}
