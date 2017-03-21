package cs4r.labs.rabbitmq.workqueues;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.util.Scanner;

import static com.rabbitmq.client.MessageProperties.PERSISTENT_TEXT_PLAIN;

public class NewTask {

    private final static String QUEUE_NAME = "task_queue";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        boolean durable = true;
        channel.queueDeclare(QUEUE_NAME, durable, false, false, null);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Write a message (or exit to quit): ");
        String input = scanner.nextLine();
        while (!input.equals("exit")) {
            String message = input;
            channel.basicPublish("", QUEUE_NAME,
                    PERSISTENT_TEXT_PLAIN,
                    message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
            System.out.println("Write a message (or exit to quit): ");
            input = scanner.nextLine();
        }


        channel.close();
        connection.close();
    }
}