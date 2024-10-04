package rabbitmq.project.rabbitmq_project;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;


public class EmitLog {

    // Set up exchange name
    private static final String EXCHANGE_NAME = "logs";

    // Emit logs, i.e. publish messages to the logs exchange
    public static void main(String[] argv) throws Exception {

        // ConnectionFactory() facilitates opening a Connection to an AMQP broker.
        ConnectionFactory factory = new ConnectionFactory();

        // Connect factory to server by setting default host to use for connection (we use the local machine)
        factory.setHost("localhost");

        try (Connection connection = factory.newConnection(); // Connect to a RabbitMQ node to the factory variable
             Channel channel = connection.createChannel()) {  // Create the channel

            // Declare the exchange that receives messages and pushes them onto queues
            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

            // Create a message string variable
            String message = argv.length < 1 ? "info: Hello World!" :
                    String.join(" ", argv);

            // Publish message to exchange
            channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + message + "'");
        }
    }
}
