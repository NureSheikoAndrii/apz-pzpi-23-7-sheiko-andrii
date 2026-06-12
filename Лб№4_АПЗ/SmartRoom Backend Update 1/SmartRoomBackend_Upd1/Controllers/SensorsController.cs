namespace SmartRoomBackend_Upd1.Controllers
{
    using Data;
    using Models;
    using Microsoft.AspNetCore.Mvc;
    using Microsoft.EntityFrameworkCore;
    using SmartRoomBackend_Upd1.Services;

    [ApiController]
    [Route("api/sensors")]
    public class SensorsController : ControllerBase
    {
        private readonly AppDbContext _context;

        public SensorsController(AppDbContext context)
        {
            _context = context;
        }

        // POST api/sensors (оновлений):
        [HttpPost]
        public async Task<IActionResult> Create(SensorData data, [FromServices] AutomationService automationService, [FromServices] LogService logService)
        {
            _context.Sensors.Add(data);
            await _context.SaveChangesAsync();

            await automationService.ProcessAsync(data);
            await logService.LogAsync("Отримано нові сенсорні дані");

            return Ok(data);
        }

        // GET api/sensors/latest:
        [HttpGet("latest")]
        public async Task<IActionResult> GetLatest()
        {
            var latest = await _context.Sensors
                .OrderByDescending(x => x.CreatedAt)
                .FirstOrDefaultAsync();

            return Ok(latest);
        }

        [HttpPut("{id}")]
        public async Task<IActionResult> Update(int id, SensorData updatedData)
        {
            var data = await _context.Sensors.FindAsync(id);

            if (data == null)
                return NotFound();

            data.Temperature = updatedData.Temperature;
            data.Humidity = updatedData.Humidity;
            data.LightLevel = updatedData.LightLevel;

            await _context.SaveChangesAsync();
            return Ok(data);
        }

        [HttpDelete("{id}")]
        public async Task<IActionResult> Delete(int id)
        {
            var data = await _context.Sensors.FindAsync(id);

            if (data == null)
                return NotFound();

            _context.Sensors.Remove(data);
            await _context.SaveChangesAsync();

            return NoContent();
        }


        // GET api/sensors/history:
        [HttpGet("history")]
        public async Task<IActionResult> GetHistory(
            DateTime from,
            DateTime to)
        {
            var data = await _context.Sensors
                .Where(x => x.CreatedAt >= from && x.CreatedAt <= to)
                .OrderBy(x => x.CreatedAt)
                .ToListAsync();

            return Ok(data);
        }
    }
}
