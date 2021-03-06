package org.wangxm.nio.zerocopy;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

public class NewIOClient {
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost",7001));

        String fileName = "jetbrains-agent.jar";
        FileChannel fileChannel = new FileInputStream(fileName).getChannel();

        long startTime = System.currentTimeMillis();

        // transferto 底层 0拷贝
        long transferCount = fileChannel.transferTo(0,fileChannel.size(),socketChannel);


        System.out.println("发送的总字节数=" + transferCount + " 耗时:"+ (System.currentTimeMillis() - startTime));
        fileChannel.close();
    }
}
