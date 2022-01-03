package bgu.spl.net.impl.BGSServer;

import bgu.spl.net.impl.rci.ObjectEncoderDecoder;
import bgu.spl.net.impl.rci.RemoteCommandInvocationProtocol;
import bgu.spl.net.srv.Reactor;
import bgu.spl.net.srv.Server;

public class TPCMain {
    public static void main(String[] args) {
        Server.threadPerClient(
                7777, //port
                () -> new BidiMessagingProtocolImpl(), //protocol factory
                () -> new BidiMessageEncoderDecoder() //message encoder decoder factory
        ).serve();

    }
}
