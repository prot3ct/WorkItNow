namespace WorkIt_Server.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class ChangeFullName : DbMigration
    {
        public override void Up()
        {
            AddColumn("dbo.Users", "FullName", c => c.String(nullable: false));
            DropColumn("dbo.Users", "Firstname");
            DropColumn("dbo.Users", "Lastname");
        }
        
        public override void Down()
        {
            AddColumn("dbo.Users", "Lastname", c => c.String(nullable: false));
            AddColumn("dbo.Users", "Firstname", c => c.String(nullable: false));
            DropColumn("dbo.Users", "FullName");
        }
    }
}
