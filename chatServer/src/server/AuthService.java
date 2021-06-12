package server;

public interface AuthService {
    void start();
    String getNickByLoginPass(String login, String password);
    boolean rename(String newNick, String oldNick);
    void stop();
}
