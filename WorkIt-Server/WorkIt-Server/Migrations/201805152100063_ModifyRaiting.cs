namespace WorkIt_Server.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class ModifyRaiting : DbMigration
    {
        public override void Up()
        {
            AddColumn("dbo.Raitings", "Description", c => c.String());
        }
        
        public override void Down()
        {
            DropColumn("dbo.Raitings", "Description");
        }
    }
}
