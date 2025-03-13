import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class InstantSerializer extends JsonSerializer<Instant> {
    private static final DateTimeFormatter formatter = 
        DateTimeFormatter.ISO_DATE_TIME.withZone(ZoneId.systemDefault());

    @Override
    public void serialize(Instant value, JsonGenerator gen, SerializerProvider provider) 
            throws IOException {
        if (value != null) {
            gen.writeString(formatter.format(value));
        }
    }
} 