package com.lepg.client;


import java.net.Socket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.util.Scanner;

/**
 * @author Ptthappy
 * @version 08/11/2018
 */


public class ClientMain {
    private static Socket socket = null;
    private static Scanner s = new Scanner(System.in);
    private static ObjectInputStream in = null;
    private static ObjectOutputStream out = null;
    
    public static void main(String[] args) throws IOException {
        System.out.println("1. Conectarse al servidor\n2. Salir");
        
        while (true) {
            switch(s.nextInt()) {
            case 1:
                connectToServer();
                break;
                
            case 2:
                System.exit(0);
                
            default:
                System.err.println("Entráda inválida");
            }
        }
        /*
        try {
            System.out.println("Connecting");
            socket = new Socket("localhost", 2000);
            System.out.println("Connected");
            System.out.println(socket.getInetAddress());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }
    
    private static void connectToServer() {
        try {
            System.out.println("Conectando");
            socket = new Socket("localhost", 2000);
            System.out.println("Conexión exitosa");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
