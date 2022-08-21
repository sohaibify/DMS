package qatar.bank.app.exception;

public class PDFFileNotFoundException extends Exception{
    public PDFFileNotFoundException(String exception) {
        super(exception);
    }
    public PDFFileNotFoundException(String exception, Throwable cause) {
        super(exception, cause);
    }
}
