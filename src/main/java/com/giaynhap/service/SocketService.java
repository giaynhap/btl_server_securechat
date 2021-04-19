package com.giaynhap.service;

import org.springframework.stereotype.Service;

import java.io.*;
import java.net.Socket;

@Service
public class SocketService {
    private Socket clientSocket;
//    private PrintWriter out;
//    private BufferedReader in;
    private DataInputStream in;
    private DataOutputStream out;

    public void startConnection(String ip, int port) throws IOException {
        clientSocket = new Socket(ip, port);
        in = new DataInputStream(clientSocket.getInputStream());
        out = new DataOutputStream(clientSocket.getOutputStream());
//        out = new PrintWriter(clientSocket.getOutputStream(), true);
//        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    public void startConnection() throws IOException {
        clientSocket = new Socket("127.0.0.1", 37713);
        in = new DataInputStream(clientSocket.getInputStream());
        out = new DataOutputStream(clientSocket.getOutputStream());
//        out = new PrintWriter(clientSocket.getOutputStream(), true);
//        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    public String sendMessage(String msg) throws IOException {
        System.out.println("\n\n\n\n Pre-send MSG: " + msg + "\n\n\n\n");
        out.writeUTF(msg);
        System.out.println("\n\n\n\n Post-send MSG: " + msg + "\n\n\n\n");
        return in.readUTF();
    }

    public void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }
}