namespace WorkIt_Server.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class DatabaseRedesign : DbMigration
    {
        public override void Up()
        {
            DropForeignKey("dbo.Tasks", "LocationId", "dbo.Locations");
            DropIndex("dbo.Locations", "IX_CountryCityAndAddress");
            DropIndex("dbo.Tasks", new[] { "LocationId" });
            AddColumn("dbo.Tasks", "City", c => c.String(nullable: false));
            AddColumn("dbo.Tasks", "Address", c => c.String(nullable: false));
            AddColumn("dbo.Tasks", "Length", c => c.Int(nullable: false));
            AddColumn("dbo.Tasks", "HasTaskerGivenRating", c => c.Boolean(nullable: false));
            DropColumn("dbo.Tasks", "EndDate");
            DropColumn("dbo.Tasks", "MinTasksCompleted");
            DropColumn("dbo.Tasks", "HasTaskterGivenRating");
            DropColumn("dbo.Tasks", "LocationId");
            DropTable("dbo.Locations");
        }
        
        public override void Down()
        {
            CreateTable(
                "dbo.Locations",
                c => new
                    {
                        LocationId = c.Int(nullable: false, identity: true),
                        Country = c.String(nullable: false, maxLength: 50),
                        City = c.String(nullable: false, maxLength: 50),
                        Address = c.String(nullable: false, maxLength: 50),
                    })
                .PrimaryKey(t => t.LocationId);
            
            AddColumn("dbo.Tasks", "LocationId", c => c.Int(nullable: false));
            AddColumn("dbo.Tasks", "HasTaskterGivenRating", c => c.Boolean(nullable: false));
            AddColumn("dbo.Tasks", "MinTasksCompleted", c => c.Int(nullable: false));
            AddColumn("dbo.Tasks", "EndDate", c => c.DateTime(nullable: false));
            DropColumn("dbo.Tasks", "HasTaskerGivenRating");
            DropColumn("dbo.Tasks", "Length");
            DropColumn("dbo.Tasks", "Address");
            DropColumn("dbo.Tasks", "City");
            CreateIndex("dbo.Tasks", "LocationId");
            CreateIndex("dbo.Locations", new[] { "Country", "City", "Address" }, unique: true, name: "IX_CountryCityAndAddress");
            AddForeignKey("dbo.Tasks", "LocationId", "dbo.Locations", "LocationId", cascadeDelete: true);
        }
    }
}
