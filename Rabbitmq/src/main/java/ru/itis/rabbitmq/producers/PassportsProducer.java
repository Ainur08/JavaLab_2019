package ru.itis.rabbitmq.producers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class PassportsProducer {
    private final static String EXCHANGE_NAME = "passports";
    private final static String EXCHANGE_TYPE = "fanout";

    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        ObjectMapper objectMapper = new ObjectMapper();

        ArrayList data = data();
        try {
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();
            channel.exchangeDeclare(EXCHANGE_NAME, EXCHANGE_TYPE);

            String json = objectMapper.writeValueAsString(data);
            System.out.println(json);
            channel.basicPublish(EXCHANGE_NAME, "", null, json.getBytes());
        } catch (JsonProcessingException e) {
            throw new IllegalStateException(e);
        } catch (IOException | TimeoutException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static ArrayList data() {
        Scanner in = new Scanner(System.in);
        Date dateNow = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy.MM.dd");
        ArrayList data = new ArrayList();

        System.out.println("Введите имя");
        data.add(in.nextLine());
        System.out.println("Введите фамилию");
        data.add(in.nextLine());
        System.out.println("Номер паспорта");
        data.add(in.nextLine());
        System.out.println("Возраст");
        data.add(in.nextLine());
        data.add(formatForDateNow.format(dateNow));
        return data;
    }
}
