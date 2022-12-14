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
        try (MyKafkaProducer producer = new MyKafkaProducer("my-topic", props)) {

            // Publish a message to the Kafka topic
            producer.produce("Hello, Kafka!");
        }
        try (MyKafkaConsumer consumer = new MyKafkaConsumer("my-topic", props)) {
            // Consume messages from the Kafka topic
            consumer.consume();
        }
    }
}
