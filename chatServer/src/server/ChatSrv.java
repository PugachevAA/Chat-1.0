package server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatSrv {
    private static final Logger LOGGER = LogManager.getLogger(ChatSrv.class.getName());
    private List<ClientHandler> clients;
    private AuthService authService;
    private ExecutorService service = Executors.newFixedThreadPool(10);

    public ChatSrv() {
        try(ServerSocket server = new ServerSocket(Config.PORT)) {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            authService = new BaseAuthService();
            authService.start();
            clients = new ArrayList<>();
            while (true) {
                LOGGER.info("Ожидается подключение клиента");
                Socket socket = server.accept();
                LOGGER.info("Клиент подключился");
                new ClientHandler(this, socket);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (authService != null) {
                authService.stop();
                service.shutdown();
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

    public ExecutorService getService() {
        return service;
    }

}
