namespace SmartRoomBackend_Upd1.Models
{
    public class SensorData
    {
        public int Id { get; set; }
        public double Temperature { get; set; }   // Температура
        public double Humidity { get; set; }      // Вологість
        public int LightLevel { get; set; }       // Позник світла (вкл\викл)
        public DateTime CreatedAt { get; set; } = DateTime.UtcNow;
    }
}
