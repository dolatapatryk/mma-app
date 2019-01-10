package exceptions;

public class UniqueKeyException extends Exception {
    public UniqueKeyException() { super(); }
    public UniqueKeyException(String message) { super(message); }
    public UniqueKeyException(String message, Throwable cause) { super(message, cause); }
    public UniqueKeyException(Throwable cause) { super(cause); }
}
