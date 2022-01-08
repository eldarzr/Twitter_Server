package bgu.spl.net.impl.BGSServer;


import bgu.spl.net.api.bidi.Command;

public class Stat implements Command<String> {
    @Override
    public boolean execute(String arg, int connectionId) {
        String s = arg;
        Manneger manneger = Manneger.getInstance();
        Command command;
        if(manneger.isUserLoggedIn(connectionId))
            for(String userName : arg.split("|")) {
                User u = manneger.getUser(userName);
                if(u != null) {
                    command = new ACK();
                    s = "8" + "\0" + u.getStat();
                    command.execute(s, connectionId);
                }
            }
        else {
            command = new Error();
            command.execute(s, connectionId);
        }
        return true;
    }
}
