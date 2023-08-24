package exceptions;

public class AlreadyExistsException extends BaseException {
    public AlreadyExistsException(ErrorMessage errorMessage) {
        super(errorMessage);
    }
}
