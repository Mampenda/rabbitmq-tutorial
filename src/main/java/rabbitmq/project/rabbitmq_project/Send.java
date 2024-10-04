package rabbitmq.project.rabbitmq_project;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;

// Producer class
public class Send {

    // Set up queue
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] argv) throws Exception {

        // ConnectionFactory() facilitates opening a Connection to an AMQP broker.
        ConnectionFactory factory = new ConnectionFactory();

        // Connect factory to server by setting default host to use for connection (we use the local machine)
        factory.setHost("localhost");

        try ( Connection connection = factory.newConnection(); // Connect to a RabbitMQ node to the factory variable
              Channel channel = connection.createChannel()) {  // Create the channel

            // Declare a queue to send to
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            // Create and send/publish a message to the queue
            String message = "Hello World!";
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + message + "'");
        }
    }
}