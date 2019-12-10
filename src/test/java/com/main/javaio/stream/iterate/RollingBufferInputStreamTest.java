package com.main.javaio.stream.iterate;

import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.InputStream;


/**
 * @author xiaolin
 * @date 2019/12/2
 **/
class RollingBufferInputStreamTest {
    int blockSize = 1024;
    byte[] buffer = new byte[blockSize * 4];

    @Test
    public void test() throws Exception {
        InputStream sourceInputStream = new FileInputStream("/Users/phoenix/Downloads/test.txt");
        RollingBufferInputStream bufferInput =
                new RollingBufferInputStream(sourceInputStream, buffer);

        while (bufferInput.hasAvailableBytes(1)) {

            boolean matchFound = lookForMatch(
                    bufferInput.getBuffer(),
                    bufferInput.getStart(),
                    bufferInput.getEnd());

            if (matchFound) {
                bufferInput.moveStart(blockSize);
                System.out.println("move blockSize");
            } else {
                bufferInput.moveStart(1);
                System.out.println("move 1");
            }
        }
    }

    private boolean lookForMatch(byte[] buffer, int start, int end) throws Exception{
        byte b = buffer[start];
        byte[] bs = {b};
        System.out.println(b);
        System.out.println(new String(bs,"UTF-8"));
        if (b == 1){
            return true;
        }
        return false;
    }
}