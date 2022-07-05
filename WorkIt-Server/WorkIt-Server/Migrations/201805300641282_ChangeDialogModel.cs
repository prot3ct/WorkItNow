namespace WorkIt_Server.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class ChangeDialogModel : DbMigration
    {
        public override void Up()
        {
            DropForeignKey("dbo.Dialogs", "DialogId", "dbo.Messages");
            DropIndex("dbo.Dialogs", new[] { "DialogId" });
            DropPrimaryKey("dbo.Dialogs");
            AddColumn("dbo.Dialogs", "LastMessageText", c => c.String());
            AddColumn("dbo.Dialogs", "LastMessageCreatedAt", c => c.DateTime());
            AlterColumn("dbo.Dialogs", "DialogId", c => c.Int(nullable: false, identity: true));
            AddPrimaryKey("dbo.Dialogs", "DialogId");
            CreateIndex("dbo.Messages", "DialogId");
            AddForeignKey("dbo.Messages", "DialogId", "dbo.Dialogs", "DialogId", cascadeDelete: true);
            DropColumn("dbo.Dialogs", "LastMessageId");
        }
        
        public override void Down()
        {
            AddColumn("dbo.Dialogs", "LastMessageId", c => c.Int());
            DropForeignKey("dbo.Messages", "DialogId", "dbo.Dialogs");
            DropIndex("dbo.Messages", new[] { "DialogId" });
            DropPrimaryKey("dbo.Dialogs");
            AlterColumn("dbo.Dialogs", "DialogId", c => c.Int(nullable: false));
            DropColumn("dbo.Dialogs", "LastMessageCreatedAt");
            DropColumn("dbo.Dialogs", "LastMessageText");
            AddPrimaryKey("dbo.Dialogs", "DialogId");
            CreateIndex("dbo.Dialogs", "DialogId");
            AddForeignKey("dbo.Dialogs", "DialogId", "dbo.Messages", "MessageId");
        }
    }
}
