namespace SmartRoomBackend_Upd1.Controllers
{
    using Microsoft.AspNetCore.Mvc;
    using SmartRoomBackend_Upd1.Data;

    [ApiController]
    [Route("api/admin")]
    public class AdminController : ControllerBase
    {
        private readonly AppDbContext _context;

        public AdminController(AppDbContext context)
        {
            _context = context;
        }

        // Перегляд логів
        [HttpGet("logs")]
        public IActionResult GetLogs()
        {
            return Ok(_context.SystemLogs
                .OrderByDescending(x => x.CreatedAt)
                .ToList());
        }

        // Отримати налаштування
        [HttpGet("settings")]
        public IActionResult GetSettings()
        {
            return Ok(_context.AutomationSettings.FirstOrDefault());
        }

        // Оновити налаштування
        [HttpPost("settings")]
        public async Task<IActionResult> UpdateSettings(AutomationSettings settings)
        {
            var current = _context.AutomationSettings.FirstOrDefault();

            if (current == null)
                _context.AutomationSettings.Add(settings);
            else
            {
                current.MaxTemperature = settings.MaxTemperature;
                current.MinLightLevel = settings.MinLightLevel;
            }

            await _context.SaveChangesAsync();
            return Ok(settings);
        }
    }
}
