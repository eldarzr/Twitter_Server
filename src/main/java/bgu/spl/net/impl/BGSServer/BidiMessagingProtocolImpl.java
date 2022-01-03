package bgu.spl.net.impl.BGSServer;

import bgu.spl.net.api.bidi.BidiMessagingProtocol;
import bgu.spl.net.api.bidi.Command;
import bgu.spl.net.api.bidi.ConnectionHandler;
import bgu.spl.net.api.bidi.Connections;
import sun.rmi.runtime.Log;

import java.time.LocalDateTime;

public class BidiMessagingProtocolImpl implements BidiMessagingProtocol<String> {

    private boolean shouldTerminate = false;
    private int connectionId = 0;
    private Connections connections = null;

    @Override
    public void start(int connectionId, Connections connections) {
        this.connectionId = connectionId;
        this.connections = connections;
    }

    @Override
    public void process(String message) {
        System.out.println(message);
        if(message.charAt(0) == '1'){
            message = message.substring(1);
            Register register = new Register();
            register.execute(message,connectionId);
        }
        else if(message.charAt(0) == '2'){
            message = message.substring(1);
            Login login = new Login();
            login.execute(message,connectionId);
        }
        else if(message.charAt(0) == '3'){
            message = message.substring(1);
            Logout logout = new Logout();
            logout.execute("",connectionId);
            //shouldTerminate = true;
        }
        else if(message.charAt(0) == '4'){
            message = message.substring(1);
            Follow follow = new Follow();
            follow.execute(message,connectionId);
            //shouldTerminate = true;
        }
        System.out.println("[" + LocalDateTime.now() + "]: " + message);
    }

    @Override
    public boolean shouldTerminate() {
        return shouldTerminate;
    }
}
