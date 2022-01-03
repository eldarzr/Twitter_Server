package bgu.spl.net.impl.BGSServer;

import bgu.spl.net.srv.Reactor;

public class ReactorMain {
 public static void main(String[] args) {

  Reactor reactor = new Reactor(2,1829,() -> new BidiMessagingProtocolImpl(),()-> new BidiMessageEncoderDecoder());
  reactor.serve();
 }
}
