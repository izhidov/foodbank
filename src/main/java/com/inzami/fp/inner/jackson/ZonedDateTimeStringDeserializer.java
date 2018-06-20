package com.inzami.fp.inner.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.inzami.fp.exception.DateISO8601FormatException;
import com.inzami.fp.util.DateTimeUtils;

import java.io.IOException;
import java.text.ParseException;
import java.time.ZonedDateTime;

public class ZonedDateTimeStringDeserializer extends JsonDeserializer<ZonedDateTime> {

    public static final String DATE_TIME_WITH_ZONE = "dd-MM-yyyy HH:mm:ss";

    @Override
    public ZonedDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        ObjectCodec objectCodec = jsonParser.getCodec();
        JsonNode jsonNode = objectCodec.readTree(jsonParser);
        String zonedDateTimeString = jsonNode.asText();
        try {
            return DateTimeUtils.parseStringToZonedDateTime(zonedDateTimeString, DATE_TIME_WITH_ZONE);
        } catch (ParseException e) {
            Object parsedObject = jsonParser.getParsingContext().getCurrentValue();
            String parsedName = jsonParser.getParsingContext().getCurrentName();
            throw new DateISO8601FormatException(parsedObject.getClass(), parsedName);
        }
    }

}