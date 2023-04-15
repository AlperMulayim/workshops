package com.alper.svgapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/v1/boardingcards")
public class BoardingCardController {
    @Autowired
    private BoardingCardService cardService;

    @PostMapping("")
    public String generateBoardingCard(@RequestBody BoardingCard boardingCard){
        return cardService.getBoardingCard(boardingCard);
    }
}
