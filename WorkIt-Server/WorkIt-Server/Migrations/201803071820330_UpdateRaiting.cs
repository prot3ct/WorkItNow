namespace WorkIt_Server.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class UpdateRaiting : DbMigration
    {
        public override void Up()
        {
            DropForeignKey("dbo.Raitings", "GiverUserId", "dbo.Users");
            DropIndex("dbo.Raitings", new[] { "GiverUserId" });
            DropColumn("dbo.Raitings", "GiverUserId");
        }
        
        public override void Down()
        {
            AddColumn("dbo.Raitings", "GiverUserId", c => c.Int(nullable: false));
            CreateIndex("dbo.Raitings", "GiverUserId");
            AddForeignKey("dbo.Raitings", "GiverUserId", "dbo.Users", "UserId", cascadeDelete: true);
        }
    }
}
