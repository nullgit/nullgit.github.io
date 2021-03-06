package project3.service;

public class TeamException extends RuntimeException {
    static final long serialVersionUID = -9034897190745766939L;

    public TeamException() {
        super();
    }

    public TeamException(String message) {
        super(message);
    }
}
