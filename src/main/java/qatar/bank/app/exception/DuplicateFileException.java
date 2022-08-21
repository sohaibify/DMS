package qatar.bank.app.exception;

public class DuplicateFileException extends Exception{
    public DuplicateFileException(String exception) {
        super(exception);
    }
    public DuplicateFileException(String exception, Throwable cause) {
        super(exception, cause);
    }
}
