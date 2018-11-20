package com.lepg.client;


import java.net.Socket;
import java.io.*;
import java.net.InetAddress;
import java.util.Scanner;

/**
 * @author Ptthappy
 * @version 08/11/2018
 */


public class ClientMain {
    private static Socket socket = null;
    private static Scanner s = new Scanner(System.in); //Scanner para primitivos
    private static Scanner s2 = new Scanner(System.in); //Scanner para el input
    private static InputStream in = null;
    private static ObjectOutputStream out = null;
    private static FileOutputStream fout = null;
    private static String userInput = null;
    private static File file = null;
    
    private static final String finalPath = "C:\\\\Users\\Ptthappy\\Downloads\\";
    private static final String path = "C:\\\\Users\\Ptthappy\\";
    
    private static byte[] buffer = new byte[32];
    
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
        
        s.close();
        s2.close();
        in.close();
        out.close();
        fout.close();
        socket.close();
        s.close();
        s2.close();
        
        System.exit(0);
    }
    
    private static boolean connectToServer() {
        try {
            System.out.println("Conectando");
            socket = new Socket("localhost", 2000);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = socket.getInputStream();
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
        System.out.println("Solicitud enviada. Esperando respuesta");
        listenToServer();
        System.out.println("Transmisión completa");
    }
    
    private static void listenToServer() throws IOException {
        String to;
        if (userInput.lastIndexOf('/') != -1)
            to = userInput.substring(userInput.lastIndexOf('/'));
        else if (userInput.lastIndexOf('\\') != -1)
            to = userInput.substring(userInput.lastIndexOf('\\'));
        else
            to = userInput;
        file = new File(finalPath, to);
        file.createNewFile();
        fout = new FileOutputStream(file);
        int x, y = 0;
        byte[] data = new byte[1024 * 100000];
        while((x = in.read()) != -1) {
            if (checkBuffers(x))
                break;
            
            data[y] = (byte)x;
            y++;
        }
        for (int i = 0; i < y - 31; i++) {
            fout.write(data[i]);
        }
    }
    
    private static boolean checkBuffers(int next) {
        
        for (int i = 31; i > 0; i--) {
            buffer[i] = buffer[i - 1];
        }
        buffer[0] = (byte)next;
        
        for (int i = 0; i < 32; i++) {
            if(buffer[i] != 85)
                return false;
        }
        return true;
    }
    
}
