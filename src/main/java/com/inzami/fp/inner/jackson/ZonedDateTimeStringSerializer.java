package com.inzami.fp.inner.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.inzami.fp.util.DateTimeUtils;

import java.io.IOException;
import java.time.ZonedDateTime;

import static com.inzami.fp.inner.jackson.ZonedDateTimeStringDeserializer.DATE_TIME_WITH_ZONE;

public class ZonedDateTimeStringSerializer extends JsonSerializer<ZonedDateTime> {

    @Override
    public void serialize(ZonedDateTime value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        String zonedDateTimeString = DateTimeUtils.parseZonedDateTimeToString(value, DATE_TIME_WITH_ZONE);
        jsonGenerator.writeString(zonedDateTimeString);
    }

}