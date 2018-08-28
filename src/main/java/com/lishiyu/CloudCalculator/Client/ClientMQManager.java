package com.lishiyu.CloudCalculator.Client;

import com.lishiyu.CloudCalculator.Common.Constants;
import com.lishiyu.CloudCalculator.Common.MQManager;
import com.lishiyu.CloudCalculator.Common.MQUtils;
import com.lishiyu.CloudCalculator.Common.Utils;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.List;

public class ClientMQManager implements MQManager {

    private static final MQManager instance = new ClientMQManager();

    private ConnectionFactory connectionFactory;
    private Connection connection;

    private ClientMQManager() {
        try {
            init();
        }
        catch (Exception e) {
            Utils.showMessageBox("CloudMQ init failed!", "error", null);
        }
    }

    public static MQManager getInstance() {
        return instance;
    }

    private void init() throws Exception {
        String uri = System.getenv("CLOUDAMQP_URL");
        if(uri == null) uri = Constants.AMQP_URL;

        connectionFactory = new ConnectionFactory();
        connectionFactory.setUri(uri);
        connectionFactory.setRequestedHeartbeat(30);
        connectionFactory.setConnectionTimeout(30);

        connection = connectionFactory.newConnection();
    }

    @Override
    public void send(String message) {
        try {
            MQUtils.send(message, MQUtils.REQUEST_QUEUE_NAME, connection);
        } catch (Exception e) {
        }
    }

    @Override
    public List<String> read() {
        try {
            return MQUtils.read(MQUtils.RESULT_QUEUE_NAME, connection);
        } catch (Exception e) {
            return null;
        }
    }
}
