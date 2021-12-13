using Microsoft.EntityFrameworkCore;
using DataAccessTier.Model;

namespace DataAccessTier.Data
{
    public class CalendarDbContext : DbContext
    {
        public  DbSet<User> Users { get; set; }
        public  DbSet<Event> Event { get; set; }
        public DbSet<Address> Address {get;set;}
        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.Entity<User>().HasIndex(u => u.Username).IsUnique();
            modelBuilder.Entity<User>().HasIndex(u => u.Email).IsUnique();
        }
        public CalendarDbContext(DbContextOptions<CalendarDbContext> options)
        :base(options)
        {
        
        }

        // The following configures EF to use a special Postgres library, that enables to update multiple rows in the database
      //protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
            //=> optionsBuilder.UseBatchEF_Npgsql();
    }

}