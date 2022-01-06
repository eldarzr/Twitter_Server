package bgu.spl.net.impl.BGSServer;

import bgu.spl.net.api.bidi.Command;
import com.sun.xml.internal.ws.api.message.Message;

import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import sun.util.resources.LocaleData;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;
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
    private Boolean loggedIn;
    private int cID;
    private LocalDate bd= LocalDate.of(1998,1,20);

    public User(String userName, String password, String birthday) {
        this.userName = userName;
        this.password = password;
        this.birthday = birthday;
        awaitMessage = new ConcurrentLinkedQueue<>();
        following = new ConcurrentSkipListSet<>();
        followers = new ConcurrentSkipListSet<>();
        blocked = new ConcurrentSkipListSet<>();
        this.loggedIn = false;
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
        if (following.contains(fUser))
            return false;
        if (fUser.isBlocked(this))
            return false;
        following.add(fUser);
        fUser.followers.add(this);
        return true;
    }

    public boolean block(User fUser) {
        if (blocked.contains(fUser))
            return false;
        this.unfollow(fUser);
        fUser.unfollow(this);
        blocked.add(fUser);
        return true;
    }

    public boolean unfollow(User fUser) {
        if (!following.contains(fUser))
            return false;
        following.remove(fUser);
        fUser.followers.remove(this);
        return true;
    }

    @Override
    public int compareTo(Object o) {
        if (!(o instanceof User))
            return -1;
        User u = (User) o;
        return u.userName.compareTo(userName);
    }

    private boolean isBlocked(User user) {
        return blocked.contains(user);
    }

    public Set<User> getAllFollowers() {
        return followers;
    }

    public void postMsg(String content/*, int cID*/) {
        synchronized (loggedIn) {
            if (loggedIn) {
                Command command = new Notification();
                content = "\0" + content;
                command.execute(content, cID);
            } else {
                awaitMessage.add(content);
            }
        }

    }

    public void login() {
        synchronized (loggedIn) {
            loggedIn = true;
            awakeMessage();
        }
    }

    private void awakeMessage() {
        for (String msg : awaitMessage) {
            Command command = new Notification();
            msg = "\0" + msg;
            command.execute(msg, cID);
        }
    }
        public void logout () {
            synchronized (loggedIn) {
                loggedIn = false;
            }
        }

    public void setcID(int cID) {
        this.cID = cID;}

    public String getStat(){
        int age = calculateAge();
        int numOfPosts = 0;
        int numFollowers = followers.size();
        int numFollowing = following.size();
        return age + "\0" + numOfPosts + "\0" + numFollowers + "\0" + numFollowing;
    }

    private int calculateAge() {
        LocalDate currentDate = LocalDate.now();
        return Period.between(bd, currentDate).getYears();
    }
}

