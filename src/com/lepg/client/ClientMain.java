package com.lepg.client;


import java.net.Socket;
import java.io.*;
import java.util.Scanner;

/**
 * @author Ptthappy
 * @version 08/11/2018
 */


public class ClientMain {
    private static Socket socket = null;
    private static Scanner s = new Scanner(System.in); //Scanner para primitivos
    private static Scanner s2 = new Scanner(System.in); //Scanner para el input
    private static DataInputStream in = null;
    private static ObjectOutputStream out = null;
    private static FileOutputStream fout = null;
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
                System.err.println("Entrada inválida");
            }
            if (connected)
                break;
        }  //Este loop hará la conexión con el servidor. Esto se hace para que no tire una excepción por si el servidor no está listo
        
        loop();  //Toda la transmisión está aquí
        
        out.writeObject("-1");
        
        if (out != null) out.close();
        if (in != null) in.close();
        if (socket != null) socket.close();
        if (s != null) s.close();
        if (s2 != null) s2.close();
        
        System.exit(0);
    }
    
    private static boolean connectToServer() {
        try {
            System.out.println("Conectando");
            File file = new File ("test\\culis.txt");
            file.createNewFile();
            socket = new Socket("localhost", 2000);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            fout = new FileOutputStream(file);
            System.out.println("Conexión exitosa");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    private static void loop() throws IOException {
        while (true) {
            System.out.println("Ingresar la dirección y el archivo a enviar. Ingrese una línea vacía para salir");
            userInput = s2.nextLine();
            //Desktop/Test/TestFile.exe Por ejemplo
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
        listenToServer();
    }
    
    private static void listenToServer() throws IOException {
        byte[] data = new byte[1024 * 16];
        int count;
        while((count = in.read(data)) > 0) {
            fout.write(data, 0, count);
        }
    }
    
}
