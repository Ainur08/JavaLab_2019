package model;

public class Message {
    private int idUser;
    private String dateTime;
    private String message;

    public Message() {
    }

    public Message(int idUser, String dateTime, String message) {
        this.idUser = idUser;
        this.dateTime = dateTime;
        this.message = message;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
