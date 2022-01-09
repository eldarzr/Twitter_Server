package bgu.spl.net.impl.BGSServer;

import bgu.spl.net.api.bidi.MessageEncoderDecoder;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class BidiMessageEncoderDecoder implements MessageEncoderDecoder<String> {

    private byte[] bytes = new byte[1 << 10]; //start with 1k
    private int len = 0;
    final char END_CHAR = ';';

    @Override
    public String decodeNextByte(byte nextByte) {
        //notice that the top 128 ascii characters have the same representation as their utf-8 counterparts
        //this allow us to do the following comparison
        if (nextByte == END_CHAR) {
            return popString();
        }

        pushByte(nextByte);
        return null; //not a line yet
    }

    @Override
    public byte[] encode(String message) {
        try {
            short num = Short.valueOf(message);
            return shortToBytes(num);
        }
        catch (Exception e){

        }
        return (message).getBytes(); //uses utf8 by default
    }

    private void pushByte(byte nextByte) {
        if (len >= bytes.length) {
            bytes = Arrays.copyOf(bytes, len * 2);
        }

        bytes[len++] = nextByte;
    }

    private String popString() {
        //notice that we explicitly requesting that the string will be decoded from UTF-8
        //this is not actually required as it is the default encoding in java.

        short s = bytesToShort(bytes, 0);
        String result = new String(bytes, 2, len-2, StandardCharsets.UTF_8);
        //result = result.replace('\0', ' ');
        len -= 2;

        if(s == 4){
            short s1 = bytesToShort(bytes, 2);
            //result = new String(bytes, 2, len-2, StandardCharsets.UTF_8);
            result = result.substring(2);
            result = s1 + result;
        }
        len = 0;
        result = s + '\0' + result;
        return result;
    }

    private short bytesToShort(byte[] byteArr, int start)
    {
        short result = (short)((byteArr[start] & 0xff) << 8);
        result += (short)(byteArr[start + 1] & 0xff);
        return result;
    }

    private byte[] shortToBytes(short num)
    {
        byte[] bytesArr = new byte[2];
        bytesArr[0] = (byte)((num >> 8) & 0xFF);
        bytesArr[1] = (byte)(num & 0xFF);
        return bytesArr;
    }
}
