package com.cdo.cloud.web.websocket;

import java.security.Principal;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
@Controller
public class WebSocketController {
	@Autowired
	private SimpMessagingTemplate messagingTemplate;
	private static Log logger=LogFactory.getLog(WebSocketController.class); 
   
	@MessageMapping("/device/pushOne")
    public void pushOne(StompHeaderAccessor accessor,String body){
		String groupId="2";
		messagingTemplate.convertAndSend("/topic/alarm", body+",广播");
		messagingTemplate.convertAndSendToUser(groupId, "/alarm",body+",分组");
    }
	
    @MessageMapping("/person/chat") 
    public void sendPrivateMessage(StompHeaderAccessor accessor,String body) {
    	try{
    		Person person=JSON.parseObject(body,Person.class,Feature.TrimStringFieldValue);
    		if(person.getReceiver()!=null){
    			Principal principal=accessor.getUser();
    			if(principal!=null){
    				person.setSender(principal.getName());    				
    			}
    			String data=JSONObject.toJSONString(person);
    			messagingTemplate.convertAndSendToUser(person.getReceiver(), "/queue/chat",data);
    		}
		}catch(Throwable ex){
			logger.error("person chat error:"+ex.getMessage(),ex);
		}	
    }
    
    @SubscribeMapping("/queue/chat")
    public String subscribe(StompHeaderAccessor accessor){
    	List<String> nativeHeader = accessor.getNativeHeader("Authorization");
    	logger.info("body="+nativeHeader);
        return  "subscribe /queue/chat"+" success";
    }
    
    @SubscribeMapping("/{tenantId}/alarm")
    public String subscribe(@DestinationVariable String tenantId,StompHeaderAccessor accessor){
    	List<String> nativeHeader = accessor.getNativeHeader("Authorization");
    	logger.info("body="+nativeHeader);
        return  "subscribe 分组 /"+tenantId+"/alarm success";
    }
   
    
}
