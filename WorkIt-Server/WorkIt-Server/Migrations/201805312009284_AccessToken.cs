namespace WorkIt_Server.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class AccessToken : DbMigration
    {
        public override void Up()
        {
            AddColumn("dbo.Users", "AccessToken", c => c.String(nullable: true));
        }
        
        public override void Down()
        {
            DropColumn("dbo.Users", "AccessToken");
        }
    }
}
