public class User {
    private String login;
    private boolean isAuth = false;

    public User() {
        //this.login = "";
        //this.isAuth = false;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public boolean isAuth() {
        return isAuth;
    }

    public void setAuth(boolean auth) {
        isAuth = auth;
    }
}
