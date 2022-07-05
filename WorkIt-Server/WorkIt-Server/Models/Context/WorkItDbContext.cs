using System;
using System.ComponentModel.DataAnnotations.Schema;
using System.Data.Entity;
using System.Data.Entity.ModelConfiguration;
using System.Data.Entity.ModelConfiguration.Conventions;

namespace WorkIt_Server.Models.Context
{
    public class WorkItDbContext : DbContext
    {
        public WorkItDbContext() : base("DefaultConnection")
        {
        }

        public DbSet<User> Users { get; set; }
        public DbSet<Task> Tasks { get; set; }
        public DbSet<TaskRequest> TaskRequests { get; set; }
        public DbSet<Raiting> Raitings { get; set; }
        public DbSet<RequestStatus> RequestStatuses { get; set; }
        public DbSet<UserRole> UserRoles { get; set; }
        public DbSet<Dialog> Dialogs { get; set; }
        public DbSet<Message> Messages { get; set; }

        protected override void OnModelCreating(DbModelBuilder modelBuilder)
        {
            modelBuilder.Entity<User>().Property(m => m.Picture).IsOptional();

            modelBuilder.Entity<Dialog>()
            .HasRequired(c => c.User1)
            .WithMany()
            .WillCascadeOnDelete(false);

            modelBuilder.Entity<Dialog>()
             .HasRequired(c => c.User2)
             .WithMany()
             .WillCascadeOnDelete(false);

            base.OnModelCreating(modelBuilder);
        }
    }
}