namespace WorkIt_Server.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class Initial : DbMigration
    {
        public override void Up()
        {
            CreateTable(
                "dbo.Comments",
                c => new
                    {
                        CommentId = c.Int(nullable: false, identity: true),
                        Body = c.String(nullable: false),
                        UserId = c.Int(nullable: false),
                    })
                .PrimaryKey(t => t.CommentId)
                .ForeignKey("dbo.Users", t => t.UserId, cascadeDelete: true)
                .Index(t => t.UserId);
            
            CreateTable(
                "dbo.Users",
                c => new
                    {
                        UserId = c.Int(nullable: false, identity: true),
                        Email = c.String(nullable: false),
                        PassHash = c.String(nullable: false),
                        Firstname = c.String(nullable: false),
                        Lastname = c.String(nullable: false),
                        RaitingAsEmployee = c.Double(nullable: false),
                        RaitingAsCreator = c.Double(nullable: false),
                        TaskCompleted = c.Int(nullable: false),
                    })
                .PrimaryKey(t => t.UserId);
            
            CreateTable(
                "dbo.Locations",
                c => new
                    {
                        LocationId = c.Int(nullable: false, identity: true),
                        Country = c.String(nullable: false, maxLength: 50),
                        City = c.String(nullable: false, maxLength: 50),
                        Address = c.String(nullable: false, maxLength: 50),
                    })
                .PrimaryKey(t => t.LocationId)
                .Index(t => new { t.Country, t.City, t.Address }, unique: true, name: "IX_CountryCityAndAddress");
            
            CreateTable(
                "dbo.Raitings",
                c => new
                    {
                        RaitingId = c.Int(nullable: false, identity: true),
                        GiverUserId = c.Int(nullable: false),
                        ReceiverUserId = c.Int(nullable: false),
                        TaskId = c.Int(nullable: false),
                        ReceiverUserRoleId = c.Int(nullable: false),
                        Value = c.Int(nullable: false),
                    })
                .PrimaryKey(t => t.RaitingId)
                .ForeignKey("dbo.Users", t => t.GiverUserId, cascadeDelete: true)
                .ForeignKey("dbo.Users", t => t.ReceiverUserId, cascadeDelete: false)
                .ForeignKey("dbo.UserRoles", t => t.ReceiverUserRoleId, cascadeDelete: true)
                .ForeignKey("dbo.Tasks", t => t.TaskId, cascadeDelete: true)
                .Index(t => t.GiverUserId)
                .Index(t => t.ReceiverUserId)
                .Index(t => t.TaskId)
                .Index(t => t.ReceiverUserRoleId);
            
            CreateTable(
                "dbo.UserRoles",
                c => new
                    {
                        UserRoleId = c.Int(nullable: false, identity: true),
                        Name = c.String(nullable: false),
                    })
                .PrimaryKey(t => t.UserRoleId);
            
            CreateTable(
                "dbo.Tasks",
                c => new
                    {
                        TaskId = c.Int(nullable: false, identity: true),
                        Title = c.String(nullable: false),
                        StartDate = c.String(nullable: false),
                        EndDate = c.String(nullable: false),
                        Description = c.String(nullable: false),
                        Reward = c.String(nullable: false),
                        MinRaiting = c.Int(nullable: false),
                        MinTasksCompleted = c.Int(nullable: false),
                        IsCompleted = c.Boolean(nullable: true),
                        HasCreatorGivenRating = c.Boolean(nullable: true),
                        HasTaskterGivenRating = c.Boolean(nullable: true),
                        LocationId = c.Int(nullable: false),
                        CreatorId = c.Int(nullable: false),
                        AssignedUserId = c.Int(nullable: true),
                    })
                .PrimaryKey(t => t.TaskId)
                .ForeignKey("dbo.Users", t => t.AssignedUserId, cascadeDelete: false)
                .ForeignKey("dbo.Users", t => t.CreatorId, cascadeDelete: false)
                .ForeignKey("dbo.Locations", t => t.LocationId, cascadeDelete: true)
                .Index(t => t.LocationId)
                .Index(t => t.CreatorId)
                .Index(t => t.AssignedUserId);
            
            CreateTable(
                "dbo.RequestStatus",
                c => new
                    {
                        RequestStatusId = c.Int(nullable: false, identity: true),
                        Name = c.String(nullable: false),
                    })
                .PrimaryKey(t => t.RequestStatusId);
            
            CreateTable(
                "dbo.TaskRequests",
                c => new
                    {
                        TaskRequestId = c.Int(nullable: false, identity: true),
                        Description = c.String(nullable: false),
                        UserId = c.Int(nullable: false),
                        TaskId = c.Int(nullable: false),
                        RequestStatusId = c.Int(nullable: false),
                    })
                .PrimaryKey(t => t.TaskRequestId)
                .ForeignKey("dbo.RequestStatus", t => t.RequestStatusId, cascadeDelete: true)
                .ForeignKey("dbo.Tasks", t => t.TaskId, cascadeDelete: true)
                .ForeignKey("dbo.Users", t => t.UserId, cascadeDelete: true)
                .Index(t => t.UserId)
                .Index(t => t.TaskId)
                .Index(t => t.RequestStatusId);
            
            CreateTable(
                "dbo.TaskComments",
                c => new
                    {
                        TaskCommentsId = c.Int(nullable: false, identity: true),
                        TaskId = c.Int(nullable: true),
                        TaskRequestId = c.Int(nullable: true),
                        CommentId = c.Int(nullable: false),
                    })
                .PrimaryKey(t => t.TaskCommentsId)
                .ForeignKey("dbo.Comments", t => t.CommentId)
                .ForeignKey("dbo.Tasks", t => t.TaskId)
                .ForeignKey("dbo.Tasks", t => t.TaskRequestId)
                .Index(t => t.TaskId)
                .Index(t => t.TaskRequestId)
                .Index(t => t.CommentId);
            
            CreateTable(
                "dbo.TaskReports",
                c => new
                    {
                        TaskReportId = c.Int(nullable: false, identity: true),
                        Description = c.String(nullable: false),
                        TaskId = c.Int(nullable: false),
                        UserId = c.Int(nullable: false),
                    })
                .PrimaryKey(t => t.TaskReportId)
                .ForeignKey("dbo.Tasks", t => t.TaskId, cascadeDelete: true)
                .ForeignKey("dbo.Users", t => t.UserId, cascadeDelete: true)
                .Index(t => t.TaskId)
                .Index(t => t.UserId);
            
            CreateTable(
                "dbo.UserReports",
                c => new
                    {
                        UserReportId = c.Int(nullable: false, identity: true),
                        Description = c.String(nullable: false),
                        AuthorUserId = c.Int(nullable: false),
                        TargetUserId = c.Int(nullable: false),
                    })
                .PrimaryKey(t => t.UserReportId)
                .ForeignKey("dbo.Users", t => t.AuthorUserId, cascadeDelete: true)
                .ForeignKey("dbo.Users", t => t.TargetUserId, cascadeDelete: false)
                .Index(t => t.AuthorUserId)
                .Index(t => t.TargetUserId);
            
        }
        
        public override void Down()
        {
            DropForeignKey("dbo.UserReports", "TargetUserId", "dbo.Users");
            DropForeignKey("dbo.UserReports", "AuthorUserId", "dbo.Users");
            DropForeignKey("dbo.TaskReports", "UserId", "dbo.Users");
            DropForeignKey("dbo.TaskReports", "TaskId", "dbo.Tasks");
            DropForeignKey("dbo.TaskComments", "TaskRequestId", "dbo.Tasks");
            DropForeignKey("dbo.TaskComments", "TaskId", "dbo.Tasks");
            DropForeignKey("dbo.TaskComments", "CommentId", "dbo.Comments");
            DropForeignKey("dbo.TaskRequests", "UserId", "dbo.Users");
            DropForeignKey("dbo.TaskRequests", "TaskId", "dbo.Tasks");
            DropForeignKey("dbo.TaskRequests", "RequestStatusId", "dbo.RequestStatus");
            DropForeignKey("dbo.Raitings", "TaskId", "dbo.Tasks");
            DropForeignKey("dbo.Tasks", "LocationId", "dbo.Locations");
            DropForeignKey("dbo.Tasks", "CreatorId", "dbo.Users");
            DropForeignKey("dbo.Tasks", "AssignedUserId", "dbo.Users");
            DropForeignKey("dbo.Raitings", "ReceiverUserRoleId", "dbo.UserRoles");
            DropForeignKey("dbo.Raitings", "ReceiverUserId", "dbo.Users");
            DropForeignKey("dbo.Raitings", "GiverUserId", "dbo.Users");
            DropForeignKey("dbo.Comments", "UserId", "dbo.Users");
            DropIndex("dbo.UserReports", new[] { "TargetUserId" });
            DropIndex("dbo.UserReports", new[] { "AuthorUserId" });
            DropIndex("dbo.TaskReports", new[] { "UserId" });
            DropIndex("dbo.TaskReports", new[] { "TaskId" });
            DropIndex("dbo.TaskComments", new[] { "CommentId" });
            DropIndex("dbo.TaskComments", new[] { "TaskRequestId" });
            DropIndex("dbo.TaskComments", new[] { "TaskId" });
            DropIndex("dbo.TaskRequests", new[] { "RequestStatusId" });
            DropIndex("dbo.TaskRequests", new[] { "TaskId" });
            DropIndex("dbo.TaskRequests", new[] { "UserId" });
            DropIndex("dbo.Tasks", new[] { "AssignedUserId" });
            DropIndex("dbo.Tasks", new[] { "CreatorId" });
            DropIndex("dbo.Tasks", new[] { "LocationId" });
            DropIndex("dbo.Raitings", new[] { "ReceiverUserRoleId" });
            DropIndex("dbo.Raitings", new[] { "TaskId" });
            DropIndex("dbo.Raitings", new[] { "ReceiverUserId" });
            DropIndex("dbo.Raitings", new[] { "GiverUserId" });
            DropIndex("dbo.Locations", "IX_CountryCityAndAddress");
            DropIndex("dbo.Comments", new[] { "UserId" });
            DropTable("dbo.UserReports");
            DropTable("dbo.TaskReports");
            DropTable("dbo.TaskComments");
            DropTable("dbo.TaskRequests");
            DropTable("dbo.RequestStatus");
            DropTable("dbo.Tasks");
            DropTable("dbo.UserRoles");
            DropTable("dbo.Raitings");
            DropTable("dbo.Locations");
            DropTable("dbo.Users");
            DropTable("dbo.Comments");
        }
    }
}
