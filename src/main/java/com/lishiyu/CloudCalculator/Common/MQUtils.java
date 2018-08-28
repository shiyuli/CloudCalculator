package com.lishiyu.CloudCalculator.Common;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import java.util.ArrayList;
import java.util.List;

public class MQUtils {

    public static final String REQUEST_QUEUE_NAME = "REQUEST_QUEUE";
    public static final String RESULT_QUEUE_NAME = "RESULT_QUEUE";

    private MQUtils() {}

    public static void send(String message, String queueName, Connection connection) throws Exception {
        Channel channel = connection.createChannel();

        channel.queueDeclare(queueName, false, false, false, null);
        channel.basicPublish("", queueName, null, message.getBytes());

        Utils.debug(" [x] sent '" + message + "'");
    }

    public static List<String> read(String queueName, Connection connection) throws Exception {
        Channel channel = connection.createChannel();

        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(queueName, true, consumer);

        List<String> messageList = new ArrayList<String>();

        while(true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());

            Utils.debug(" [x] read '" + message + "'");

            if("END".equals(message))
            {
                break;
            }
            else
            {
                messageList.add(message);
            }
        }

        return messageList;
    }
}
