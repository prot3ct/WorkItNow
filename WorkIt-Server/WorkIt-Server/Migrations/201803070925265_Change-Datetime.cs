namespace WorkIt_Server.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class ChangeDatetime : DbMigration
    {
        public override void Up()
        {
            AlterColumn("dbo.Tasks", "StartDate", c => c.DateTime(nullable: false));
            AlterColumn("dbo.Tasks", "EndDate", c => c.DateTime(nullable: false));
        }
        
        public override void Down()
        {
            AlterColumn("dbo.Tasks", "EndDate", c => c.String(nullable: false));
            AlterColumn("dbo.Tasks", "StartDate", c => c.String(nullable: false));
        }
    }
}
