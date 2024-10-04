package rabbitmq.project.rabbitmq_project;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class ReceiveLogs {

    // Set up exchange name
    private static final String EXCHANGE_NAME = "logs";

    // Receive logs from the queue
    public static void main(String[] argv) throws Exception {

        // ConnectionFactory() facilitates opening a Connection to an AMQP broker.
        ConnectionFactory factory = new ConnectionFactory();

        // Connect factory to server by setting default host to use for connection (we use the local machine)
        factory.setHost("localhost");

        // Create connection to channel
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // Declare the exchange that receives messages and pushes them onto queues
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

        // Create non-durable, exclusive, auto-delete queue with generated queue name
        String queueName = channel.queueDeclare().getQueue();

        // Binding between exchange and queue
        channel.queueBind(queueName, EXCHANGE_NAME, "");

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });
    }
}
