package bgu.spl.net.impl.Objects;

import bgu.spl.net.api.bidi.Command;
import bgu.spl.net.impl.BGSServer.Manneger;

public class Logout implements Command<String> {
    @Override
    public boolean execute(String arg, int connectionId) {
        Manneger manneger = Manneger.getInstance();
        String s = "3";
        Command command;
        if(manneger.logout(connectionId)) {
            command = new ACK();
            command.execute(s,connectionId);
            return true;
        }
        else
            command = new Error();
        command.execute(s,connectionId);
        return false;
    }
}
