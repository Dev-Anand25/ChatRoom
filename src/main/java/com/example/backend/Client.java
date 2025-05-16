package com.example.backend;
import com.example.gui.ChatRoom;

import java.io.*;
import java.net.*;

public class Client {
    private static String clientmessage;
    private PrintWriter out;
    private BufferedReader in;
    private ChatRoom chatroom;


    public Client(String host, int port, ChatRoom chatroom, String user, String rname) throws IOException {
        Socket socket = new Socket(host, port);
        this.chatroom = chatroom;
        System.out.println("Connected to server.");
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        out.println(user);
        out.println(rname);

        new Thread(() -> {
            String message;
            try {
                while ((message = in.readLine()) != null) {
                    chatroom.display(message);
                }
            } catch (IOException e) {
                System.out.println("Connection closed.");
            }
        }).start();

    }

    public void getMessage(String outmessage){
        clientmessage = outmessage;
        out.println(clientmessage);
    }
}
