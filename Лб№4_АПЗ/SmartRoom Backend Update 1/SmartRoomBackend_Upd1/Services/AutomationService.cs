namespace SmartRoomBackend_Upd1.Services
{
    using SmartRoomBackend_Upd1.Data;
    using SmartRoomBackend_Upd1.Models;
    using Microsoft.EntityFrameworkCore;

    public class AutomationService
    {
        private readonly AppDbContext _context;
        private readonly LogService _logService;

        public AutomationService(AppDbContext context, LogService logService)
        {
            _context = context;
            _logService = logService;
        }

        public async Task ProcessAsync(SensorData data)
        {
            var settings = await _context.AutomationSettings.FirstOrDefaultAsync()
                ?? new AutomationSettings();

            if (data.Temperature > settings.MaxTemperature && data.LightLevel < settings.MinLightLevel)
            {
                await _logService.LogAsync("Автоматизація: перевищено максимальну температуру та мінімальну освітленість!!!");
            }

            else  if (data.Temperature > settings.MaxTemperature)
            {
                await _logService.LogAsync("Автоматизація: перевищено температуру!");
            }

            if (data.LightLevel < settings.MinLightLevel)
            {
                await _logService.LogAsync("Автоматизація: недостатня освітленість!");
            }
        }
    }

}
