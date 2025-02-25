package tn.esprit.tic.timeforge.Payload.Response;

public class MessageRespone {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MessageRespone() {
    }

    public MessageRespone(String message) {
        this.message = message;
    }
}
