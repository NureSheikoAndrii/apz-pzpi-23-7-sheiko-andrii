namespace SmartRoomBackend_Upd1.Models
{
    public class SystemLog
    {
        public int Id { get; set; }
        public string Message { get; set; }
        public DateTime CreatedAt { get; set; } = DateTime.UtcNow;
    }
}
