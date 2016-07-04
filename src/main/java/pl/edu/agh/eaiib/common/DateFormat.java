package pl.edu.agh.eaiib.common;

import org.joda.time.ReadablePartial;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import static com.google.common.base.Preconditions.*;


public class DateFormat {

    private static final DateTimeFormatter DEFAULT_DATE_FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd");

    private ReadablePartial readablePartial;

    public DateFormat(ReadablePartial readablePartial) {
        this.readablePartial = checkNotNull(readablePartial);
    }

    public String printDefault() {
        return DEFAULT_DATE_FORMATTER.print(readablePartial);
    }
}
