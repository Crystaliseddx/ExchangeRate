package exceptions;

import java.sql.SQLException;

public class BaseException extends SQLException {
    private ErrorMessage errorMessage;

    public BaseException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
        this.errorMessage = errorMessage;
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }
}
