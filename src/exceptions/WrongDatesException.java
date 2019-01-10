package exceptions;

public class WrongDatesException extends Exception {
    public WrongDatesException() { super(); }
    public WrongDatesException(String message) { super(message); }
    public WrongDatesException(String message, Throwable cause) { super(message, cause); }
    public WrongDatesException(Throwable cause) { super(cause); }
}
