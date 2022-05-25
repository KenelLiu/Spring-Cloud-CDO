package com.cdo.cloud.config.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import com.cdo.cloud.config.websocket.second.WebSocketInterceptor;
import com.cdo.cloud.config.websocket.second.WebSocketMessageHandler;

@Configuration
//@EnableWebSocket WebSocketConfigurer
@EnableWebSocketMessageBroker	
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer{

    
    //=================开启websocket 第一种方式=============//
    /**
     * 开启websocket
     * 第一种方式  @bean ServerEndpointExporter+{@link com.cdo.cloud.config.websocket.second.IOTWebSocket}
     * 
     * @return
     */
    //@Bean
    public ServerEndpointExporter serverEndpointExporter() {
    	
        return new ServerEndpointExporter();
    }	
    
    //=================开启websocket 第二种方式=============//
    /**
     * 开启websocket
     * 第二种方式: @EnableWebSocket+WebSocketConfigurer
     * 
     */
    @Autowired
    private WebSocketMessageHandler webSocketMessageHandler; //处理消息类  类似注解onOpen,onMessage,onClose
    
    @Autowired
    private WebSocketInterceptor webSocketInterceptor;//【第二种和第三种 】方式的拦截器 
    
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry
                .addHandler(webSocketMessageHandler, "websocket/bodyiot2")
                .addInterceptors(webSocketInterceptor)
                .setAllowedOrigins("*");
    }

    /**
     * 开启websocket 使用强大STOMP
     *  第三种方式: @EnableWebSocketMessageBroker+WebSocketMessageBrokerConfigurer
     * STOMP
     */
    @Autowired
    private AuthChannelInterceptor authChannelInterceptor;//websocket开启【第三种 】方式的拦截器 
    

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 配置客户端尝试连接地址
        registry
        .addEndpoint("/websocket/bodyiot")
        .setAllowedOrigins("*")    // 配置跨域
        .withSockJS();      // 开启sockJS支持，这里可以对不支持stomp的浏览器进行兼容。
    }
    
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
    	
        registry.enableSimpleBroker("/topic","/queue","/user");    //user 用于分组 服务端推送，     queue 用户点对点
        registry.setApplicationDestinationPrefixes("/app","/user"); //app 常规数据推送 user 触发订阅  @SubscribeMapping   
        registry.setUserDestinationPrefix("/user");
    }    
    /**
     * 使用通道拦截方式处理鉴权等 比 webSocketInterceptor 处理起来方便。
     * @param registration
     */
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(authChannelInterceptor);
    }    
    /**
     * 配置发送与接收的消息参数，可以指定消息字节大小，缓存大小，发送超时时间
     * @param registration
     */
    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
        /*
         * 1. setMessageSizeLimit 设置消息缓存的字节数大小 字节
         * 2. setSendBufferSizeLimit 设置websocket会话时，缓存的大小 字节
         * 3. setSendTimeLimit 设置消息发送会话超时时间，毫秒
         */
        registration.setMessageSizeLimit(10240)
                    .setSendBufferSizeLimit(10240)
                    .setSendTimeLimit(10000);
    }
}
