package ru.multiclientchat.server;

import ru.multiclientchat.controller.PayloadController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MultiClientServer {
    private ServerSocket serverSocket;
    private List<ClientHandler> clients;

    public MultiClientServer() {
        clients = new ArrayList<ClientHandler>();
    }

    public void start(int port) {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        for (; ; ) {
            try {
                new ClientHandler(serverSocket.accept()).start();
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    public class ClientHandler extends Thread {
        private Socket clientSocket;
        private BufferedReader reader;
        private String login;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
            clients.add(this);
            try {
                reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }

        public Socket getClientSocket() {
            return clientSocket;
        }

        public void setClientSocket(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try {
                reader = new BufferedReader(
                        new InputStreamReader(
                                clientSocket.getInputStream()));
                PayloadController payloadController = new PayloadController(clientSocket, clients, this);
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                    payloadController.handleRequest(line);
                }
                reader.close();
                clientSocket.close();
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
    }
}
