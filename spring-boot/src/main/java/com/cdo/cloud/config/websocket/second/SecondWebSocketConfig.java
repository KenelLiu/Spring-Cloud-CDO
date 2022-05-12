package com.cdo.cloud.config.websocket.second;

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
@EnableWebSocket 
//@EnableWebSocketMessageBroker	
public class SecondWebSocketConfig implements WebSocketConfigurer{

    @Autowired
    private WebSocketMessageHandler webSocketMessageHandler; //websocket 第二种处理onOpen,onMessage
    @Autowired
    private WebSocketInterceptor webSocketInterceptor;//websocket开启【第二种和第三种 】方式的拦截器 

   
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

}
