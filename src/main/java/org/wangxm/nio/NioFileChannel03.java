package org.wangxm.nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NioFileChannel03 {
    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream = new FileInputStream("1.txt");
        FileChannel fileChannel1 = fileInputStream.getChannel();


        FileOutputStream fileOutputStream = new FileOutputStream("2.txt");
        FileChannel fileChannel2 = fileOutputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(512);


        while (true){
            // 清空buffer
            byteBuffer.clear();
            int read = fileChannel1.read(byteBuffer);
            if(read == -1){
                break;
            }
            // 将buffer中的数据写入到fileChannel2中  2.txt
            byteBuffer.flip();
            fileChannel2.write(byteBuffer);
        }

        fileInputStream.close();
        fileOutputStream.close();
    }
}
