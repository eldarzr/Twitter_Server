package bgu.spl.net.impl.BGSServer;

import bgu.spl.net.impl.rci.ObjectEncoderDecoder;
import bgu.spl.net.impl.rci.RemoteCommandInvocationProtocol;
import bgu.spl.net.srv.Reactor;
import bgu.spl.net.srv.Server;
import jdk.nashorn.internal.runtime.regexp.joni.Regex;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TPCMain {
    public static void main(String[] args) {
        System.out.println("tpc");
        Server.threadPerClient(
                Integer.valueOf(args[0]), //port
                () -> new BidiMessagingProtocolImpl(), //protocol factory
                () -> new BidiMessageEncoderDecoder() //message encoder decoder factory
        ).serve();

    }
}
