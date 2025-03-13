@Configuration
public class MetricsConfig {
    
    private final MeterRegistry registry;

    public MetricsConfig(MeterRegistry registry) {
        this.registry = registry;
    }

    @Bean
    public Counter documentCreationCounter() {
        return Counter.builder("dms.documents.created")
                .description("Number of documents created")
                .register(registry);
    }

    @Bean
    public Timer documentProcessingTimer() {
        return Timer.builder("dms.documents.processing")
                .description("Time taken to process documents")
                .register(registry);
    }
} 