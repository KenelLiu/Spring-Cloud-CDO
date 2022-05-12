package com.cdo.cloud.config.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

    @Autowired
    private WebSocketMessageHandler webSocketMessageHandler; //websocket 第二种处理onOpen,onMessage
    @Autowired
    private WebSocketInterceptor webSocketInterceptor;//websocket开启【第二种和第三种 】方式的拦截器 

    @Autowired
    private AuthChannelInterceptor authChannelInterceptor;//websocket开启【第三种 】方式的拦截器 
    /**
     * 开启websocket
     * 第一种方式  @bean ServerEndpointExporter+{@link com.cdo.cloud.config.websocket.second.iot.bodyiot.config.websocket.IOTWebSocket}
     * 
     * @return
     */
    //@Bean
    public ServerEndpointExporter serverEndpointExporter() {
    	
        return new ServerEndpointExporter();
    }	
    
    /**
     * 开启websocket
     * 第二种方式: @EnableWebSocket+WebSocketConfigurer
     * 
     */
   
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry
                .addHandler(webSocketMessageHandler, "websocket/bodyiot2")
                .addInterceptors(webSocketInterceptor)
                .setAllowedOrigins("*");
    }

    /**
     * 开启websocket
     *第三种方式: @EnableWebSocketMessageBroker+WebSocketMessageBrokerConfigurer
     * STOMP
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 配置客户端尝试连接地址
        registry
        .addEndpoint("websocket/bodyiot")
        .setAllowedOrigins("*")    // 配置跨域
        .withSockJS();      // 开启sockJS支持，这里可以对不支持stomp的浏览器进行兼容。
    }
    
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic","/queue");         
        registry.setApplicationDestinationPrefixes("/app");      
        registry.setUserDestinationPrefix("/user");
    }    
    /**
     * 开启websocket
     *第三种方式: @EnableWebSocketMessageBroker+WebSocketMessageBrokerConfigurer
     * 拦截器方式2
     *
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
