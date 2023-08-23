package exceptions;

import java.sql.SQLException;

public class DBIsNotAvailableException extends MyException {
    public DBIsNotAvailableException(ErrorMessage errorMessage) {
        super(errorMessage);
    }
}
