package bgu.spl.net.impl.BGSServer;

import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class User {

    private String userName;
    private String password;
    private String birthday;
    private Queue<String> awaitMessage;

    public User(String userName, String password, String birthday) {
        this.userName = userName;
        this.password = password;
        this.birthday = birthday;
        awaitMessage = new ConcurrentLinkedQueue<>();
    }

    public void addMessage(String msg){
        awaitMessage.add(msg);
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getBirthday() {
        return birthday;
    }

    public Queue<String> getAwaitMessage() {
        return awaitMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userName.equals(user.userName) && password.equals(user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, password);
    }
}
