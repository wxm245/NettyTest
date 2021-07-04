package org.wangxm.nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIOFileChannel01 {
    public static void main(String[] args) throws IOException {
        String str = "Hello World!";
        FileOutputStream outputStream = new FileOutputStream("file01.txt");

        // 通过输出流获取FileChannel
        FileChannel fileChannel = outputStream.getChannel();

        // 创建一个缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        // 将str放入byteBuffer
        byteBuffer.put(str.getBytes());
        byteBuffer.flip();

        // 将byteBuffer数据写入fileChannle
        fileChannel.write(byteBuffer);
        outputStream.close();

    }
}
