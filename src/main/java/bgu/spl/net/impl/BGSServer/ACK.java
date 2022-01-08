package bgu.spl.net.impl.BGSServer;

import bgu.spl.net.api.bidi.Command;

import java.io.Serializable;

public class ACK implements Command<String> {
    @Override
    public boolean execute(String args, int connectionId) {
        Manneger manneger = Manneger.getInstance();
        manneger.getConnections().send(connectionId,"10");
        for(String s : args.split("\0"))
            manneger.getConnections().send(connectionId,s);
        manneger.getConnections().send(connectionId,";");
        return true;
    }
}
