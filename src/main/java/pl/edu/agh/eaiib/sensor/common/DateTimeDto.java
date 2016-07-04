package pl.edu.agh.eaiib.sensor.common;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.joda.ser.LocalDateTimeSerializer;
import com.google.common.base.Optional;
import org.joda.time.LocalDateTime;

import java.math.BigDecimal;


public class DateTimeDto implements MomentValue {

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime time;

    public DateTimeDto(){}

    public DateTimeDto(LocalDateTime time) {
        this.time = time;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    @Override
    public LocalDateTime getMoment() {
        return time;
    }

    @Override
    public Optional<BigDecimal> getMomentValue() {
        return Optional.absent();
    }
}
