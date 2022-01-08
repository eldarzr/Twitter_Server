package bgu.spl.net.impl.BGSServer;

import bgu.spl.net.api.bidi.Command;

import java.io.Serializable;

public class Error implements Command<String> {
    @Override
    public boolean execute(String args, int connectionId) {
        Manneger manneger = Manneger.getInstance();
        manneger.getConnections().send(connectionId,"11");
        manneger.getConnections().send(connectionId,args);
        manneger.getConnections().send(connectionId,";");
        return true;
    }
}
