namespace WorkIt_Server.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class Revert : DbMigration
    {
        public override void Up()
        {
            DropForeignKey("dbo.Messages", "DialogId", "dbo.Dialogs");
            DropPrimaryKey("dbo.Dialogs");
            AlterColumn("dbo.Dialogs", "DialogId", c => c.Int(nullable: false, identity: true));
            AddPrimaryKey("dbo.Dialogs", "DialogId");
            AddForeignKey("dbo.Messages", "DialogId", "dbo.Dialogs", "DialogId", cascadeDelete: true);
        }
        
        public override void Down()
        {
            DropForeignKey("dbo.Messages", "DialogId", "dbo.Dialogs");
            DropPrimaryKey("dbo.Dialogs");
            AlterColumn("dbo.Dialogs", "DialogId", c => c.Int(nullable: false));
            AddPrimaryKey("dbo.Dialogs", "DialogId");
            AddForeignKey("dbo.Messages", "DialogId", "dbo.Dialogs", "DialogId", cascadeDelete: true);
        }
    }
}
