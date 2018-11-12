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
    private static Socket socket = null;
    private static ServerSocket ss = null;
    private static ObjectInputStream in = null;
    private static ObjectOutputStream out = null;
    
    public static void main(String[] args) throws IOException {
        ss = new ServerSocket(2000);
        
        try {
            System.out.println("Esperando cliente");
            socket = ss.accept();  //Recibe al cliente
            System.out.println("El cliente se ha conectado con la siguiente dirección: " + socket.getInetAddress());
            
            in = new ObjectInputStream( socket.getInputStream());
            out = new ObjectOutputStream( socket.getOutputStream());
            
            try {
                loop();  //Loop donde se escucha al usuario hasta que este decide salir
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Error de transmisión");
                e.printStackTrace();
            } finally {
                
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void loop() throws IOException, ClassNotFoundException {
        while (true) {
            String userInput = (String)(in.readObject());
            if (userInput.equals("-1"))
                break;
            else
                listenClient();
        }
    }
    
    private static void listenClient() {
        
    }
    
}
