package org.wangxm.netty.http;

import com.sun.jndi.toolkit.url.Uri;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

/*
* 1.SimpleChannelInboundHandler是ChannelInboundHandler的子类
* 2.HttpObject表示
* */
public class TestHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    // 读取客户端数据
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {

        if(msg instanceof HttpRequest){
            System.out.println("msg 类型=" + msg.getClass());
            System.out.println("客户端地址=" + ctx.channel().remoteAddress());

            // 获取到
            HttpRequest httpRequest = (HttpRequest) msg;
            // 获取Uri
//            Uri uri = new Uri(httpRequest.uri());
//            if(uri.getPath().equals("/favicon.ico")){
//                System.out.println("请求了 favicon.ico,不做响应");
//                return;
//            }

            // 恢复信息给浏览器
            ByteBuf content = Unpooled.copiedBuffer("hello,我是服务器", CharsetUtil.UTF_8);

            // 构造一个http的响应，即HttpResponse
            DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
            response.headers().set(HttpHeaderNames.CONTENT_TYPE,"test/plain");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH,content.readableBytes());

            // 将构建好的response返回
            ctx.writeAndFlush(response);
        }
    }
}
