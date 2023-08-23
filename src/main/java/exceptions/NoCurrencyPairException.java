package exceptions;

public class NoCurrencyPairException extends MyException {
    public NoCurrencyPairException(ErrorMessage errorMessage) {
        super(errorMessage);
    }
}
