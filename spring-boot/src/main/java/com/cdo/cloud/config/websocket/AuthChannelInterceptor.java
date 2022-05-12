package com.cdo.cloud.config.websocket;

import java.security.Principal;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

/**
     * 开启websocket
     *第三种方式: @EnableWebSocketMessageBroker+WebSocketMessageBrokerConfigurer
     * STOMP
 * @author Kenel
 *
 */
@Component
@Order(Integer.MIN_VALUE + 99)
public class AuthChannelInterceptor implements ChannelInterceptor {
	private static Log logger=LogFactory.getLog(AuthChannelInterceptor.class);
	/**
   * 连接前监听
   *
   * @param message
   * @param channel
   * @return
   */
  @Override
  public Message<?> preSend(Message<?> message, MessageChannel channel) {
      StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);    
      //1、判断是否首次连接
      if (accessor != null && StompCommand.CONNECT.equals(accessor.getCommand())) {
          //2、判断token
          List<String> nativeHeader = accessor.getNativeHeader("Authorization");
          logger.info("AuthChannelInterceptor..........");
          if (nativeHeader != null && !nativeHeader.isEmpty()) {
              String token = nativeHeader.get(0);
              if(token!=null && !token.equals("")){
            	 //todo Token 解密
            	 String  user=token;
            	 Principal principal = new Principal() {
                     @Override
                     public String getName() {
                         return user;
                     }
                 }; 
                 accessor.setUser(principal);
                 return message;
              }
          }
         // return null;
          logger.warn("Authorization is not exists");
      }else{
    	  String userName="";
    	  if(accessor.getUser()!=null)
    		  userName=accessor.getUser().getName();
    	  logger.info("用户["+userName+"]在websocket交互中......");
      }     
      //不是首次连接，已经登陆成功
      return message;
  }
}
