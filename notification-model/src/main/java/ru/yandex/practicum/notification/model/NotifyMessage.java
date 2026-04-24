package ru.yandex.practicum.notification.model;

public class NotifyMessage {
    private String id;
    private String message;
    private Boolean error;

    public NotifyMessage() {
    }

    public NotifyMessage(String id, String message, Boolean error) {
        this.id = id;
        this.message = message;
        this.error = error;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "NotifyMessage{" +
                "id='" + id + '\'' +
                ", message='" + message + '\'' +
                ", error=" + error +
                '}';
    }
}
