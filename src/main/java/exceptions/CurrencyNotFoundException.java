package exceptions;

public class CurrencyNotFoundException extends MyException{
    public CurrencyNotFoundException(ErrorMessage errorMessage) {
        super(errorMessage);
    }
}
