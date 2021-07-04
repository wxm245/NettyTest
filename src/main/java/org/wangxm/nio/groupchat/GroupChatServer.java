package org.wangxm.nio.groupchat;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

public class GroupChatServer {
    private Selector selector;
    private ServerSocketChannel serverSocketChannel;
    private static final int PORT = 6667;

    public GroupChatServer() {
        try {
            selector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress(PORT));
            serverSocketChannel.configureBlocking(false);

            // 注册到selector
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException ex) {

        }
    }

    public void listen() {
        try {
            // 循环处理
            while (true) {
                int count = selector.select();
                if (count > 0) { // 有事件要处理
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();

                        // acctep事件
                        if (key.isAcceptable()) {
                            SocketChannel socketChannel = serverSocketChannel.accept();
                            socketChannel.configureBlocking(false);
                            socketChannel.register(selector, SelectionKey.OP_READ);

                            // alert
                            System.out.println(socketChannel.getRemoteAddress() + "上线");
                        }

                        // read事件
                        if (key.isReadable()) {
                            // 处理read
                            readData(key);
                        }

                        // 当前key删除，防止重复处理
                        iterator.remove();
                    }

                } else {
                    System.out.println("等待中");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void readData(SelectionKey selectionKey) {
        // 定义一个socketChannel
        SocketChannel channel = null;
        try {
            // 取得关联的channel
            channel = (SocketChannel)selectionKey.channel();
            // 创建Buffer
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int count = channel.read(buffer);
            // 根据count的值做处理
            if(count > 0){
                // 把缓冲区的数据转成字符串
                String msg = new String(buffer.array());
                System.out.println("From 客户端" + msg);

                // 向其他客户端转发消息(去掉自己)，专门写一个方法来处理
                sendInfoToOtherClients(msg,channel);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            try {
                System.out.println(channel.getRemoteAddress() + "离线");
                // 取消注册
                selectionKey.cancel();
                // 关闭通道
                channel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // 转发消息给其他客户
    private void sendInfoToOtherClients(String msg, SocketChannel self) throws IOException {
        System.out.println("服务转发消息");
        // 遍历所有注册到selector上的SocketChannel并派出self
        for (SelectionKey key: selector.keys()) {
            // 获取channel
            SelectableChannel channel = key.channel();
            // 排除自己
            if(channel instanceof SocketChannel && channel != self){
                SocketChannel dest = (SocketChannel) channel;
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());

                // 将buffer数据写入 通道
                dest.write(buffer);
            }
        }
    }

    public static void main(String[] args) {
        GroupChatServer server = new GroupChatServer();
        server.listen();
    }
}
