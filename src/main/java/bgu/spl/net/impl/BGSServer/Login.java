package bgu.spl.net.impl.BGSServer;

import bgu.spl.net.api.bidi.Command;

import java.io.Serializable;

public class Login implements Command<String> {
    @Override
    public void execute(String arg, int connectionId) {
        String userName = null, password = null;
        for(String s : arg.split("\0")){
            if(userName == null)
                userName = s;
            else password = s;
        }
        User user = new User(userName, password, "");
        Manneger manneger = Manneger.getInstance();
        String s = "2";
        Command command;
        if(manneger.login(user, connectionId))
            command = new ACK();
        else
            command = new Error();
        command.execute(s,connectionId);
    }
}
