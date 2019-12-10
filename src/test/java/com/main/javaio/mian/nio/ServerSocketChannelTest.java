package com.main.javaio.mian.nio;

import org.junit.jupiter.api.Test;

import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author xiaolin
 * @date 2019/12/3
 **/
public class ServerSocketChannelTest {
    /**
     * selecter 测试
     *
     * @throws Exception
     */
    @Test
    public void test3() throws Exception {
        Selector selector = Selector.open();
        //TODO 提供网络链接
        ServerSocketChannel channel = null;
        channel.configureBlocking(false);

        //支持多种状态合并 int interestSet = SelectionKey.OP_READ | SelectionKey.OP_WRITE;
        SelectionKey selectionKey = channel.register(selector, SelectionKey.OP_READ);

        while (true) {

            int readyChannels = selector.selectNow();

            if (readyChannels == 0) continue;


            Set<SelectionKey> selectedKeys = selector.selectedKeys();

            Iterator<SelectionKey> keyIterator = selectedKeys.iterator();

            while (keyIterator.hasNext()) {
                SelectionKey key = keyIterator.next();
                if (key.isAcceptable()) {
                    // a connection was accepted by a ServerSocketChannel.
                } else if (key.isConnectable()) {
                    // a connection was established with a remote server.
                } else if (key.isReadable()) {
                    // a channel is ready for reading
                } else if (key.isWritable()) {
                    // a channel is ready for writing
                }
                keyIterator.remove();
            }
        }


    }
}
