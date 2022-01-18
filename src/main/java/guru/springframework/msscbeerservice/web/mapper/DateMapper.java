package guru.springframework.msscbeerservice.web.mapper;

import lombok.val;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Component
public class DateMapper {

    public OffsetDateTime toOffsetDateTime(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        val localDate = timestamp.toLocalDateTime();

        return OffsetDateTime.of(localDate.getYear(),
                localDate.getMonthValue(),
                localDate.getDayOfMonth(),
                localDate.getHour(),
                localDate.getMinute(),
                localDate.getSecond(),
                localDate.getNano(),
                ZoneOffset.UTC
        );
    }

    public Timestamp toTimestamp(OffsetDateTime offsetDateTime) {
        if (offsetDateTime == null) {
            return null;
        }

        return Timestamp.valueOf(offsetDateTime.atZoneSameInstant(ZoneOffset.UTC).toLocalDateTime());
    }
}
