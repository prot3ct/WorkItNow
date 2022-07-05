namespace WorkIt_Server.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class BigRefactoring : DbMigration
    {
        public override void Up()
        {
            DropColumn("dbo.Tasks", "MinRaiting");
        }
        
        public override void Down()
        {
            AddColumn("dbo.Tasks", "MinRaiting", c => c.Double(nullable: false));
        }
    }
}
