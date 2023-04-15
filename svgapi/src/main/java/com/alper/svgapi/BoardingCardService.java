package com.alper.svgapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class BoardingCardService {

    @Autowired
    private HtmlTemplateGenerator templateGenerator;


    public String getBoardingCard(BoardingCard card){
        Map<String,Object> cardDataMap = new HashMap<>();

        cardDataMap.put("card",card);

        return templateGenerator.createBindedHtmlTemplate("boardingcard.html",cardDataMap);
    }
}
