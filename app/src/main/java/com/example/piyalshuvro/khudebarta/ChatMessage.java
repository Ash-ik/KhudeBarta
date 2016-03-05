package com.example.piyalshuvro.khudebarta;

/**
 * Created by Technovibe on 17-04-2015.
 */
public class ChatMessage {
    private String message;
    private String dateTime;
    private String type;


    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
    public String getDate() {
        return dateTime;
    }

    public void setDate(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type =type;
    }


}
