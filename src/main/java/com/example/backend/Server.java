package com.example.backend;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final int PORT = 1234;
    private static Map<String, Set<ClientHandler>> rooms = new HashMap<>();

    public void runServer() throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Server started...");

        do {
            Socket socket = serverSocket.accept();
            ClientHandler clientHandler = new ClientHandler();
            clientHandler.Handler(socket);
            clientHandler.start();
        } while (true);
    }


    private static class ClientHandler extends Thread {
        private Socket socket;
        private BufferedReader in;
        private PrintWriter out;
        private String room, name;


        public void Handler(Socket socket) {
            this.socket = socket;

            System.out.println("Client room " + room);
        }



        public void broadcast(String message) {
            out.println("Me: " + message);


            synchronized (rooms) {
                Set<ClientHandler> clients = rooms.get(room);
                if (clients != null) {
                    for (ClientHandler client : clients) {
                        if (client != this) {
                            client.out.println(name + ": " + message);
                        }
                    }
                }
            }
        }


        @Override
        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                name = in.readLine();
                room = in.readLine();

                synchronized (rooms) {
                    rooms.putIfAbsent(room, new HashSet<>());
                    rooms.get(room).add(this);
                }

                System.out.println("Client joined room: " + room);

                String message;
                while ((message = in.readLine()) != null) {
                    broadcast(message);
                }

            } catch (IOException e) {
                System.out.println("Error: " + e);
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    //ignore
                }
                if (room != null && out != null) {
                    synchronized (rooms) {
                        Set<ClientHandler> handlers = rooms.get(room);
                        if (handlers != null) {
                            handlers.remove(this);
                            broadcast( " left the room.");
                            if (handlers.isEmpty()) {
                                rooms.remove(room);
                            }
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        try {
            Server server = new Server();
            server.runServer();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}