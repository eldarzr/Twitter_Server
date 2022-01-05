package bgu.spl.net.impl.BGSServer;

import bgu.spl.net.api.bidi.Command;

public class Block implements Command<String> {
    @Override
    public void execute(String args, int connectionId) {
        Manneger manneger = Manneger.getInstance();
        //int setF=args.charAt(0) - '0';
        String userName=args;
        String s = "12";
        Command command;
        if(manneger.block(userName,connectionId))
            command = new ACK();
        else
            command = new Error();
        command.execute(s,connectionId);

    }
}
