package bgu.spl.net.impl.BGSServer;

import bgu.spl.net.api.bidi.Command;

import java.io.Serializable;

public class Notification implements Command<String> {
    @Override
    public void execute(String args, int connectionId) {
        Manneger manneger = Manneger.getInstance();
        manneger.getConnections().send(connectionId,"9");
//        String noType = "" + args.charAt(0);
//        args = args.substring(1);
//        manneger.getConnections().send(connectionId,noType);
//        User user = manneger.getUser(connectionId);
//        if(user == null)
//            return;
//        String postingUser = user.getUserName();
//        manneger.getConnections().send(connectionId,postingUser + "\0");
        manneger.getConnections().send(connectionId,args);
        manneger.getConnections().send(connectionId,";");

    }
}
