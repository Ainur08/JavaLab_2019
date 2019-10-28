package ru.multiclientchat.client;

import ru.multiclientchat.config.ConnectionConfig;
import ru.multiclientchat.dao.MessageDaoImpl;
import ru.multiclientchat.dao.UserDaoImpl;
import ru.multiclientchat.model.Message;
import ru.multiclientchat.model.User;
import ru.multiclientchat.service.LoginService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;
import java.text.SimpleDateFormat;

public class ChatClient {
    private static final ConnectionConfig CONFIG = ConnectionConfig.getInstance();
    private User user;
    private Message message;
    private String time;
    private String nickname;
    private Socket clientSocket;
    private PrintWriter writer; // поток чтения в сокет
    private BufferedReader reader; // поток чтения из сокета
    private BufferedReader inputUser; // поток чтения с консоли

    public void startConnection(String ip, int port) {
        try {
            clientSocket = new Socket(ip, port);
            writer = new PrintWriter(clientSocket.getOutputStream(), true);
            reader = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            LoginService service = new LoginService();
            this.user = service.isUserExists();
            new ReadMessage().start();
            new SendMessage().start();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private void db(int id, String dateTime, String str){
        MessageDaoImpl messageDao = new MessageDaoImpl(CONFIG.getConnection());
        message = new Message(id, dateTime, str);
        messageDao.save(message);
    }

    private class ReadMessage extends Thread {
        @Override
        public void run() {
            try {
                String message;
                while (true) {
                    message = reader.readLine();
                    if (message.equals("stop")) {
                        break;
                    }
                    System.out.println(message);
                }
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    private class SendMessage extends Thread {
        @Override
        public void run() {
            UserDaoImpl userDao = new UserDaoImpl(CONFIG.getConnection());
            while (true) {
                String userMessage;
                try {
                    time = new SimpleDateFormat("HH:mm:ss").format(new Date()); // время до секунд
                    userMessage = inputUser.readLine(); // сообщения с консоли
                    if (userMessage.equals("stop")) {
                        writer.write("Вышел" + "\n");
                        break;
                    } else {
                        writer.write("(" + time + ") " + nickname + ": " + userMessage + "\n"); // отправляем на сервер
                        user = userDao.findOneByUsername(nickname);
                        db(user.getId(), time, userMessage);
                    }
                } catch (IOException e) {
                    throw new IllegalStateException(e);
                }
            }
        }
    }
}

