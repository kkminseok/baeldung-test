package manipulations;

import manipulations.utils.DateValidator;
import manipulations.utils.impl.DateValidatorUsingDateFormat;
import manipulations.utils.impl.DateValidatorUsingDateTimeFormatter;
import manipulations.utils.impl.DateValidatorUsingLocalDate;
import org.apache.commons.validator.GenericValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Locale;

public class CheckDateValidatorTest {

    @Test
    public void using_dateFormat_test() {
        DateValidator dateValidator = new DateValidatorUsingDateFormat("MM/dd/yyyy");

        Assertions.assertTrue(dateValidator.isValid("02/28/2019"));
        Assertions.assertFalse(dateValidator.isValid("02/30/2019"));
    }

    @Test
    public void using_local_date_test() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.BASIC_ISO_DATE;
        DateValidator dateValidator = new DateValidatorUsingLocalDate(dateTimeFormatter);

        Assertions.assertTrue(dateValidator.isValid("20190228"));
        Assertions.assertFalse(dateValidator.isValid("20190230"));
    }

    @Test
    public void using_dateTimeFormatter_test() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("uuuu-MM-dd", Locale.US)
                .withResolverStyle(ResolverStyle.STRICT);
        DateValidator dateValidator = new DateValidatorUsingDateTimeFormatter(dateTimeFormatter);

        Assertions.assertTrue(dateValidator.isValid("2019-02-28"));
        Assertions.assertFalse(dateValidator.isValid("2019-02-30"));
    }

    @Test
    public void apache_common_data_validator_test() {
        Assertions.assertTrue(GenericValidator.isDate("2019-02-28", "yyyy-MM-dd", true));
        Assertions.assertFalse(GenericValidator.isDate("2019-02-29", "yyyy-MM-dd", true));
    }
}
