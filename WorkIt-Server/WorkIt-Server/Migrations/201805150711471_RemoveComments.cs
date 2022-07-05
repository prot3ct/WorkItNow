namespace WorkIt_Server.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class RemoveComments : DbMigration
    {
        public override void Up()
        {
            DropForeignKey("dbo.Comments", "UserId", "dbo.Users");
            DropForeignKey("dbo.TaskComments", "CommentId", "dbo.Comments");
            DropForeignKey("dbo.TaskComments", "TaskId", "dbo.Tasks");
            DropForeignKey("dbo.TaskComments", "TaskRequestId", "dbo.Tasks");
            DropIndex("dbo.Comments", new[] { "UserId" });
            DropIndex("dbo.TaskComments", new[] { "TaskId" });
            DropIndex("dbo.TaskComments", new[] { "TaskRequestId" });
            DropIndex("dbo.TaskComments", new[] { "CommentId" });
            DropTable("dbo.Comments");
            DropTable("dbo.TaskComments");
        }
        
        public override void Down()
        {
            CreateTable(
                "dbo.TaskComments",
                c => new
                    {
                        TaskCommentsId = c.Int(nullable: false, identity: true),
                        TaskId = c.Int(nullable: false),
                        TaskRequestId = c.Int(),
                        CommentId = c.Int(nullable: false),
                    })
                .PrimaryKey(t => t.TaskCommentsId);
            
            CreateTable(
                "dbo.Comments",
                c => new
                    {
                        CommentId = c.Int(nullable: false, identity: true),
                        Body = c.String(nullable: false),
                        UserId = c.Int(nullable: false),
                    })
                .PrimaryKey(t => t.CommentId);
            
            CreateIndex("dbo.TaskComments", "CommentId");
            CreateIndex("dbo.TaskComments", "TaskRequestId");
            CreateIndex("dbo.TaskComments", "TaskId");
            CreateIndex("dbo.Comments", "UserId");
            AddForeignKey("dbo.TaskComments", "TaskRequestId", "dbo.Tasks", "TaskId");
            AddForeignKey("dbo.TaskComments", "TaskId", "dbo.Tasks", "TaskId");
            AddForeignKey("dbo.TaskComments", "CommentId", "dbo.Comments", "CommentId");
            AddForeignKey("dbo.Comments", "UserId", "dbo.Users", "UserId", cascadeDelete: true);
        }
    }
}
