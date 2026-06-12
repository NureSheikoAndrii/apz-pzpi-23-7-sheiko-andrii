namespace SmartRoomBackend_Upd1.Controllers
{
    using Microsoft.AspNetCore.Mvc;

    [ApiController]
    [Route("api/light")]
    public class LightController : ControllerBase
    {
        private static bool _isLightOn = false;
        
        // POST api/light:
        [HttpPost]
        public IActionResult SetLight([FromQuery] bool state)
        {
            _isLightOn = state;
            return Ok(new { light = _isLightOn });
        }

        // GET api/light/status:
        [HttpGet("status")]
        public IActionResult GetStatus()
        {
            return Ok(new { light = _isLightOn });
        }
    }
}
