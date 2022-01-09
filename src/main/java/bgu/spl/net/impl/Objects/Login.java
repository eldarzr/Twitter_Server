package bgu.spl.net.impl.Objects;

import bgu.spl.net.api.bidi.Command;
import bgu.spl.net.impl.BGSServer.Manneger;

public class Login implements Command<String> {
    @Override
    public boolean execute(String arg, int connectionId) {
        String userName = null, password = null;
        char captcha = '\0';
        for(String s : arg.split("\0")){
            if(userName == null)
                userName = s;
            else if (password == null)
                password = s;
            else captcha = s.charAt(0);
        }
        if(captcha == '\0') {
            (new Error()).execute("2", connectionId);
            return true;
        }
        User user = new User(userName, password);
        Manneger manneger = Manneger.getInstance();
        String s = "2";
        Command command;
        if(manneger.login(user, connectionId))
            command = new ACK();
        else
            command = new Error();
        command.execute(s,connectionId);
        return true;
    }
}
