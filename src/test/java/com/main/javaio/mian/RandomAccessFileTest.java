package com.main.javaio.mian;

import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.io.RandomAccessFile;

/**
 * @author xiaolin
 * @date 2019/12/3
 **/
public class RandomAccessFileTest {

    /**
     * 测试文件指针
     *
     * @throws Exception
     */
    @Test
    public void test1() throws Exception {
        RandomAccessFile randomAccessFile = new RandomAccessFile("/Users/phoenix/Downloads/test.txt", "rw");

        randomAccessFile.seek(2);
        long filePointer = randomAccessFile.getFilePointer();
        Assert.isTrue(filePointer == 2L, "文件指针定位成功");
        randomAccessFile.close();
    }

    /**
     * 测试文件读取
     * @throws Exception
     */
    @Test
    public void test2() throws Exception{
        RandomAccessFile randomAccessFile = new RandomAccessFile("/Users/phoenix/Downloads/test.txt", "rw");

        randomAccessFile.seek(2);
        byte read = randomAccessFile.readByte();
//        byte[] reads = {read};
//        System.out.println(new String(reads,"UTF-8"));
        Assert.isTrue(read == 2, "文件指针定位成功");
        randomAccessFile.close();
    }

    /**
     * 测试写文件
     * @throws Exception
     */
    @Test
    public void test3() throws Exception{
        RandomAccessFile randomAccessFile = new RandomAccessFile("/Users/phoenix/Downloads/test.txt", "rw");
        randomAccessFile.seek(randomAccessFile.length());
        randomAccessFile.writeByte(10);
        randomAccessFile.writeByte(55);
        randomAccessFile.close();
    }
}
