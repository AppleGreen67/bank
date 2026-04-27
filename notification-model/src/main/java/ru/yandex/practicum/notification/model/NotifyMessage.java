package ru.yandex.practicum.notification.model;

public class NotifyMessage {
    private String id;
    private String login;
    private String message;
    private Boolean error;

    public NotifyMessage() {
    }

    public NotifyMessage(String id, String login, String message, Boolean error) {
        this.id = id;
        this.login = login;
        this.message = message;
        this.error = error;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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
                ", login='" + login + '\'' +
                ", message='" + message + '\'' +
                ", error=" + error +
                '}';
    }
}
