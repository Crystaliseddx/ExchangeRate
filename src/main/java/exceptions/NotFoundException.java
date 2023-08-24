package exceptions;

public class NotFoundException extends BaseException {
    public NotFoundException(ErrorMessage errorMessage) {
        super(errorMessage);
    }
}
