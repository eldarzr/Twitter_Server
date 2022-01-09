package bgu.spl.net.srv;

import bgu.spl.net.api.bidi.MessageEncoderDecoder;
import bgu.spl.net.api.bidi.BidiMessagingProtocol;
import bgu.spl.net.api.bidi.ConnectionHandler;
import bgu.spl.net.api.bidi.Connections;
import bgu.spl.net.impl.BGSServer.Manneger;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;

public class BlockingConnectionHandler<T> implements Runnable, ConnectionHandler<T> {

    private final BidiMessagingProtocol<T> protocol;
    private final MessageEncoderDecoder<T> encdec;
    private Manneger manneger;
    private Connections connections;
    private final Socket sock;
    private BufferedInputStream in;
    private BufferedOutputStream out;
    private int connectionId;
    private volatile boolean connected = true;

    public BlockingConnectionHandler(Socket sock, MessageEncoderDecoder<T> reader, BidiMessagingProtocol<T> protocol, Connections connections) {
        this.sock = sock;
        this.encdec = reader;
        this.protocol = protocol;
        manneger = Manneger.getInstance();
        this.connections = connections;
        connectionId = -1;
        //connections.connect(connectionId,this);
    }

    @Override
    public void run() {
        connectionId = manneger.addConnection(this);
        protocol.start(connectionId,connections);
        try (Socket sock = this.sock) { //just for automatic closing
            int read;

            in = new BufferedInputStream(sock.getInputStream());

            while (!protocol.shouldTerminate() && connected && (read = in.read()) >= 0) {
                T nextMessage = encdec.decodeNextByte((byte) read);
                if (nextMessage != null) {
//                    T response = protocol.process(nextMessage);
                    protocol.process(nextMessage);
/*                    if (response != null) {
                        out.write(encdec.encode(response));
                        out.flush();
                    }*/
                }
            }
            close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void close() throws IOException {
        connected = false;
        sock.close();
        if(connectionId != -1)
            connections.disconnect(connectionId);
    }

    @Override
    public void send(T msg) {
        try {
            out = new BufferedOutputStream(sock.getOutputStream());
            out.write(encdec.encode(msg));
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
