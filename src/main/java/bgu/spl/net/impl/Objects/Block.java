package bgu.spl.net.impl.Objects;

import bgu.spl.net.api.bidi.Command;
import bgu.spl.net.impl.BGSServer.Manneger;

public class Block implements Command<String> {
    @Override
    public boolean execute(String args, int connectionId) {
        Manneger manneger = Manneger.getInstance();
        String userName=args.substring(0,args.length()-1);
        String s = "12";
        Command command;
        if(manneger.block(userName,connectionId))
            command = new ACK();
        else
            command = new Error();
        command.execute(s,connectionId);
        return true;
    }
}
