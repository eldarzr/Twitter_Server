package bgu.spl.net.impl.BGSServer;

import bgu.spl.net.api.bidi.BidiMessagingProtocol;
import bgu.spl.net.api.bidi.Connections;
import bgu.spl.net.impl.Objects.*;

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
        if (message.charAt(0) == '1' && message.charAt(1) != '2') {
            message = message.substring(1);
            Register register = new Register();
            register.execute(message, connectionId);
        } else if (message.charAt(0) == '2') {
            message = message.substring(1);
            Login login = new Login();
            login.execute(message, connectionId);
        } else if (message.charAt(0) == '3') {
            message = message.substring(1);
            Logout logout = new Logout();
            if(logout.execute("", connectionId))
                shouldTerminate = true;
        } else if (message.charAt(0) == '4') {
            message = message.substring(1);
            Follow follow = new Follow();
            follow.execute(message, connectionId);
            //shouldTerminate = true;
        } else if (message.charAt(0) == '5') {
            message = message.substring(1);
            Post post = new Post();
            post.execute(message, connectionId);
        }
        else if (message.charAt(0) == '6') {
            message = message.substring(1);
            PrivateMessage pm = new PrivateMessage();
            pm.execute(message, connectionId);
            //shouldTerminate = true;
        }
        else if (message.charAt(0) == '7') {
            //message = "";
            Logstat logstat = new Logstat();
            logstat.execute(message, connectionId);
            //shouldTerminate = true;
        } else if (message.charAt(0) == '8') {
            //message = "";
            message = message.substring(1);
            Stat stat = new Stat();
            stat.execute(message, connectionId);
            //shouldTerminate = true;
        }
        else if (message.charAt(0) == '1' && message.charAt(1) == '2') {
            message = message.substring(2);
            Block block = new Block();
            block.execute(message, connectionId);
            //shouldTerminate = true;
        }
        System.out.println("[" + LocalDateTime.now() + "]: " + message);
    }

    @Override
    public boolean shouldTerminate() {
        return shouldTerminate;
    }
}
