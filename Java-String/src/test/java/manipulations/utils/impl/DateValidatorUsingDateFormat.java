package manipulations.utils.impl;

import manipulations.utils.DateValidator;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateValidatorUsingDateFormat implements DateValidator {

    private String dateformat;
    public DateValidatorUsingDateFormat(String dateformat) {
        this.dateformat = dateformat;
    }

    @Override
    public boolean isValid(String dateStr) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateformat);
        simpleDateFormat.setLenient(false); //비정상적인 날짜가 들어오면 에러 발생
        try {
            simpleDateFormat.parse(dateStr);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }
}
