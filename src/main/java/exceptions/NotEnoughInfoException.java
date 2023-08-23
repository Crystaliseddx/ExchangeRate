package exceptions;

public class NotEnoughInfoException extends MyException {
    public NotEnoughInfoException(ErrorMessage errorMessage) {
        super(errorMessage);
    }
}
