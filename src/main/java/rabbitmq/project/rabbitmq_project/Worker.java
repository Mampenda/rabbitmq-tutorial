package rabbitmq.project.rabbitmq_project;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.nio.charset.StandardCharsets;

public class Worker {
    // Set up queue
    private final static String TASK_QUEUE_NAME = "hello";
    private final static String DUARBLE_TASK_QUEUE_NAME = "task_queue";

    public static void main(String[] argv) throws Exception {

        // ConnectionFactory() facilitates opening a Connection to an AMQP broker.
        ConnectionFactory factory = new ConnectionFactory();

        // Connect factory to server by setting default host to use for connection (we use the local machine)
        factory.setHost("localhost");

        // Connect to a RabbitMQ node to the factory variable
        Connection connection = factory.newConnection();

        // Create the channel
        Channel channel = connection.createChannel();

        // Make sure that the queue will survive a RabbitMQ node restart by making it durable
        boolean durable = true;

        // Declare a durable queue to receive from
        channel.queueDeclare(DUARBLE_TASK_QUEUE_NAME, durable, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        // DeliverCallback interface to buffer the messages pushed to us by the server (only accept 1 at a time)
        channel.basicQos(1);

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");

            System.out.println(" [x] Received '" + message + "'");
            try {
                doWork(message);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                System.out.println(" [x] Done");
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            }
        };

        // Manual message acknowledgements are turned on by default (change to true to turn off)
        boolean autoAck = false;
        channel.basicConsume(TASK_QUEUE_NAME, autoAck, deliverCallback, consumerTag -> { });    }

    private static void doWork(String task) throws InterruptedException {
        for (char ch: task.toCharArray()) {
            if (ch == '.') Thread.sleep(1000);
        }
    }
}
