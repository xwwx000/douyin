package com.xwwx.douyin.system.websocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xwwx.douyin.common.core.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: 可乐罐
 * @date: 2022/7/14 20:06
 * @description:大屏websocket
 */
@Component
@Slf4j
@ServerEndpoint("/device/system/allmonitor")
public class MonitorWebSocket {
    private static ConcurrentHashMap<String,MonitorWebSocket> webSocketMap = new ConcurrentHashMap<String, MonitorWebSocket>();
    private Session session;
    @OnOpen
    public void onOpen(Session session){
        this.session = session;
        webSocketMap.put(session.getId(),this);
        log.info("新用户连接");

    }
    @OnClose
    public void onClose(){
        if(webSocketMap.containsKey(session.getId())){
            webSocketMap.remove(session.getId());
        }
        log.info("用户断开连接{0}",session.getId());
    }
    @OnMessage
    public void onMessage(String message,Session session){
        if(StringUtils.isNotEmpty(message)){
            try{
                JSONObject jsonObject = JSON.parseObject(message);
                log.info("接收到用户的消息{0}-{1}",session.getId(),message);
            }catch (Exception e){{
                e.printStackTrace();
            }}
        }
    }
    @OnError
    public void onError(Session session,Throwable error){
        log.info("发生错误{1}",error.getMessage());
    }

    /**
     * 向所有客户发送消息
     * @param message
     */
    public static void sendMessage(String message){
        log.info("已连接数量:"+ webSocketMap.size());
        try{
            for(Map.Entry<String,MonitorWebSocket> entry : webSocketMap.entrySet()){
                if(entry.getValue().session != null){
                    entry.getValue().session.getBasicRemote().sendText(message);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
