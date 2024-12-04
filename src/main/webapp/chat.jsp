<%--
  Created by IntelliJ IDEA.
  User: CYTech Student
  Date: 12/4/2024
  Time: 3:10 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Chat</title>
</head>
<body>
<h1>Chat</h1>
<!-- I want to display the name of the connected -->
<p>Welcome, <%= request.getParameter("login") %>!</p>
<textarea id="output" rows="10" cols="50" readonly></textarea><br>
<input type="text" id="message" size="50" placeholder="Enter a message">
<button onclick="sendMessage()">Send</button>
<button onclick="closeSocket()">Close</button>
<script type="text/javascript">
    const socket = new WebSocket("ws://172.17.16.1:8080/TP_WebSocket_war_exploded/chat");
    socket.onOpen = function(event) {
        const output = document.getElementById("output");
        output.value += "Connected\n";
    };
    socket.onmessage = function(event) {
        const output = document.getElementById("output");
        output.value += event.data + "\n";
    };
    function sendMessage() {
        const message = document.getElementById("message").value;
        socket.send("<%=request.getParameter("login")%>" + " : "+message);
    }
    function closeSocket() {
        socket.close();
    }
</script>
</body>
</html>
