package com.lepg.server;


import java.io.IOException;
import java.net.Socket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;


/**
 * @author Ptthappy
 * @version 08/11/2018
 */


public class ServerMain {
    
    
    public static void main(String[] args) throws IOException {
        Socket socket = null;
        ServerSocket ss = new ServerSocket(2000);
        ObjectInputStream in = null;
        ObjectOutputStream out = null;
        try {
            System.out.println("Esperando cliente");
            socket = ss.accept();
            System.out.println("El cliente se ha conectado con la siguiente dirección: " + socket.getInetAddress());
            
            in = new ObjectInputStream( socket.getInputStream());
            out = new ObjectOutputStream( socket.getOutputStream());
            
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    
}
