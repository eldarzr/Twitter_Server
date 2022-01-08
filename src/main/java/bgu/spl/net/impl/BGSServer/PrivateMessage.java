package bgu.spl.net.impl.BGSServer;

import bgu.spl.net.api.bidi.Command;

public class PrivateMessage implements Command<String> {
    @Override
    public boolean execute(String args, int connectionId) {
        Manneger manneger = Manneger.getInstance();
        String content = args;
        String s = "6";
        Command command;
        if(manneger.sendPM(content,connectionId))
            command = new ACK();
        else
            command = new Error();
        command.execute(s,connectionId);
        return true;
    }
}
