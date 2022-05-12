package com.cdo.cloud.config.websocket.second;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

/**
  * 开启websocket
  * 第一种方式  @bean ServerEndpointExporter+{@link com.cdo.cloud.config.websocket.second.iot.bodyiot.config.websocket.IOTWebSocket}
  * 
 * @author Kenel
 *
 */
//@ServerEndpoint(value = "/websocket/bodyiot1")
//@Component
public class IOTWebSocket {

	private static Log logger=LogFactory.getLog(IOTWebSocket.class);
    //concurrent包的线程安全Set，用来存放每个客户端对应的WebSocket对象。
    private static CopyOnWriteArraySet<IOTWebSocket> webSocketSet = new CopyOnWriteArraySet<IOTWebSocket>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    /**
     * 连接建立成功调用的方法*/
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSocketSet.add(this);     //加入set中
        logger.info("call onOpen");
        try {
            sendMessage("call onOpen");
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this); 
    }

    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     **/
    @OnMessage
    public void onMessage(String message, Session session) {       
        logger.info("来自客户端的消息:"+message);     
        //群发消息
        for (IOTWebSocket item : webSocketSet) {
            try {
                item.sendMessage("服务端:"+message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 发生错误时调用
     */
    @OnError
    public void onError(Session session, Throwable error) {
        logger.error("sessionId="+session.getId()+" occurred error:"+error.getMessage(), error);
    }


    public void sendMessage(String message) throws IOException{
        this.session.getBasicRemote().sendText(message);
        //this.session.getAsyncRemote().sendText(message);
    }


    /**
     * 群发自定义消息
     * */
    public static void sendInfo(String message){
        for (IOTWebSocket item : webSocketSet) {
            try {
                item.sendMessage(message);
            } catch (Exception e) {
                continue;
            }
        }
    }
}
