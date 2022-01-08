package bgu.spl.net.impl.BGSServer;

import bgu.spl.net.api.bidi.Command;

public class Post implements Command<String> {
    @Override
    public boolean execute(String args, int connectionId) {
        Manneger manneger = Manneger.getInstance();
      String content = args;
      String s = "5";
        Command command;
        if(manneger.post(content,connectionId))
            command = new ACK();
        else
            command = new Error();
        command.execute(s,connectionId);
        return true;
    }
}
