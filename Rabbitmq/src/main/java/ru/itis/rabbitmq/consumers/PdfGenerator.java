package ru.itis.rabbitmq.consumers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import ru.itis.rabbitmq.dto.UserDto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

public class PdfGenerator {
    private final static String EXCHANGE_NAME = "passports";
    private final static String EXCHANGE_TYPE = "fanout";

    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();
            channel.exchangeDeclare(EXCHANGE_NAME, EXCHANGE_TYPE);

            // создаем временные очереди со случайными названиями
            String queue = channel.queueDeclare().getQueue();
            String queue2 = channel.queueDeclare().getQueue();
            String queue3 = channel.queueDeclare().getQueue();

            // привязали очереди к EXCHANGE_NAME
            channel.queueBind(queue, EXCHANGE_NAME, "");
            channel.queueBind(queue2, EXCHANGE_NAME, "");
            channel.queueBind(queue3, EXCHANGE_NAME, "");

            // первая очередь
            DeliverCallback queueCallback = (consumerTag, message) -> {
                try {
                    UserDto userDto = new UserDto(objectMapper.readValue(message.getBody(), ArrayList.class));
                    AcademicLeavePdf.generate(userDto);

                    channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
                } catch (IOException e) {
                    System.err.println("FAILED");
                    channel.basicReject(message.getEnvelope().getDeliveryTag(), false);
                }
            };
            channel.basicConsume(queue, false, queueCallback, consumerTag -> {
            });

            // вторая очередь
            DeliverCallback queue2Callback = (consumerTag, message) -> {
                try {
                    UserDto userDto = new UserDto(objectMapper.readValue(message.getBody(), ArrayList.class));
                    DismissalPdf.generate(userDto);

                    channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
                } catch (IOException e) {
                    System.err.println("FAILED");
                    channel.basicReject(message.getEnvelope().getDeliveryTag(), false);
                }
            };
            channel.basicConsume(queue2, false, queue2Callback, consumerTag -> {
            });

            // третья очередь
            DeliverCallback queue3Callback = (consumerTag, message) -> {
                try {
                    UserDto userDto = new UserDto(objectMapper.readValue(message.getBody(), ArrayList.class));
                    ExclusionPdf.generate(userDto);

                    channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
                } catch (IOException e) {
                    System.err.println("FAILED");
                    channel.basicReject(message.getEnvelope().getDeliveryTag(), false);
                }
            };
            channel.basicConsume(queue3, false, queue3Callback, consumerTag -> {
            });
        } catch (IOException | TimeoutException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
