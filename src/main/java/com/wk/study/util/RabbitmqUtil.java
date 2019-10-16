package com.wk.study.util;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

/**
 * Created with IDEA
 * author:wenka wkwenka@gmail.com
 * Date:2019/10/16  上午 09:27
 * Description:
 */
public class RabbitmqUtil {

    private static final String EXCHANGE = "IpoOrder";
    private static final String ROUTING_KEY = "IPO.order";
    private static final String QUEUE = "ipo-order";

    private static final String RABBITMQ_IP = "192.168.80.129";
    private static final int RABBITMQ_PORT = 5672;
    private static final String RABBITMQ_USERNAME = "admin";
    private static final String RABBITMQ_PASSWORD = "123456";
    private static final String VIRTUAL_HOST = "my_vhost";

    private static final boolean IS_TURN = true;

    private volatile static Connection connection;
    private volatile static Channel channel;

    private static Connection getConnection() {
        if (Objects.isNull(connection)) {
            ConnectionFactory connectionFactory = new ConnectionFactory();
            connectionFactory.setHost(RABBITMQ_IP);
            connectionFactory.setPort(RABBITMQ_PORT);
            connectionFactory.setVirtualHost(VIRTUAL_HOST);
            connectionFactory.setUsername(RABBITMQ_USERNAME);
            connectionFactory.setPassword(RABBITMQ_PASSWORD);
            try {
                connection = connectionFactory.newConnection();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    private static Channel getChannel() {
        if (Objects.isNull(channel)) {
            Connection connection = getConnection();
            try {
                Optional<Channel> channelOptional = connection.openChannel();
                if (channelOptional.isPresent()) {
                    channel = channelOptional.get();
                } else {
                    throw new RuntimeException("获取 Channel 失败!");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return channel;
    }

    private static void close() {
        try {
            if (Objects.nonNull(channel)) {
                channel.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } finally {
            channel = null;
        }
        try {
            if (Objects.nonNull(connection)) {
                connection.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            connection = null;
        }
    }

    private static byte[] getMessageBytes(Object o) {
        byte[] bytes = null;

        if (o instanceof String) {
            bytes = ((String) o).getBytes(Charset.forName("utf-8"));
        } else {
            bytes = JSON.toJSONString(o).getBytes(Charset.forName("utf-8"));
        }
        return bytes;
    }

    public static boolean sendMessage(Object o) {
        if (!IS_TURN){
            return true;
        }
        Channel channel = getChannel();
        try {
            channel.queueDeclare(QUEUE, true, false, false, null);
            channel.exchangeDeclare(EXCHANGE, "topic", true, false, null);
            channel.queueBind(QUEUE, EXCHANGE, ROUTING_KEY);
            channel.basicPublish(EXCHANGE, ROUTING_KEY, null, getMessageBytes(o));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
//        sendMessage("test");
        byte[] a = {-59};

        System.out.println(new String(a));
    }
}
