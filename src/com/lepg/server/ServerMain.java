package com.lepg.server;


import java.io.*;
import java.net.Socket;
import java.net.ServerSocket;


/**
 * @author Ptthappy
 * @version 08/11/2018
 */


public class ServerMain {
    private static Socket socket = null;
    private static ServerSocket ss = null;
    private static ObjectInputStream in = null;
    private static DataOutputStream out = null;
    
    public static void main(String[] args) throws IOException {
        ss = new ServerSocket(2000);
        
        try {
            System.out.println("Esperando cliente");
            socket = ss.accept();  //Recibe al cliente
            System.out.println("El cliente se ha conectado con la siguiente dirección: " + socket.getInetAddress());
            
            out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            in = new ObjectInputStream(socket.getInputStream());
            
            try {
                loop();  //Loop donde se escucha al usuario hasta que este decide salir
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Error de transmisión");
                e.printStackTrace();
            } finally {
                socket.close();
                in.close();
                out.close();
                ss.close();
                System.exit(0);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void loop() throws IOException, ClassNotFoundException {
        while (true) {
            System.out.println("ekisde");
            String userInput = (String)in.readObject();
            System.out.println(userInput);
            if (userInput.equals("-1")) {
                System.out.println("izi pisi tu gfa en visi");
                break;
            }
            else {
                respondClient(userInput);
            }
        }
    }
    
    private static void respondClient(String input) {
        System.out.println("Se recibió " + input);
    }
    
}
