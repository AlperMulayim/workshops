package com.alper.carsnotification.notification;

import com.alper.carsnotification.Car;
import com.alper.carsnotification.CarsService;
import org.springframework.beans.SimpleTypeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@Controller
@CrossOrigin
public class NotificationController {
    @Autowired
    private SimpMessagingTemplate notificationTemplate;

    @Autowired
    private CarsService carsService;

    @MessageMapping("/notifications")
    @SendTo("/notifications/subscribe")
    public void sendNotification(@Payload Notification notification ){
        System.out.println(notification);
        notification.setDetail(notification.getDetail().toUpperCase());
        notification.setStatus("READ");
        Car car = carsService.getAllCars().get(10);
        notification.setCar(car);
        notificationTemplate.convertAndSend("/notifications/subscribe",notification);
    }
}
