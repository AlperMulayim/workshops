package com.alper.websockettutor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@Controller
@CrossOrigin
public class ChatController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat")
    @SendTo("/topic")
    public void sendChat(@Payload WSMessage wsMessage){
        System.out.println(wsMessage);
        System.err.println(wsMessage.getMessage());
        messagingTemplate.convertAndSend("/topic",wsMessage);
    }
}
