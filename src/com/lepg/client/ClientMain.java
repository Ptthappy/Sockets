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
    private static Scanner s = new Scanner(System.in); //Scanner para primitivos
    private static Scanner s2 = new Scanner(System.in); //Scanner para el input
    private static ObjectInputStream in = null;
    private static ObjectOutputStream out = null;
    private static String userInput = null;
    
    public static void main(String[] args) throws IOException, InterruptedException  {
        System.out.println("1. Conectarse al servidor\n2. Salir");
        
        while (true) {
            boolean connected = false;
            switch(s.nextInt()) {
            case 1:
                connected = connectToServer();
                break;
                
            case 2:
                System.exit(0);
                
            default:
                System.err.println("Entráda inválida");
            }
            if (connected)
                break;
        }  //Este loop hará la conexión con el servidor. Esto se hace para que no tire una excepción por si el servidor no está listo
        
        loop();  //Toda la transmisión está aquí
        
        out.writeObject("-1");  //Le avisa al servidor que el cliente se va a desconectar
        Thread.sleep(1000);
        System.exit(0);
    }
    
    private static boolean connectToServer() {
        try {
            System.out.println("Conectando");
            socket = new Socket("localhost", 2000);
            System.out.println("Conexión exitosa");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    private static void loop() {
        while (true) {
            System.out.println("Ingresar la dirección y el archivo a enviar. Ingrese una línea vacía para salir");
            //Desktop/Test/TestFile.exe Por ejemplo
            userInput = s2.nextLine();
            if (userInput.equals(""))
                break;
            else {
                try {
                    speakToServer();
                } catch (IOException e) {
                    e.printStackTrace();
                    System.exit(0);
                }
            }
        }
    }
    
    private static void speakToServer() throws IOException {
        out.writeObject(userInput);
        System.out.println("Solicitud enviada");
    }
    
}
