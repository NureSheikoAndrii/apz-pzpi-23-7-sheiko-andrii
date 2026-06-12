namespace SmartRoomBackend_Upd1.Services
{
    using Data;

    public class LogService
    {
        private readonly AppDbContext _context;

        public LogService(AppDbContext context)
        {
            _context = context;
        }

        public async Task LogAsync(string message)
        {
            _context.SystemLogs.Add(new Models.SystemLog { Message = message });
            await _context.SaveChangesAsync();
        }
    }
}
