package ru.neoflex.exceptions;

public class MyException extends Exception{
    private String getMyMessage() {
        return myMessage;
    }

    public void setMyMessage(String myMessage) {
        this.myMessage = myMessage;
    }

    private String myMessage;
    public MyException(String message) {
        super(message);
    }
}
