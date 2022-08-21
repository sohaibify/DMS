package qatar.bank.app.exception;

public class PostNotFoundException extends Exception{
    public PostNotFoundException(String message) {
        super(message);
    }

    public PostNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
