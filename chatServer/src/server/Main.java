package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Socket socket;
        DataInputStream in;
        DataOutputStream out;


        try (ServerSocket serverSocket = new ServerSocket(8189)) {
            System.out.println("Server started");
            socket = serverSocket.accept();
            System.out.println("Client connected");
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            Thread outputThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true) {
                            out.writeUTF(scanner.nextLine());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            outputThread.start();
            while (true) {
                String str = in.readUTF();
                if (str.equals("/end")) {
                    break;
                }
                System.out.println("Client: " + str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
