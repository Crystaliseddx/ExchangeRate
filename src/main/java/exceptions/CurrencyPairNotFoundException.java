package exceptions;

public class CurrencyPairNotFoundException extends MyException {
    public CurrencyPairNotFoundException(ErrorMessage errorMessage) {
        super(errorMessage);
    }
}
