namespace WorkIt_Server.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class RemoveReportingModel : DbMigration
    {
        public override void Up()
        {
            DropForeignKey("dbo.TaskReports", "TaskId", "dbo.Tasks");
            DropForeignKey("dbo.TaskReports", "UserId", "dbo.Users");
            DropForeignKey("dbo.UserReports", "AuthorUserId", "dbo.Users");
            DropForeignKey("dbo.UserReports", "TargetUserId", "dbo.Users");
            DropIndex("dbo.TaskReports", new[] { "TaskId" });
            DropIndex("dbo.TaskReports", new[] { "UserId" });
            DropIndex("dbo.UserReports", new[] { "AuthorUserId" });
            DropIndex("dbo.UserReports", new[] { "TargetUserId" });
            DropTable("dbo.TaskReports");
            DropTable("dbo.UserReports");
        }
        
        public override void Down()
        {
            CreateTable(
                "dbo.UserReports",
                c => new
                    {
                        UserReportId = c.Int(nullable: false, identity: true),
                        Description = c.String(nullable: false),
                        AuthorUserId = c.Int(nullable: false),
                        TargetUserId = c.Int(nullable: false),
                    })
                .PrimaryKey(t => t.UserReportId);
            
            CreateTable(
                "dbo.TaskReports",
                c => new
                    {
                        TaskReportId = c.Int(nullable: false, identity: true),
                        Description = c.String(nullable: false),
                        TaskId = c.Int(nullable: false),
                        UserId = c.Int(nullable: false),
                    })
                .PrimaryKey(t => t.TaskReportId);
            
            CreateIndex("dbo.UserReports", "TargetUserId");
            CreateIndex("dbo.UserReports", "AuthorUserId");
            CreateIndex("dbo.TaskReports", "UserId");
            CreateIndex("dbo.TaskReports", "TaskId");
            AddForeignKey("dbo.UserReports", "TargetUserId", "dbo.Users", "UserId", cascadeDelete: true);
            AddForeignKey("dbo.UserReports", "AuthorUserId", "dbo.Users", "UserId", cascadeDelete: true);
            AddForeignKey("dbo.TaskReports", "UserId", "dbo.Users", "UserId", cascadeDelete: true);
            AddForeignKey("dbo.TaskReports", "TaskId", "dbo.Tasks", "TaskId", cascadeDelete: true);
        }
    }
}
