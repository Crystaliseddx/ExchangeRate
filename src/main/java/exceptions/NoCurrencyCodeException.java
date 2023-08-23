package exceptions;

public class NoCurrencyCodeException extends MyException {

    public NoCurrencyCodeException(ErrorMessage errorMessage) {
        super(errorMessage);
    }

}
