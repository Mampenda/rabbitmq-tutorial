package rabbitmq.project.rabbitmq_project;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class NewTask {

    // Set up queue
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] argv) throws Exception {

        // ConnectionFactory() facilitates opening a Connection to an AMQP broker.
        ConnectionFactory factory = new ConnectionFactory();

        // Connect factory to server by setting default host to use for connection (we use the local machine)
        factory.setHost("localhost");

        try (Connection connection = factory.newConnection(); // Connect to a RabbitMQ node to the factory variable
             Channel channel = connection.createChannel()) {  // Create the channel

            // Make sure that the queue will survive a RabbitMQ node restart by making it durable



            // Declare a queue to send to
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            // Create and send/publish a message to the queue
            String message = String.join(" ", argv);
            channel.basicPublish("", "hello", null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
        }
    }
}
