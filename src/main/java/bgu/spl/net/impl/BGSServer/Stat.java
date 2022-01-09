package bgu.spl.net.impl.BGSServer;


import bgu.spl.net.api.bidi.Command;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Stat implements Command<String> {
    @Override
    public boolean execute(String arg, int connectionId) {
        String s = arg;
        if(arg.length() == 0){
            new Error().execute("8", connectionId);
            return true;
        }
        arg = arg.substring(0,arg.length()-1);

        Manneger manneger = Manneger.getInstance();
        Command command;
        if(manneger.isUserLoggedIn(connectionId)) {
            User currentUser = manneger.getUser(connectionId);
            for (String userName : arg.split("\\|")) {
                User u = manneger.getUser(userName);
                if (u != null && !currentUser.isBlocked(u)) {
                    command = new ACK();
                    s = "8" + "\0" + u.getStat();
                    command.execute(s, connectionId);
                }
                else {
                    command = new Error();
                    command.execute("8", connectionId);
                }
            }
        }
        else {
            command = new Error();
            command.execute("8", connectionId);
        }
        return true;
    }
}
