namespace WorkIt_Server.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class UpdateTAsk : DbMigration
    {
        public override void Up()
        {
            AlterColumn("dbo.Tasks", "Reward", c => c.Double(nullable: false));
            AlterColumn("dbo.Tasks", "MinRaiting", c => c.Double(nullable: false));
        }
        
        public override void Down()
        {
            AlterColumn("dbo.Tasks", "MinRaiting", c => c.Int(nullable: false));
            AlterColumn("dbo.Tasks", "Reward", c => c.String(nullable: false));
        }
    }
}
