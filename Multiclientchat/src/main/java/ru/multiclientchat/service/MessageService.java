package ru.multiclientchat.service;

import ru.multiclientchat.dao.MessageDaoImpl;
import ru.multiclientchat.model.Message;
import ru.multiclientchat.model.Payload;
import ru.multiclientchat.model.User;
import ru.multiclientchat.server.MultiClientServer;

import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.List;

public class MessageService {
    public void sendMessage(LinkedHashMap message, List<MultiClientServer.ClientHandler> clients, User user) {
        try {
            for (MultiClientServer.ClientHandler client :
                    clients) {
                PrintWriter out = new PrintWriter(client.getClientSocket().getOutputStream(), true);
                out.println("(" + message.get("dateTime") + ")" + "<" + user.getName() + ">: " + message.get("message"));
            }
            db(user.getId(), (String) message.get("dateTime"), (String) message.get("message"));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private void db(int id, String dateTime, String str) {
        MessageDaoImpl messageDao = new MessageDaoImpl();
        Message message = new Message(id, dateTime, str);
        messageDao.save(message);
    }
}
