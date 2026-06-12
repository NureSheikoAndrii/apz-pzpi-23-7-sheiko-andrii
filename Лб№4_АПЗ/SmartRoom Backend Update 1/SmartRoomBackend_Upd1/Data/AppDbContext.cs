namespace SmartRoomBackend_Upd1.Data
{
    using Models;
    using Microsoft.EntityFrameworkCore;

    public class AppDbContext : DbContext
    {
        public AppDbContext(DbContextOptions<AppDbContext> options) : base(options) { }

        public DbSet<SensorData> Sensors { get; set; }
        public DbSet<SystemLog> SystemLogs { get; set; }
        public DbSet<AutomationSettings> AutomationSettings { get; set; }
    }
}
