package rabbitmq.project.rabbitmq_project;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class NewTask {

    // Set up queue(s)
//    private final static String QUEUE_NAME = "hello";
    private final static String DURABLE_QUEUE_NAME = "task_queue";

    public static void main(String[] argv) throws Exception {

        // ConnectionFactory() facilitates opening a Connection to an AMQP broker.
        ConnectionFactory factory = new ConnectionFactory();

        // Connect factory to server by setting default host to use for connection (we use the local machine)
        factory.setHost("localhost");

        try (Connection connection = factory.newConnection(); // Connect to a RabbitMQ node to the factory variable
             Channel channel = connection.createChannel()) {  // Create the channel

            // Make durable queue that will survive a RabbitMQ node restart
            boolean durable = true;

            // Declare a durable queue that wont we lost at restart
            channel.queueDeclare(DURABLE_QUEUE_NAME, durable, false, false, null);

            // Create and send/publish a message to the queue
            String message = String.join(" ", argv);

            //  Mark messages as persistent, since queue won't be lost at restarts
            channel.basicPublish("", DURABLE_QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
        }
    }
}
