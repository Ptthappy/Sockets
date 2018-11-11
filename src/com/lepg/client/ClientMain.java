package com.lepg.client;


import java.net.Socket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;


/**
 * @author Ptthappy
 * @version 08/11/2018
 */


public class ClientMain {
    
    public static void main(String[] args) throws IOException {
        Socket socket = null;
        ObjectInputStream in = null;
        ObjectOutputStream out = null;
        
        try {
            System.out.println("Connecting");
            socket = new Socket("localhost", 2000);
            System.out.println("Connected");
            System.out.println(socket.getInetAddress());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
