package bgu.spl.net.impl.BGSServer;

import bgu.spl.net.api.bidi.Command;

import java.io.Serializable;

public class Logstat implements Command<String> {
    @Override
    public boolean execute(String arg, int connectionId) {
        Manneger manneger = Manneger.getInstance();
        String s = "7";
        User user = manneger.getUser(connectionId);
        Command command;
        if(manneger.isUserLoggedIn(connectionId))
            for(User u : manneger.getAllUsers()) {
                if(u != user && !u.isBlocked(user)){
                    command = new ACK();
                    s = "7" + "\0" + u.getStat();
                    command.execute(s,connectionId);
                }
            }
        else {
            command = new Error();
            command.execute(s, connectionId);
        }
        return true;
    }
}
