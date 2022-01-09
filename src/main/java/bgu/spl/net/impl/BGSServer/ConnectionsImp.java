package bgu.spl.net.impl.BGSServer;

import bgu.spl.net.api.bidi.ConnectionHandler;
import bgu.spl.net.api.bidi.Connections;

import java.io.IOException;
import java.util.HashMap;

public class ConnectionsImp implements Connections<String> {

    HashMap<Integer, ConnectionHandler> connectionsMap;
    Manneger manneger;

    public ConnectionsImp(){

        connectionsMap = new HashMap<>();
        this.manneger = Manneger.getInstance();
    }

    public void connect(int connectionId, ConnectionHandler connectionHandler) {
        if(!connectionsMap.containsKey(connectionId))
            connectionsMap.put(connectionId, connectionHandler);
    }

    @Override
    public boolean send(int connectionId, String msg) {
        if(connectionsMap.containsKey(connectionId)) {
            connectionsMap.get(connectionId).send(msg);
            return true;
        }
        return false;
    }

    @Override
    public void broadcast(String msg) {
        for (ConnectionHandler ch: connectionsMap.values()) {
            ch.send(msg);
        }
    }

    @Override
    public void disconnect(int connectionId) {
        if(connectionsMap.containsKey(connectionId)) {
            connectionsMap.remove(connectionId);
        }
    }
}
