namespace WorkIt_Server.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class ModifyTaskRequest : DbMigration
    {
        public override void Up()
        {
            DropColumn("dbo.TaskRequests", "Description");
        }
        
        public override void Down()
        {
            AddColumn("dbo.TaskRequests", "Description", c => c.String(nullable: false));
        }
    }
}
