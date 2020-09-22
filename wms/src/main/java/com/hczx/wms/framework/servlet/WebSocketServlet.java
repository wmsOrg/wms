package com.hczx.wms.framework.servlet;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @ClassName: WebsocketServlet
 * @Description: 实现了进行数据库操作的Servlet类，又实现了Websocket的功能
 * @Date: 2020/9/17 10:16
 * @Author: linsheng.zheng
 * @Version: 1.0
 */
//该注解用来指定一个URI，客户端可以通过这个URI来连接到WebSocket，类似Servlet的注解mapping；
@ServerEndpoint("/websocket/{userId}")
@Component
public class WebSocketServlet extends HttpServlet {

    private static int onlineCount = 0;
    private static Map<String, WebSocketServlet> clients = new ConcurrentHashMap<>();
    private Session session;
    private String userId;

    @OnOpen
    public void onOpen(@PathParam("userId") String userId, Session session) throws IOException {

        this.userId = userId;
        this.session = session;

        addOnlineCount();
        clients.put(userId, this);
        System.out.println("已连接");
    }

    @OnClose
    public void onClose() throws IOException {
        clients.remove(userId);
        subOnlineCount();
    }

    @OnMessage
    public void onMessage(String message) throws IOException {

//        JSONObject jsonTo = JSONObject.parseObject(message);


            sendMessageAll("message");

    }

    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

    public void sendMessageTo(String message, String To) throws IOException {
        // session.getBasicRemote().sendText(message);
        //session.getAsyncRemote().sendText(message);
        for (WebSocketServlet item : clients.values()) {
            if (item.userId.equals(To) ){
                item.session.getAsyncRemote().sendText(message);
            }
        }
    }

    public void sendMessageAll(String message) throws IOException {
        for (WebSocketServlet item : clients.values()) {
            item.session.getAsyncRemote().sendText(message);
        }
    }


    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServlet.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServlet.onlineCount--;
    }

    public static synchronized Map<String, WebSocketServlet> getClients() {
        return clients;
    }

}
