package bgu.spl.net.impl.BGSServer;

import bgu.spl.net.api.bidi.Command;

import java.io.Serializable;

public class Register implements Command<String> {
    @Override
    public boolean execute(String arg, int connectionId) {
        String userName = null, password = null, birthday = null;
        for(String s : arg.split("\0")){
            if(userName == null)
                userName = s;
            else if(password == null)
                password = s;
            else birthday = s;
        }
        User user = new User(userName, password, birthday);
        Manneger manneger = Manneger.getInstance();
        String s = "1";
        Command command;
        if(manneger.register(user, connectionId))
            command = new ACK();
        else
            command = new Error();
        command.execute(s,connectionId);
        return true;
    }
}
