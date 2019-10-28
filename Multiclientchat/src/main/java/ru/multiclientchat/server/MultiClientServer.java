package ru.multiclientchat.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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

    private class ClientHandler extends Thread {
        private Socket clientSocket;
        private BufferedReader reader;
        private String login;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
            clients.add(this);
            try {
                reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                this.login = reader.readLine();
                System.out.println("Новый участник: " + "<" + this.login + ">");
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }

        @Override
        public void run() {
            try {
                reader = new BufferedReader(
                        new InputStreamReader(
                                clientSocket.getInputStream()));
                String line;
                // участник подключился
                for (ClientHandler client :
                        clients) {
                    PrintWriter out = new PrintWriter(client.clientSocket.getOutputStream(), true);
                    out.println(this.login + " joined the chat");
                }

                while ((line = reader.readLine()) != null) {
                    // участник отключился
                    if (line.contains("stop")) {
                        for (ClientHandler client :
                                clients) {
                            PrintWriter out = new PrintWriter(client.clientSocket.getOutputStream(), true);
                            out.println(this.login + " left the chat");
                        }
                        removeClient();
                        break;
                        // участник отправил сообщение
                    } else {
                        for (ClientHandler client : clients) {
                            PrintWriter writer = new PrintWriter(
                                    client.clientSocket.getOutputStream(), true);
                            writer.println(line);
                        }
                    }
                }
                reader.close();
                clientSocket.close();
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }

        public void removeClient() {
            clients.remove(this);
        }
    }
}
