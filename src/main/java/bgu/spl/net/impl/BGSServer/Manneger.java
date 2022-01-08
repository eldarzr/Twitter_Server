package bgu.spl.net.impl.BGSServer;

import bgu.spl.net.api.bidi.ConnectionHandler;
import bgu.spl.net.api.bidi.Connections;


import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Manneger {
    private static class mannegerHolder{
        private static Manneger instance = new Manneger();
    }

    ConcurrentHashMap<String, User> registeredUsers; // <username, user>
    ConcurrentHashMap<User, Boolean> loggedInUsers; // is username loggedIn
    ConcurrentHashMap<User, Integer> userId; // <user, connectionId>
    ConcurrentHashMap<Integer, User> idUser; // <connectionId, user>
    Connections connections;
    AtomicInteger counter;
    static Manneger manneger = null;

    private Manneger() {
        this.registeredUsers = new ConcurrentHashMap<>();
        this.loggedInUsers = new ConcurrentHashMap<>();
        this.userId = new ConcurrentHashMap<>();
        this.idUser = new ConcurrentHashMap<>();
        counter = new AtomicInteger(0);
    }

    public static Manneger getInstance(){
        return mannegerHolder.instance;
    }

    public int addConnection(ConnectionHandler connectionHandler){
        int id = counter.getAndIncrement();
        connections.connect(id,connectionHandler);
        return id;
    }

    public boolean register(User user, int connectionId){
        if(registeredUsers.containsKey(user.getUserName()))
            return false;
        registeredUsers.put(user.getUserName(), user);
        loggedInUsers.put(user, false);
        return true;
    }

    public boolean login(User user, int connectionId){
        if(isUserLoggedIn(connectionId))
            return false;
        if(!registeredUsers.containsKey(user.getUserName()))
            return false;
        User user2 = registeredUsers.get(user.getUserName());
        if(!user2.equals(user))
            return false;
        if(loggedInUsers.get(user2))
            return false;
        loggedInUsers.put(user2, true);
        userId.put(user2, connectionId);
        idUser.put(connectionId, user2);
        user2.setcID(connectionId);
        user2.login();

        return true;
    }

    public void setConnections(Connections connections){
        this.connections = connections;
    }

    public Connections getConnections() {
        return connections;
    }

    public boolean logout(int connectionId) {
        if(!isUserLoggedIn(connectionId))
            return false;
        User user = idUser.get(connectionId);
        loggedInUsers.put(user, false);
        userId.remove(user);
        idUser.remove(connectionId);
        user.logout();
        return true;
    }

    public boolean follow(String userName, int setF,int cID) {
        if(!isUserLoggedIn(cID))
            return false;
        User u = registeredUsers.get(userName);
        if (!registeredUsers.containsKey(userName))
            return false;
        User fUser = registeredUsers.get(userName);
        User currentUser=idUser.get(cID);
        if(currentUser.equals(fUser)) {return false;}
        if(setF==0){
            return currentUser.follow(fUser);
        }
        return currentUser.unfollow(fUser);
    }

    public boolean block(String userName,int cID) {
        if(!isUserLoggedIn(cID))
            return false;
        User u = registeredUsers.get(userName);
        if (!registeredUsers.containsKey(userName))
            return false;
        User fUser = registeredUsers.get(userName);
        User currentUser=idUser.get(cID);
        return currentUser.block(fUser);
    }

    public boolean isUserLoggedIn(int cID) {
        if(!idUser.containsKey(cID))
            return false;
        User user = idUser.get(cID);
        if(!registeredUsers.containsKey(user.getUserName()))
            return false;
        if(!loggedInUsers.containsKey(user))
            return false;
        return loggedInUsers.get(user);

    }


        public boolean post(String content, int connectionId) {
            User curentUser = getUser(connectionId);
            if (curentUser == null || !isUserLoggedIn(connectionId))
                return false;
            content = curentUser.getUserName() + "\0" + content + "\0";
            Matcher m = Pattern.compile("@(\\w+)").matcher(content);
            Set<User> userFollowers = curentUser.getAllFollowers();
            Set<User> taggedFollowers = new ConcurrentSkipListSet<>();
            while (m.find()) {
                String userName = m.group().substring(1);
                User sendUser = getUser(userName);
                if (sendUser != null) {
                    taggedFollowers.add(sendUser);
                }
            }
            for (User u : userFollowers) {
                //int uCID = userId.get(u);
                u.postMsg(content);
            }
            for (User u : taggedFollowers) {
                //int uCID = userId.get(u);
                u.postMsg(content);
            }
/*        String contentCopy=content;
        while (contentCopy.contains("@")){
             int userPlace = contentCopy.indexOf('@');
             contentCopy = contentCopy.substring(userPlace);
             int indexOfBlank = contentCopy.indexOf(" ");
             String userName = contentCopy.substring(1,indexOfBlank);
        }*/

            return true;
        }

    public User getUser(int connectionId){
        if(!idUser.containsKey(connectionId))
            return null;
        return idUser.get(connectionId);
    }

    public User getUser(String userName){
        if(!registeredUsers.containsKey(userName))
            return null;
        return registeredUsers.get(userName);
    }

    public Collection<User> getAllUsers(){
        return registeredUsers.values();
    }
}
