package org.websocket.tp_websocket;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@WebServlet("/chat")
@ServerEndpoint("/chat")
public class Websocket extends HttpServlet {
    String login;
    String toDisplayLogin;
    private static final Set<Session> clients = new CopyOnWriteArraySet<>();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        login = request.getParameter("login");
        toDisplayLogin = login;
        response.sendRedirect("chat.jsp?login=" + login);
    }

    @OnOpen
    public void onOpen(Session session) {
        clients.add(session);
        System.out.println("New client: " + session.getId());
    }
    @OnMessage
    public String onMessage(String message, Session session) {
        System.out.println("Message from " + session.getId() + ": " + message);
        for (Session client : clients) {
            if (!client.equals(session)) {
                client.getAsyncRemote().sendText(message);
            }
        }
        return message;
    }
    @OnClose
    public void onClose(Session session) {
        clients.remove(session);
        System.out.println("Client disconnected: " + session.getId());
    }
    @OnError
    public void onError(Throwable t) {
        t.printStackTrace();
    }
    // tostring method
    public String toString() {
        return login;
    }
}
