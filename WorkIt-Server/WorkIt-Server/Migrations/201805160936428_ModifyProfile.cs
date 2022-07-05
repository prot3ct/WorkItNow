namespace WorkIt_Server.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class ModifyProfile : DbMigration
    {
        public override void Up()
        {
            AddColumn("dbo.Users", "Phone", c => c.String());
            AddColumn("dbo.Users", "Picture", c => c.Binary());
            AddColumn("dbo.Users", "RaitingAsTasker", c => c.Double(nullable: false));
            AddColumn("dbo.Users", "RaitingAsSupervisor", c => c.Double(nullable: false));
            AddColumn("dbo.Users", "ReviewsAsTasker", c => c.Int(nullable: false));
            AddColumn("dbo.Users", "ReviewsAsSupervisor", c => c.Int(nullable: false));
            DropColumn("dbo.Users", "RaitingAsEmployee");
            DropColumn("dbo.Users", "RaitingAsCreator");
            DropColumn("dbo.Users", "TaskCompleted");
        }
        
        public override void Down()
        {
            AddColumn("dbo.Users", "TaskCompleted", c => c.Int(nullable: false));
            AddColumn("dbo.Users", "RaitingAsCreator", c => c.Double(nullable: false));
            AddColumn("dbo.Users", "RaitingAsEmployee", c => c.Double(nullable: false));
            DropColumn("dbo.Users", "ReviewsAsSupervisor");
            DropColumn("dbo.Users", "ReviewsAsTasker");
            DropColumn("dbo.Users", "RaitingAsSupervisor");
            DropColumn("dbo.Users", "RaitingAsTasker");
            DropColumn("dbo.Users", "Picture");
            DropColumn("dbo.Users", "Phone");
        }
    }
}
