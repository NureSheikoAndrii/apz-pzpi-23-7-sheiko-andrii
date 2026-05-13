import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.decorators.Decorators;
import java.time.Duration;
import java.util.function.Supplier;

public class RecommendationServiceClient {
    private final CircuitBreaker circuitBreaker;
    
    public RecommendationServiceClient() {
      // Налаштовуємо параметри автоматичного вимикача
        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
            .failureRateThreshold(50)                    // замикаємо при 50% помилок
            .slowCallRateThreshold(50)                   // замикаємо при 50% повільних викликів
            .slowCallDurationThreshold(Duration.ofSeconds(2)) // повільний виклик > 2 секунд
            .waitDurationInOpenState(Duration.ofSeconds(30))   // розімкнути через 30 секунд
            .build();
        
        this.circuitBreaker = CircuitBreaker.of("recommendations", config);
    }
    
    public List<String> getRecommendations(String userId) {
        // Реальний HTTP-виклик до сервісу рекомендацій
        Supplier<List<String>> remoteCall = () -> {
            // Тут був би реальний REST-виклик
            return httpClient.get("https://api.netflix.com/recommendations/" + userId);
        };
        
        // Запасний варіант при збої основного
        Supplier<List<String>> fallback = (error) -> {
            System.err.println("Recommendation service failed: " + error.getMessage());
            return List.of("Stranger Things", "The Crown", "Black Mirror");
        };

        // Огортаємо виклик з вимикачем та fallback
        Supplier<List<String>> decorated = Decorators
            .ofSupplier(remoteCall)
            .withCircuitBreaker(circuitBreaker)
            .withFallback(fallback)
            .decorate();
        
        return decorated.get();
    }
}

