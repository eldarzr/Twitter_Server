package bgu.spl.net.impl.BGSServer;

import bgu.spl.net.api.bidi.ConnectionHandler;
import bgu.spl.net.api.bidi.Connections;


import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Manneger {

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
        if(manneger == null)
            manneger = new Manneger();
        return manneger;
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
        loggedInUsers.put(user, true);
        userId.put(user2, connectionId);
        idUser.put(connectionId, user2);
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

    private boolean isUserLoggedIn(int cID) {
        if(!idUser.containsKey(cID))
            return false;
        User user = idUser.get(cID);
        if(!registeredUsers.containsKey(user.getUserName()))
            return false;
        if(!loggedInUsers.containsKey(user))
            return false;
        return loggedInUsers.get(user);

    }

    public User getUser(int connectionId){ return idUser.get(connectionId);}
}
