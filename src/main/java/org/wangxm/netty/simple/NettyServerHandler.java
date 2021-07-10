package org.wangxm.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/*
* 1.我们自定义一个Handler 需要netty 规定好的某个HandlerAdaptor
* 2.这时我们自定义的一个Handler，才能称为一个Handler
* */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    // 读取数据实际(这里可以读取客户端发送的消息)
    // 1.ctx 含有pipeline，通道Channel ,地址
    // 2.msg: 就是客户端发送的数据，默认Object
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("server ctx =" + ctx);
        // 将msg转成以后 ByteBuf
        // ByteBuf是netty提供的，不是NIO的ByteBuffer
        ByteBuf buf =(ByteBuf)msg;

        System.out.println("客户端发送消息是:" + buf.toString(CharsetUtil.UTF_8));
        System.out.println("客户端地址:" + ctx.channel().remoteAddress());
    }

    /*
    * 数据读取完毕
    * */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        // 将数据写入到缓存，并刷新
        // 我们发送的数据需要进行编码
        ctx.writeAndFlush(Unpooled.copiedBuffer("Hello,客户端~",CharsetUtil.UTF_8));
    }

    /*
    * 发生异常，需要关闭通道
    * */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
