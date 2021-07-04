package org.wangxm.nio;

import java.nio.IntBuffer;

public class BasicBuffer {
    /*
     * 举例说明buffer的使用
     * */
    public static void main(String[] args) {
        IntBuffer intBuffer = IntBuffer.allocate(5);

        // 向buffer中存放数据
        for (int i = 0; i < intBuffer.capacity(); i++) {
            intBuffer.put(i * 2);
        }

        // 将buffer转换，读写转换
        intBuffer.flip();

        while (intBuffer.hasRemaining()) {
            System.out.println(intBuffer.get());
        }
    }
}
