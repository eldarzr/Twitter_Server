package bgu.spl.net.impl.Objects;

import bgu.spl.net.api.bidi.Command;
import bgu.spl.net.impl.BGSServer.Manneger;

import java.io.Serializable;

public class Notification implements Command<String> {
    @Override
    public boolean execute(String args, int connectionId) {
        Manneger manneger = Manneger.getInstance();
        manneger.getConnections().send(connectionId,"9");
        manneger.getConnections().send(connectionId,args);
        manneger.getConnections().send(connectionId,";");
        return true;
    }
}
