import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class MyKafkaConsumer extends KafkaConsumer<String, String> {
    Logger LOGGER = Logger.getLogger(MyKafkaConsumer.class);

    private final String topic;

    public MyKafkaConsumer(String topic, Properties props) {
        super(props);
        this.topic = topic;

        // Configure log4j. For any specific logging comment this out and edit log4j.properties instead
        BasicConfigurator.configure();
    }

    public void consume() {
        this.subscribe(Collections.singletonList(this.topic));

        //noinspection InfiniteLoopStatement
        while (true) {
            ConsumerRecords<String, String> records = this.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, String> record : records){
                LOGGER.info("Key: " + record.key() + ", Value: " + record.value());
                LOGGER.info("Partition: " + record.partition() + ", Offset:" + record.offset());
            }
        }
    }

    public static void main(String[] args) {
        Properties props = new Properties();
        try {
            props.load(new FileInputStream("java.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try (MyKafkaConsumer consumer = new MyKafkaConsumer("my-topic", props)) {
            consumer.consume();
        }
    }
}
