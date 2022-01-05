package bgu.spl.net.impl.BGSServer;

import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListSet;

public class User implements Comparable {

    private String userName;
    private String password;
    private String birthday;
    private Queue<String> awaitMessage;
    private Set<User> following;
    private Set<User> followers;
    private Set<User> blocked;

    public User(String userName, String password, String birthday) {
        this.userName = userName;
        this.password = password;
        this.birthday = birthday;
        awaitMessage = new ConcurrentLinkedQueue<>();
        following= new ConcurrentSkipListSet<>();
        followers= new ConcurrentSkipListSet<>();
        blocked= new ConcurrentSkipListSet<>();
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

    public boolean follow(User fUser) {
        if(following.contains(fUser))
            return false;
        if(fUser.isBlocked(this))
            return false;
        following.add(fUser);
        fUser.followers.add(this);
        return true;
    }

    public boolean block(User fUser) {
        if(blocked.contains(fUser))
            return false;
        this.unfollow(fUser);
        fUser.unfollow(this);
        blocked.add(fUser);
        return true;
    }

    public boolean unfollow(User fUser) {
        if(!following.contains(fUser))
            return false;
        following.remove(fUser);
        fUser.followers.remove(this);
        return true;
    }

    @Override
    public int compareTo(Object o) {
        if(!(o instanceof User))
            return -1;
        User u = (User)o;
        return u.userName.compareTo(userName);
    }

    private boolean isBlocked(User user){
        return blocked.contains(user);
    }
}
