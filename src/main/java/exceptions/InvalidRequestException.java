package exceptions;

public class InvalidRequestException extends BaseException {
    public InvalidRequestException(ErrorMessage errorMessage) {
        super(errorMessage);
    }
}
