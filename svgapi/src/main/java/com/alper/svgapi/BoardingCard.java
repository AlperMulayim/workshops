package com.alper.svgapi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardingCard {
    private String passengername;
    private String airline;
    private String departure;
    private String arrival;
    private String flightno;
    private String flightdate;
    private String flighttime;
    private String seat;
    private String boardingtime;
    private String gate;
    private String passengerclass;
}
