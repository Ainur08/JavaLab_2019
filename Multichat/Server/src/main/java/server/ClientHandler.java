package server;

import dispatcher.RequestsDispatcher;
import protocol.Response;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class ClientHandler extends Thread {
    private Socket clientSocket;
    private BufferedReader reader;
    private PrintWriter out;
    private MultiClientServer server;
    private RequestsDispatcher requestsDispatcher;
    private List<ClientHandler> clients;

    public ClientHandler(Socket clientSocket, MultiClientServer server) {
        this.clientSocket = clientSocket;
        this.server = server;
        clients = server.getClients();
        clients.add(this);
        try {
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void run() {
        try {
            requestsDispatcher = server.getRequestsDispatcher();
            requestsDispatcher.setClientHandler(this);
            String line;
            while ((line = reader.readLine()) != null) {
                requestsDispatcher.handleRequest(line);
            }
            reader.close();
            clientSocket.close();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public void sendMessage(Response response) {
        System.out.println(response.toJson());
        out.println(response.toJson());
    }

    public void sendMessageAllClient(Response response) {
        for (ClientHandler client :
                clients) {
            PrintWriter our = client.out;
            System.out.println(response.toJson());
            our.println(response.toJson());
        }
    }
}
