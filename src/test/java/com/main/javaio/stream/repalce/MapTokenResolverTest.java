package com.main.javaio.stream.repalce;

import org.assertj.core.internal.Maps;
import org.junit.jupiter.api.Test;

import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author xiaolin
 * @date 2019/12/3
 **/
class MapTokenResolverTest {

    /**
     * 字符串替换
     * 1、repalce
     * 2、stream方式
     *
     * @throws Exception
     */
    @Test
    public void test() throws Exception {
        String data = "1234567890";   // imagine a large string loaded from a file

        long time = System.nanoTime();
        data.replace("12", "ab")
                .replace("34", "cd")
                .replace("56", "ef")
                .replace("78", "gh")
                .replace("90", "ij");
        System.out.println(System.nanoTime() - time);

        String data1 = "${12}${34}${56}${78}${90}";   // imagine a large string loaded from a file

        Map<String, String> maps = new HashMap<>();
        maps.put("12", "ab");
        maps.put("34", "cd");
        maps.put("56", "ef");
        maps.put("78", "gh");
        maps.put("90", "ij");
        MapTokenResolver resolver = new MapTokenResolver(maps);
        Reader source =
                new StringReader(data1);
        Reader reader = new TokenReplacingReader(source, resolver);

        long time1 = System.nanoTime();

        int data1String = reader.read();
        while (data1String != -1) {
//            System.out.print((char) data1String);
            data1String = reader.read();
        }
        System.out.println(System.nanoTime() - time1);
    }

    @Test
    void resolveToken() throws Exception {
        Map<String, String> tokens = new HashMap<String, String>();
        tokens.put("token1", "value1");
        tokens.put("token2", "JJ ROCKS!!!");

        MapTokenResolver resolver = new MapTokenResolver(tokens);

        Reader source =
                new StringReader("1234567890${token1}abcd${token1}efg${token2}XYZ$000");

        Reader reader = new TokenReplacingReader(source, resolver);

        int data = reader.read();
        while (data != -1) {
            System.out.print((char) data);
            data = reader.read();
        }
    }
}