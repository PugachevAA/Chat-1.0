package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler {
    private ChatSrv server;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private String name = "";
    private boolean isAuth = false;

    public ClientHandler(ChatSrv server, Socket socket) {
        try {
            this.server = server;
            this.socket = socket;
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());

            new Thread(() -> {
                try {
                    auth();
                    readMsg();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("Клиент отключился");
                    closeConnection();
                }
            }).start();
        } catch (IOException e) {
            System.out.println("Проблема при создании обработчика");
            e.printStackTrace();
        }
    }

    private void auth() throws IOException {
        while (!isAuth) {
            new Thread(() -> {
                long connectTime = System.currentTimeMillis();
                while (!isAuth) {
                    if (System.currentTimeMillis() - connectTime > 120000) {
                        System.out.println("Вышвырнули");
                        sendMsg("/end");
                        return;
                    }
                }
            }).start();
            String str = in.readUTF();
            // /auth login1 pass1
            if (str.startsWith("/auth")) {
                String[] parts = str.split(" ");
                String login = parts[1];
                String password = parts[2];
                String nick = server.getAuthService().getNickByLoginPass(login, password);
                if (nick != null) {
                    if (!server.isNickBysy(nick)) {
                        sendMsg("/authOk " + nick);
                        name = nick;
                        server.broadcastMsg(name + " зашел в чат");
                        server.subscribe(this);
                        isAuth = true;
                        return;
                    } else {
                        sendMsg("УЗ используется");
                    }

                } else {
                    sendMsg("Неверные логин/пароль");
                }
            } else {
                sendMsg("Зарегистрируйтесь перед отправкой сообщений");
            }
        }
    }

    public void readMsg() throws IOException {
        while (true) {

            String strFromClient = in.readUTF();
            if (strFromClient.startsWith("/w")) {
                System.out.println("Персональное сообщение");
                String[] parts = strFromClient.split(" ", 3);
                String nameTo = parts[1];
                String msg = parts[2];
                System.out.println("Для " + nameTo);
                if (server.isNickBysy(nameTo)) {
                    System.out.println("Отправлено");
                    if (nameTo.equals(name)) {
                        server.personalMsg(name,"Вы пытаетесь отправить сообщение себе");
                    } else {
                        server.personalMsg(nameTo, "(Лично)" + name + ": " + msg);
                        server.personalMsg(name, "(Лично для " + name + "): " + msg);
                    }
                }  else {
                    server.personalMsg(name, "Пользователь не найден");
                }
            } else {
                System.out.println("от " + name + ": " + strFromClient);
                server.broadcastMsg(name + ": " + strFromClient);
            }
            if (strFromClient.equals("/end")) {
                return;
            }
        }
    }

    public void sendMsg(String msg) {
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        server.unSubscribe(this);
        server.broadcastMsg(name + " вылеш из чата");
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }
}
