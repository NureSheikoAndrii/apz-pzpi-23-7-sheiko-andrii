using Microsoft.AspNetCore.Mvc;

namespace SmartRoomBackend_Upd1.Controllers
{
    [ApiController]
    [Route("api/test")]
    public class TestController : ControllerBase
    {
        //ендпоінт навантажує процесор
        //нічого не зберігає в базу даних
        [HttpGet("cpu")]
        public IActionResult CpuLoad()
        {
            //щтучне навантаження на CPU
            //підррахунок чисел Фібоначчі - важка операція
            long result = 0;
            for (int i = 0; i < 1000000; i++)
            {
                result += i;
            }
            
            //повертаємо результат
            return Ok(new { 
                message = "CPU навантаження виконано", 
                instance = Environment.MachineName,
                result = result 
            });
        }
        
        //легкий ендпоінт - просто перевірка, що сервер працює
        [HttpGet("ping")]
        public IActionResult Ping()
        {
            return Ok(new { 
                status = "OK", 
                time = DateTime.UtcNow,
                instance = Environment.MachineName,
                port = HttpContext.Connection.LocalPort
            });
        }
    }
}