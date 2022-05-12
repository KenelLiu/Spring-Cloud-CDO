package com.cdo.cloud.config.websocket.second;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * 开启websocket
     第二种方式: @EnableWebSocket+WebSocketConfigurer
   使用
 * @author Kenel
 *
 */
@Component
public class WebSocketMessageHandler extends TextWebSocketHandler {
	private static Log logger=LogFactory.getLog(WebSocketMessageHandler.class);
    /**
     * socket 建立成功事件
     * @param session
     * @throws Exception
     * @OnOpen 
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        logger.info("afterConnectionEstablished.....OnOpen..");
    }
    /**
     * 接收消息事件
     * @param session
     * @param message
     * @throws Exception
     *  @OnMessage 
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 获得客户端传来的消息
        String payload = message.getPayload();
        Object token = session.getAttributes().get("token");
        logger.info("server 接收到 " + token + " 发送的 " + payload);
        session.sendMessage(new TextMessage("server 发送给 " + token + " 消息 " + payload + " " ));
    }

    /**
     * socket 断开连接时
     * @param session
     * @param status
     * @throws Exception
     *  @OnClose
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
    	 logger.info("afterConnectionClosed.....OnClose..");
    }
}
