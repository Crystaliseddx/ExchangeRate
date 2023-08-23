package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {
    public boolean isValidCurrencyCode(String url) {
        boolean isValidCurrencyCode = true;
        if (url.length() != 3) {
            isValidCurrencyCode = false;
        } else if (url.matches("[a-zA-Z]")) {
            isValidCurrencyCode = false;
        }
        return isValidCurrencyCode;
    }
}
