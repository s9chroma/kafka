import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class MyKafkaClient {

    public static void main(String[] args) {
        Properties props = new Properties();
        try {
            props.load(new FileInputStream("src/main/java/java.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // Create instances of the MyKafkaProducer and MyKafkaConsumer classes
        try (MyKafkaProducer producer = new MyKafkaProducer("testing", props)) {

            // Publish a message to the Kafka topic
            for(int i = 0; i < 1e2; i++)
            {
                producer.produce("Hello, Kafka!");
            }

        }
        try (MyKafkaConsumer consumer = new MyKafkaConsumer("testing", props)) {
            // Consume messages from the Kafka topic
            // Publish a message to the Kafka topic
            for(int i = 0; i < 1e2; i++)
            {
                consumer.consume();
            }
        }
    }
}
