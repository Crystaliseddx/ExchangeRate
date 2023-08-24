package exceptions;

public class DBIsNotAvailableException extends BaseException {
    public DBIsNotAvailableException(ErrorMessage errorMessage) {
        super(errorMessage);
    }
}
