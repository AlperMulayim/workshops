package com.alper.websockettutor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WSMessage {
    private String sender;
    private String message;
}
