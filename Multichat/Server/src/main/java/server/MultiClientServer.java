package server;

import context.Component;
import dispatcher.RequestsDispatcher;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class MultiClientServer implements Component {
    private RequestsDispatcher requestsDispatcher;
    private List<ClientHandler> clients;


    public MultiClientServer() {
        clients = new ArrayList<ClientHandler>();
    }

    public void start(int port) {
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        for (; ; ) {
            try {
                new ClientHandler(serverSocket.accept(), this).start();
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    @Override
    public String getComponentName() {
        return null;
    }

    public RequestsDispatcher getRequestsDispatcher() {
        return requestsDispatcher;
    }

    public void setRequestsDispatcher(RequestsDispatcher requestsDispatcher) {
        this.requestsDispatcher = requestsDispatcher;
    }

    public List<ClientHandler> getClients() {
        return clients;
    }

    public void setClients(List<ClientHandler> clients) {
        this.clients = clients;
    }
}
