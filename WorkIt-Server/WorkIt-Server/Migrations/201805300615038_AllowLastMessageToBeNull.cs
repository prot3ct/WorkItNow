namespace WorkIt_Server.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class AllowLastMessageToBeNull : DbMigration
    {
        public override void Up()
        {
            AlterColumn("dbo.Dialogs", "LastMessageId", c => c.Int());
        }
        
        public override void Down()
        {
            AlterColumn("dbo.Dialogs", "LastMessageId", c => c.Int(nullable: false));
        }
    }
}
