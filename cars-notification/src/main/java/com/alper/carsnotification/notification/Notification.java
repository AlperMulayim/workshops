package com.alper.carsnotification.notification;

import com.alper.carsnotification.Car;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Notification {
    private String id;
    private String detail;
    private Car car;
    private  String status;
}
