package bgu.spl.net.impl.BGSServer;

import bgu.spl.net.api.bidi.Command;

import java.io.Serializable;

public class Logout implements Command<String> {
    @Override
    public void execute(String arg, int connectionId) {
        Manneger manneger = Manneger.getInstance();
        String s = "3";
        Command command;
        if(manneger.logout(connectionId)) {
            command = new ACK();
            command.execute(s,connectionId);
            manneger.getConnections().disconnect(connectionId);
        }
        else
            command = new Error();
        command.execute(s,connectionId);
    }
}
