package bgu.spl.net.impl.BGSServer;

import bgu.spl.net.srv.Reactor;

public class ReactorMain {
 public static void main(String[] args) {
  System.out.println("reactor");

  Reactor reactor = new Reactor(Integer.valueOf(args[0]),Integer.valueOf(args[1]),() -> new BidiMessagingProtocolImpl(),()-> new BidiMessageEncoderDecoder());
  reactor.serve();
 }
}
