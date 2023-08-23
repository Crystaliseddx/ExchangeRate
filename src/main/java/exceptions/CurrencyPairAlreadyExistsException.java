package exceptions;

public class CurrencyPairAlreadyExistsException extends MyException {
    public CurrencyPairAlreadyExistsException(ErrorMessage errorMessage) {
        super(errorMessage);
    }
}
