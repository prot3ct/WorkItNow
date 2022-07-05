namespace WorkIt_Server.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class _null : DbMigration
    {
        public override void Up()
        {
            DropForeignKey("dbo.Tasks", "AssignedUserId", "dbo.Users");
            DropIndex("dbo.Tasks", new[] { "AssignedUserId" });
            AlterColumn("dbo.Tasks", "AssignedUserId", c => c.Int());
            CreateIndex("dbo.Tasks", "AssignedUserId");
            AddForeignKey("dbo.Tasks", "AssignedUserId", "dbo.Users", "UserId");
        }
        
        public override void Down()
        {
            DropForeignKey("dbo.Tasks", "AssignedUserId", "dbo.Users");
            DropIndex("dbo.Tasks", new[] { "AssignedUserId" });
            AlterColumn("dbo.Tasks", "AssignedUserId", c => c.Int(nullable: false));
            CreateIndex("dbo.Tasks", "AssignedUserId");
            AddForeignKey("dbo.Tasks", "AssignedUserId", "dbo.Users", "UserId", cascadeDelete: true);
        }
    }
}
