package exceptions;

import java.sql.SQLException;

public class MyException extends SQLException {
    private ErrorMessage errorMessage;
    public MyException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
        this.errorMessage = errorMessage;
    }
    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }
}
