package com.cdo.cloud.web.websocket;

import java.security.Principal;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class WebSocketController {
	@Autowired
	private SimpMessagingTemplate messagingTemplate;
	 
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public String greeting(String body,Principal principal) throws Exception {
    	JSONObject dataJson=new JSONObject(body);
    	dataJson.put("content", "hello,"+dataJson.optString("name", ""));
        Thread.sleep(500); // simulated delay    
       // msg.setSender(principal.getName());
        //将消息推送到指定路径上
       // messagingTemplate.convertAndSendToUser(msg.getReceiver(), "topic/chat", msg);
        return dataJson.toString();
    }   
    
    @MessageMapping("/sendPrivateMessage") 
    public void sendPrivateMessage(String body,Principal principal) {
    	JSONObject dataJson=new JSONObject(body);
    	String content=dataJson.optString("name","");
    	String receiver=dataJson.optString("receiver","");
    	JSONObject retJson=new JSONObject();
    	retJson.put("sender", principal!=null?principal.getName():"");
    	retJson.put("content", content);
        //将消息推送到指定路径上
    	messagingTemplate.convertAndSendToUser(receiver, "topic/chat", retJson.toString());
    }
}
