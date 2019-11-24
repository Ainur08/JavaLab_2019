package ru.multiclientchat.mainchat.server;

import ru.multiclientchat.server.MultiClientServer;
import static ru.multiclientchat.config.ConnectionConfig.setDbProperty;

public class ChatServerMain {
    public static void main(String[] args) {
        MultiClientServer multiClientServer =
                new MultiClientServer();
        setDbProperty();
        multiClientServer.start(Integer.parseInt(args[0]));
    }
}
