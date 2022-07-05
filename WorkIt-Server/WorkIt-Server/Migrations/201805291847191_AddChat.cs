namespace WorkIt_Server.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class AddChat : DbMigration
    {
        public override void Up()
        {
            CreateTable(
                "dbo.Dialogs",
                c => new
                    {
                        DialogId = c.Int(nullable: false, identity: true),
                        LastMessageId = c.Int(nullable: false),
                        User1Id = c.Int(nullable: false),
                        User2Id = c.Int(nullable: false),
                    })
                .PrimaryKey(t => t.DialogId)
                .ForeignKey("dbo.Messages", t => t.DialogId)
                .ForeignKey("dbo.Users", t => t.User1Id)
                .ForeignKey("dbo.Users", t => t.User2Id)
                .Index(t => t.DialogId)
                .Index(t => t.User1Id)
                .Index(t => t.User2Id);
            
            CreateTable(
                "dbo.Messages",
                c => new
                    {
                        MessageId = c.Int(nullable: false, identity: true),
                        Text = c.String(nullable: false),
                        CreatedAt = c.DateTime(nullable: false),
                        AuthorId = c.Int(nullable: false),
                        DialogId = c.Int(nullable: false),
                    })
                .PrimaryKey(t => t.MessageId)
                .ForeignKey("dbo.Users", t => t.AuthorId, cascadeDelete: true)
                .Index(t => t.AuthorId);
            
        }
        
        public override void Down()
        {
            DropForeignKey("dbo.Dialogs", "User2Id", "dbo.Users");
            DropForeignKey("dbo.Dialogs", "User1Id", "dbo.Users");
            DropForeignKey("dbo.Dialogs", "DialogId", "dbo.Messages");
            DropForeignKey("dbo.Messages", "AuthorId", "dbo.Users");
            DropIndex("dbo.Messages", new[] { "AuthorId" });
            DropIndex("dbo.Dialogs", new[] { "User2Id" });
            DropIndex("dbo.Dialogs", new[] { "User1Id" });
            DropIndex("dbo.Dialogs", new[] { "DialogId" });
            DropTable("dbo.Messages");
            DropTable("dbo.Dialogs");
        }
    }
}
