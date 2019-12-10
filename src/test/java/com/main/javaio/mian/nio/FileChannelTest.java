package com.main.javaio.mian.nio;

import org.junit.jupiter.api.Test;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @author xiaolin
 * @date 2019/12/3
 **/
public class FileChannelTest {
    /**
     * 测试文件通道
     */
    @Test
    public void test1() throws Exception {
        RandomAccessFile aFile = new RandomAccessFile("/Users/phoenix/Downloads/test.txt", "rw");
        //创建通道
        FileChannel inChannel = aFile.getChannel();

        //创建缓冲区
        ByteBuffer buf = ByteBuffer.allocate(2);

        //通道读取到缓存区
        int bytesRead = inChannel.read(buf);
        while (bytesRead != -1) {

            System.out.println("Read " + bytesRead);
            buf.flip();

            while (buf.hasRemaining()) {
                System.out.print((char) buf.get());
            }

            buf.clear();
            bytesRead = inChannel.read(buf);
        }
        aFile.close();
    }

    /**
     * Scatter 分散缓冲区，针对固定规格的数据数据传输
     *
     * @throws Exception
     */
    @Test
    public void test2() throws Exception {
        RandomAccessFile aFile = new RandomAccessFile("/Users/phoenix/Downloads/test.txt", "rw");
        //创建通道
        FileChannel inChannel = aFile.getChannel();

        //创建缓冲区
        ByteBuffer bufHeader = ByteBuffer.allocate(2);
        ByteBuffer bufBody = ByteBuffer.allocate(2);
        ByteBuffer[] bufferArray = {bufHeader, bufBody};


        //通道读取到缓存区
        long bytesRead = inChannel.read(bufferArray);
        while (bytesRead != -1) {

            System.out.println("Read " + bytesRead);

            Arrays.stream(bufferArray).forEach(ByteBuffer::flip);
            Arrays.stream(bufferArray).filter(ByteBuffer::hasRemaining).forEach(byteBuffer -> System.out.println(byteBuffer.get()));
            Arrays.stream(bufferArray).forEach(ByteBuffer::clear);

            bytesRead = inChannel.read(bufferArray);
        }
        aFile.close();

    }

    @Test
    public void test3() {
        String string1 = "Hello World";

        int index = string1.indexOf("World");
        System.out.println(index);
    }

    public class A extends B {
        String name;
    }

    public class B {
        String name;
    }

    @Test
    public void test4() {
        A a = new A();
        a.name = "a";
        System.out.println(a.name);
        System.out.println(((B) a).name);
    }

    @Test
    public void test5() {
        String x = "张三";
        String y = "张三";
        String z = new String("张三");
        System.out.println(x == y); // true
        System.out.println(x == z); // false
        System.out.println(x.equals(z)); // false
        System.out.println(x.hashCode());
        System.out.println(y.hashCode());
        System.out.println(z.hashCode());
    }

    @Test
    public void test6() {
        System.out.println(Math.round(-1.5));
    }

    @Test
    public void test7() {
        long start = System.nanoTime();
        String s = "12345678";
        byte[] bytes = s.getBytes();
        byte[] results = new byte[bytes.length];
        for (int i = bytes.length - 1, j = 0; i >= 0; i--, j++) {
            results[j] = bytes[i];
        }
        System.out.println(new String(results));
        System.out.println(System.nanoTime() - start);
    }

    @Test
    public void test71() {
        long start = System.nanoTime();
        String s = "12345678";
        byte[] value = s.getBytes();
        int count = s.length();
        int n = count - 1;
        for (int j = n; j >= n / 2; j--) {
            int k = n - j;
            byte cj = value[j];
            byte ck = value[k];
            value[j] = ck;
            value[k] = cj;

        }
        System.out.println(new String(value));
        System.out.println(System.nanoTime() - start);

    }

    @Test
    public void test8() {
        long start = System.nanoTime();
        String s = "12345678";
        System.out.println(new StringBuilder(s).reverse());
        System.out.println(System.nanoTime() - start);
    }

    @Test
    public void test9() {
        test7();
        test71();
        test8();
    }

    public class ThreadSafe extends Thread {
        public void run() {
            while (!isInterrupted()) { //非阻塞过程中通过判断中断标志来退出
                try {
                    Thread.sleep(5 * 1000);//阻塞过程捕获中断异常来退出
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;//捕获到异常之后，执行 break 跳出循环
                }
            }
        }
    }
}
