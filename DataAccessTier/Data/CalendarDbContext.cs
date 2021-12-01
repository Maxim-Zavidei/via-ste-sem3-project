using System;
using System.Collections.Generic;
using Microsoft.EntityFrameworkCore;
using DataAccessTier.Model;
using System.Threading.Tasks;

namespace DataAccessTier.Data
{
    public class CalendarDbContext : DbContext
    {
        public  DbSet<User> Users { get; set; }
        public  DbSet<Event> Event { get; set; }

         /*public string DbPath { get; private set; }

       public CalendarDbContext()
        {
            var folder = Environment.SpecialFolder.LocalApplicationData;
            var path = Environment.GetFolderPath(folder);
            DbPath = $"{path}{System.IO.Path.DirectorySeparatorChar}sep3.db";
        }
*/
        public CalendarDbContext(DbContextOptions<CalendarDbContext> options)
        :base(options)
        {
        
        }

        // The following configures EF to create a Sqlite database file in the
        // special "local" folder for your platform.
      /* protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
            => optionsBuilder.UseNpgsql("Host=localhost;Database=sep3;Username=postgres;Password=6364");*/
    }

}