package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatSrv {
    private List<ClientHandler> clients;
    private AuthService authService;

    public ChatSrv() {
        try(ServerSocket server = new ServerSocket(Config.PORT)) {
            authService = new BaseAuthService();
            authService.start();
            clients = new ArrayList<>();
            while (true) {
                System.out.println("Ждем подключения клиента");
                Socket socket = server.accept();
                System.out.println("Клиент подключился");
                new ClientHandler(this, socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (authService != null) {
                authService.stop();
            }
        }
    }


    public AuthService getAuthService() {
        return authService;
    }

    public synchronized void subscribe(ClientHandler ch) {
        clients.add(ch);
        System.out.println(clients);
    }
    public synchronized void unSubscribe(ClientHandler ch) {
        clients.remove(ch);
    }

    public synchronized void broadcastMsg(String msg) {
        for (ClientHandler ch : clients) {
            ch.sendMsg(msg);
        }
    }
    public synchronized boolean personalMsg(String name, String msg) {
        for (ClientHandler clientHandler : clients) {
            if (clientHandler.getName().equals(name)) {
                clientHandler.sendMsg(msg);
                return true;
            }
        }
        return false;
    }

    public synchronized boolean isNickBysy(String nick) {
        for (ClientHandler clientHandler : clients) {
            if (clientHandler.getName().equals(nick)) {
                return true;
            }
        }
        return false;
    }

}
