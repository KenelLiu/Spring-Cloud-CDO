package com.cdo.cloud.config.websocket.second;

import java.net.URLDecoder;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import com.alibaba.fastjson.JSONObject;

@Component
public class WebSocketInterceptor extends  HttpSessionHandshakeInterceptor {
	private static Log logger=LogFactory.getLog(WebSocketInterceptor.class);
	/**
     * 握手前
     * @param request
     * @param response
     * @param wsHandler
     * @param attributes
     * @return
     * @throws Exception
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        logger.info("beforeHandshake....."+request.getURI().getQuery());
        // 获得请求参数
       // HashMap<String, String> paramMap = HttpUtil.decodeParamMap(request.getURI().getQuery(), "utf-8");
        String url=URLDecoder.decode(request.getURI().getQuery(), "UTF-8");
        String[] params=url.split("&");
        if(params!=null){
        	for (int i = 0; i < params.length; i++) {
        		String[] arr=params[i].split("=");
        		if(arr[0].equals("token"))
        			attributes.put(arr[0], arr[1]);
			}
        }
        
        return true;
    }
    /**
     * 握手后
     * @param request
     * @param response
     * @param wsHandler
     * @param exception
     */
    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {       
        logger.info("afterHandshake.....");
        try {
        	response.getBody().write(JSONObject.toJSONBytes("WebSocket连接成功!"));
        } catch (Exception ex) {
            logger.error(ex.getMessage(),ex);
        }
    }
}
