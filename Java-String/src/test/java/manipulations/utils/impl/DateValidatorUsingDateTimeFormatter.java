package manipulations.utils.impl;

import manipulations.utils.DateValidator;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateValidatorUsingDateTimeFormatter implements DateValidator {
    private DateTimeFormatter dateTimeFormatter;

    public DateValidatorUsingDateTimeFormatter(DateTimeFormatter dateTimeFormatter){
        this.dateTimeFormatter = dateTimeFormatter;
    }

    @Override
    public boolean isValid(String dateStr) {
        try {
            this.dateTimeFormatter.parse(dateStr);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }
}
