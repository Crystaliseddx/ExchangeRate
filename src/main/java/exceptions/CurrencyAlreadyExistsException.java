package exceptions;

public class CurrencyAlreadyExistsException extends MyException{
    public CurrencyAlreadyExistsException(ErrorMessage errorMessage) {
        super(errorMessage);
    }
}
