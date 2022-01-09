package bgu.spl.net.impl.Objects;

import bgu.spl.net.api.bidi.Command;
import bgu.spl.net.impl.BGSServer.Manneger;

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
