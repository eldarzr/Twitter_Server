package bgu.spl.net.impl.BGSServer;

import bgu.spl.net.api.bidi.Command;

public class Follow implements Command<String> {
    @Override
    public boolean execute(String args, int connectionId) {
        Manneger manneger = Manneger.getInstance();
        int setF=args.charAt(0) - '0';
        String userName=args.substring(1);
        String s = "4";
        Command command;
        if(manneger.follow(userName,setF,connectionId))
            command = new ACK();
        else
            command = new Error();
        command.execute(s,connectionId);
        return true;
    }
}
