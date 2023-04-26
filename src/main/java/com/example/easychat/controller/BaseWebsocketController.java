package com.example.easychat.controller;

import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Log4j2
@Controller
@ServerEndpoint("/websocket/{room}/{username}")
public class BaseWebsocketController {

    private static final Map<String, Map<String, Session>> rooms = new ConcurrentHashMap<>();
    private static final AtomicInteger queueCounter = new AtomicInteger(0);

    @OnOpen
    public void openSession(Session session, @PathParam("room") String room, @PathParam("username") String username) {
        Map<String, Session> sessions = rooms.computeIfAbsent(room, k -> new ConcurrentHashMap<>());
        sessions.put(session.getId(), session);
        session.getUserProperties().put("username", username);
        sendToRoom(room, "[" + username + "] enter");
    }

    @OnMessage
    public void onMessage(Session session, String message, @PathParam("room") String room) {
        String username = (String) session.getUserProperties().get("username");
        sendToRoom(room, "[" + username + "]" + message);
    }

    @OnClose
    public void closeSession(Session session, CloseReason closeReason, @PathParam("room") String room) {
        String username = (String) session.getUserProperties().get("username"); // 从Session属性中检索username
        Map<String, Session> sessions = rooms.getOrDefault(room, new ConcurrentHashMap<>());
        sessions.remove(session.getId());
        sendToRoom(room, "[" + username + "] leave");
    }

    @OnError
    public void sessionError(Session session, Throwable throwable, @PathParam("room") String room) {
        Map<String, Session> sessions = rooms.getOrDefault(room, new ConcurrentHashMap<>());
        sessions.remove(session.getId());
        try {
            session.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendToRoom(String room, String message) {
        Map<String, Session> sessions = rooms.getOrDefault(room, new ConcurrentHashMap<>());
        for (Session s : sessions.values()) {
            final RemoteEndpoint.Async async = s.getAsyncRemote();
            try {
                async.sendText(message);
            } catch (IllegalStateException e) {
                sessions.remove(s.getId());
            }
        }
    }
}
