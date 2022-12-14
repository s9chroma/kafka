import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class MyKafkaProducer extends KafkaProducer<String, String> {
    Logger LOGGER = Logger.getLogger(MyKafkaConsumer.class);

    private final String topic;

    public MyKafkaProducer(String topic, Properties props) {
        super(props);
        this.topic = topic;

        // Configure log4j. For any specific logging comment this out and edit log4j.properties instead
        BasicConfigurator.configure();
    }

    public void produce(String message) {
        this.send(new ProducerRecord<>(this.topic, message));
        LOGGER.info("Sent message: " + message);
    }

    public static void main(String[] args) {
        Properties props = new Properties();
        try {
            props.load(new FileInputStream("java.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try (MyKafkaProducer producer = new MyKafkaProducer("my-topic", props)) {
            producer.produce("Hello, Kafka!");
        }
    }
}
