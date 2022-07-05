namespace WorkIt_Server.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class ChangeUserPictureType : DbMigration
    {
        public override void Up()
        {
            AlterColumn("dbo.Users", "Picture", c => c.String(unicode: false));
        }
        
        public override void Down()
        {
            AlterColumn("dbo.Users", "Picture", c => c.Binary());
        }
    }
}
