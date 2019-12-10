package com.main.javaio.mian;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/**
 * @author xiaolin
 * @date 2019/12/3
 **/
class PipeStreamTest {
    private static PipedOutputStream outputBuffer;
    static int times = 10;
    private final static Object watchObject = new Object();

    /**
     * 测试线程间字节流通信
     */
    @Test
    void test() throws Exception {
        Thread thread1 = new Thread(PipeStreamTest::run);
        Thread thread2 = new Thread(PipeStreamTest::run2);

        thread1.start();
        Thread.sleep(1000);
        thread2.start();
    }


    private static void run() {
        try (PipedOutputStream output = new PipedOutputStream()) {
            outputBuffer = output;
            Thread.currentThread().wait();
            synchronized (watchObject) {
                watchObject.wait();
            }
            output.write("Hello world, pipe!".getBytes());
        } catch (IOException e) {
            System.out.println(e.getCause());
        } catch (InterruptedException e) {
        }
    }

    private static void run2() {
        try (PipedInputStream input = new PipedInputStream(outputBuffer)) {
            synchronized (watchObject) {
                watchObject.notifyAll();
            }
            int data = input.read();
            while (data != -1) {
                System.out.print((char) data);
                data = input.read();
            }
        } catch (IOException e) {
            System.out.println(e.getCause());
        }
    }

    public static void main(String[] args) throws IOException {

        final PipedOutputStream output = new PipedOutputStream();
        final PipedInputStream input = new PipedInputStream(output);


        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    output.write("Hello world, pipe!".getBytes());
                } catch (IOException e) {
                }
            }
        });


        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    int data = input.read();
                    while (data != -1) {
                        System.out.print((char) data);
                        data = input.read();
                    }
                } catch (IOException e) {
                }
            }
        });

        thread1.start();
        thread2.start();

    }
}
