package com.inzami.fp.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateTimeUtils {

    public static String parseZonedDateTimeToString(ZonedDateTime zonedDateTime, String format) {
        if (zonedDateTime != null) {
            return zonedDateTime.format(DateTimeFormatter.ofPattern(format));
        }
        return null;
    }

    public static ZonedDateTime parseStringToZonedDateTime(String date, String format) throws ParseException {
        if (date != null) {
            Date dateParsed = new SimpleDateFormat(format).parse(date);
            final ZoneId systemDefault = ZoneId.systemDefault();
            return ZonedDateTime.ofInstant(dateParsed.toInstant(), systemDefault);
        }
        return null;
    }

    public static ZonedDateTime atStartOfDay(ZonedDateTime zonedDateTime){
        return zonedDateTime.toLocalDate().atStartOfDay(ZoneId.systemDefault());
    }

    public static ZonedDateTime atEndOfDay(ZonedDateTime zonedDateTime){
        return zonedDateTime.toLocalDate().atTime(LocalTime.MAX).atZone(ZoneId.systemDefault());
    }
}